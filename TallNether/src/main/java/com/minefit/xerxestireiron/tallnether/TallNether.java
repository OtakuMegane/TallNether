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
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.minefit.xerxestireiron.tallnether.Messages;

public class TallNether extends JavaPlugin implements Listener {
    private String name;
    protected String version;
    protected final Messages messages = new Messages(this.getName());
    private ManageHell manageHell = new ManageHell(this);
    protected final ServerVersion serverVersion = new ServerVersion(this);
    private final List<String> compatibleVersions = Arrays.asList("v1_12_R1", "v1_13_R1", "v1_13_R2", "v1_14_R1",
            "v1_15_R1");
    private HashMap<String, ManageHell> legacy_worlds;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.name = getServer().getClass().getPackage().getName();
        this.version = this.name.substring(this.name.lastIndexOf('.') + 1);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.legacy_worlds = new HashMap<>();

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
        if (this.version.equals("v1_12_R1")) {
            ;
        } else {
            this.manageHell.unloadHell();
        }

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

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldUnload(WorldUnloadEvent event) {
        World world = event.getWorld();

        if (this.version.equals("v1_12_R1")) {
            this.legacy_worlds.remove(world.getName());
        } else {
            this.manageHell.removeWorld(event.getWorld());
        }
    }

    public void prepareWorld(World world) {
        String worldName = world.getName();

        if (this.getConfig().getBoolean("worlds." + worldName + ".enabled", false)) {
            //A bit lazy but should keep things working for 1.12
            if (this.version.equals("v1_12_R1")) {
                if (!this.legacy_worlds.containsKey(worldName)) {
                    ManageHell manageHell = new ManageHell(this);
                    manageHell.overrideGenerator(world);
                    this.legacy_worlds.put(worldName, manageHell);
                }
            } else {
                this.manageHell.overrideGenerator(world);
            }
        }
    }
}
