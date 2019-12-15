package com.minefit.xerxestireiron.tallnether;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.minefit.xerxestireiron.tallnether.Messages;

public class TallNether extends JavaPlugin implements Listener {
    private String name;
    protected String version;
    protected final Messages messages = new Messages(this.getName());
    private HashMap<String, ManageHell> manageWorlds;
    private ManageHell manageHell = new ManageHell(this);
    protected final ServerVersion serverVersion = new ServerVersion(this);
    private final List<String> compatibleVersions = Arrays.asList("v1_12_R1", "v1_13_R1", "v1_13_R2", "v1_14_R1",
            "v1_15_R1");

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.name = getServer().getClass().getPackage().getName();
        this.version = this.name.substring(this.name.lastIndexOf('.') + 1);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.manageWorlds = new HashMap<>();

        if (!this.serverVersion.compatibleVersion(this.compatibleVersions)) {
            this.messages.incompatibleVersion();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Catches the /reload command or other things that may bypass the WorldInitEvent
        for (World world : Bukkit.getWorlds()) {
            prepareWorld(world);
        }

        this.messages.pluginReady();
    }

    @Override
    public void onDisable() {
        this.messages.pluginDisable();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldInit(WorldInitEvent event) {
        prepareWorld(event.getWorld());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldLoad(WorldLoadEvent event) {
        prepareWorld(event.getWorld());
    }

    public void prepareWorld(World world) {
        String worldName = world.getName();

        if (this.getConfig().getBoolean("worlds." + worldName + ".enabled", false)
                && !this.manageWorlds.containsKey(worldName)) {
            manageHell.overrideGenerator(world);
            this.manageWorlds.put(worldName, null);
        }
    }
}
