package com.minefit.xerxestireiron.tallnether;

import java.util.ArrayList;
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

public class TallNether extends JavaPlugin implements Listener {
    private String name;
    protected String version;
    protected final Messages messages = new Messages(this.getName());
    private ManageHell manageHell = new ManageHell(this);
    protected final ServerVersion serverVersion = new ServerVersion(this);
    private final List<String> compatibleVersions = Arrays.asList("v1_12_R1", "v1_13_R1", "v1_13_R2", "v1_14_R1",
            "v1_15_R1", "v1_16_R1", "v1_16_R2", "v1_16_R3", "v1_17_R1", "v1_18_R1");
    private HashMap<String, ManageHell> legacyWorlds;
    private List<World> checkedWorlds;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.name = getServer().getClass().getPackage().getName();
        this.version = this.name.substring(this.name.lastIndexOf('.') + 1);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.legacyWorlds = new HashMap<>();
        this.checkedWorlds = new ArrayList<>();

        if (!this.serverVersion.compatibleVersion(this.compatibleVersions)) {
            this.messages.incompatibleVersion();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        new ConfigAccessor(this.getConfig());

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

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldInit(WorldInitEvent event) {
        prepareWorld(event.getWorld());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldLoadEvent event) {
        prepareWorld(event.getWorld());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldUnload(WorldUnloadEvent event) {
        World world = event.getWorld();

        if (!this.checkedWorlds.contains(world)) {
            return;
        } else {
            this.checkedWorlds.remove(world);
        }

        if (this.version.equals("v1_12_R1")) {
            this.legacyWorlds.remove(world.getName());
        } else {
            this.manageHell.removeWorld(event.getWorld());
        }
    }

    public void prepareWorld(World world) {
        String worldName = world.getName();

        if (this.checkedWorlds.contains(world)) {
            return;
        } else {
            this.checkedWorlds.add(world);
        }

        if (this.getConfig().getBoolean("worlds." + worldName + ".enabled", false)) {
            //A bit lazy but should keep things working for 1.12
            if (this.version.equals("v1_12_R1")) {
                if (!this.legacyWorlds.containsKey(worldName)) {
                    ManageHell manageHell = new ManageHell(this);
                    manageHell.overrideGenerator(world);
                    this.legacyWorlds.put(worldName, manageHell);
                }
            } else {
                this.manageHell.overrideGenerator(world);
            }
        }
    }

    public boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
