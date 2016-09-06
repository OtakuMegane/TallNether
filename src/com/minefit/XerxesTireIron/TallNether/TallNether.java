package com.minefit.XerxesTireIron.TallNether;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TallNether extends JavaPlugin implements Listener {
    private String name;
    protected String version;
    private final Messages messages = new Messages(this);
    private HashMap<String, ManageHell> manageWorlds;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.name = getServer().getClass().getPackage().getName();
        this.version = this.name.substring(this.name.lastIndexOf('.') + 1);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.manageWorlds = new HashMap<String, ManageHell>();

        if (!this.version.equals("v1_8_R2") && !this.version.equals("v1_8_R3") && !this.version.equals("v1_9_R1")
                && !this.version.equals("v1_9_R2") && !this.version.equals("v1_10_R1")) {
            this.messages.incompatibleVersion();
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.messages.pluginReady();
        }

        // Catches the /reload command or other things that may bypass the
        // WorldInitEvent
        for (World world : Bukkit.getWorlds()) {
            prepareWorld(world);
        }
    }

    @Override
    public void onDisable() {
        // Let's clean up and put the original generators back in place
        for (String worldName : this.manageWorlds.keySet()) {
            this.manageWorlds.get(worldName).restoreGenerator();
        }

        this.messages.pluginDisable();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldInit(WorldInitEvent event) {
        prepareWorld(event.getWorld());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldLoadEvent event) {
        prepareWorld(event.getWorld());
    }

    public void prepareWorld(World world) {
        String worldName = world.getName();

        if (this.getConfig().getBoolean("worlds." + worldName + ".enabled", false)
                && !this.manageWorlds.containsKey(worldName)) {
            this.manageWorlds.put(worldName, new ManageHell(world, this));
        }
    }
}
