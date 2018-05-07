package com.minefit.xerxestireiron.tallnether;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class ManageHell {

    private final TallNether plugin;
    private final World world;
    private com.minefit.xerxestireiron.tallnether.v1_8_R3.LoadHell LH8R3;
    private com.minefit.xerxestireiron.tallnether.v1_9_R1.LoadHell LH9R1;
    private com.minefit.xerxestireiron.tallnether.v1_9_R2.LoadHell LH9R2;
    private com.minefit.xerxestireiron.tallnether.v1_10_R1.LoadHell LH10R1;
    private com.minefit.xerxestireiron.tallnether.v1_11_R1.LoadHell LH11R1;
    private com.minefit.xerxestireiron.tallnether.v1_12_R1.LoadHell LH12R1;

    public ManageHell(World world, TallNether instance) {
        this.plugin = instance;
        this.world = world;
        ConfigurationSection worldConfig = this.plugin.getConfig()
                .getConfigurationSection("worlds." + this.world.getName());

        if (this.plugin.version.equals("v1_8_R3")) {
            this.LH8R3 = new com.minefit.xerxestireiron.tallnether.v1_8_R3.LoadHell(this.world, worldConfig,
                    this.plugin.getName());
            this.plugin.getServer().getPluginManager().registerEvents(this.LH8R3, this.plugin);
        } else if (this.plugin.version.equals("v1_9_R1")) {
            this.LH9R1 = new com.minefit.xerxestireiron.tallnether.v1_9_R1.LoadHell(this.world, worldConfig,
                    this.plugin.getName());
            this.plugin.getServer().getPluginManager().registerEvents(this.LH9R1, this.plugin);
        } else if (this.plugin.version.equals("v1_9_R2")) {
            this.LH9R2 = new com.minefit.xerxestireiron.tallnether.v1_9_R2.LoadHell(this.world, worldConfig,
                    this.plugin.getName());
            this.plugin.getServer().getPluginManager().registerEvents(this.LH9R2, this.plugin);
        } else if (this.plugin.version.equals("v1_10_R1")) {
            this.LH10R1 = new com.minefit.xerxestireiron.tallnether.v1_10_R1.LoadHell(this.world, worldConfig,
                    this.plugin.getName());
            this.plugin.getServer().getPluginManager().registerEvents(this.LH10R1, this.plugin);
        } else if (this.plugin.version.equals("v1_11_R1")) {
            this.LH11R1 = new com.minefit.xerxestireiron.tallnether.v1_11_R1.LoadHell(this.world, worldConfig,
                    this.plugin.getName());
            this.plugin.getServer().getPluginManager().registerEvents(this.LH11R1, this.plugin);
        } else if (this.plugin.version.equals("v1_12_R1")) {
            this.LH12R1 = new com.minefit.xerxestireiron.tallnether.v1_12_R1.LoadHell(this.world, worldConfig,
                    this.plugin.getName());
            this.plugin.getServer().getPluginManager().registerEvents(this.LH12R1, this.plugin);
        }
    }

    // Always good to clean up when disabling a plugin
    // Especially if it's a /reload command
    public void restoreGenerator() {
        if (this.plugin.version.equals("v1_8_R3")) {
            this.LH8R3.restoreGenerator();
        } else if (this.plugin.version.equals("v1_9_R1")) {
            this.LH9R1.restoreGenerator();
        } else if (this.plugin.version.equals("v1_9_R2")) {
            this.LH9R2.restoreGenerator();
        } else if (this.plugin.version.equals("v1_10_R1")) {
            this.LH10R1.restoreGenerator();
        } else if (this.plugin.version.equals("v1_11_R1")) {
            this.LH11R1.restoreGenerator();
        } else if (this.plugin.version.equals("v1_12_R1")) {
            this.LH12R1.restoreGenerator();
        }
    }
}
