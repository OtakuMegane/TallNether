package com.minefit.xerxestireiron.tallnether;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class ManageHell {

    private final TallNether plugin;
    private final World world;
    private com.minefit.xerxestireiron.tallnether.v1_12_R1.LoadHell LH12R1;
    private com.minefit.xerxestireiron.tallnether.v1_13_R1.LoadHell LH13R1;
    private com.minefit.xerxestireiron.tallnether.v1_13_R2_2.LoadHell LH13R2;
    private com.minefit.xerxestireiron.tallnether.v1_13_R2_2.LoadHell LH13R2_2;

    public ManageHell(World world, TallNether instance) {
        this.plugin = instance;
        this.world = world;
        ConfigurationSection worldConfig = this.plugin.getConfig()
                .getConfigurationSection("worlds." + this.world.getName());

        if (this.plugin.version.equals("v1_12_R1")) {
            this.LH12R1 = new com.minefit.xerxestireiron.tallnether.v1_12_R1.LoadHell(this.world, worldConfig,
                    this.plugin.getName());
        } else if (this.plugin.version.equals("v1_13_R1")) {
            this.LH13R1 = new com.minefit.xerxestireiron.tallnether.v1_13_R1.LoadHell(this.world, worldConfig,
                    this.plugin.getName());
        } else if (this.plugin.version.equals("v1_13_R2")) {
            if (this.plugin.serverVersion.getRevision().equals("2")) {
                this.LH13R2_2 = new com.minefit.xerxestireiron.tallnether.v1_13_R2_2.LoadHell(this.world, worldConfig,
                        this.plugin.getName());
            } else {
                this.LH13R2 = new com.minefit.xerxestireiron.tallnether.v1_13_R2_2.LoadHell(this.world, worldConfig,
                        this.plugin.getName());
            }

        }
    }

    // Always good to clean up when disabling a plugin
    // Especially if it's a /reload command
    public void restoreGenerator() {
        if (this.plugin.version.equals("v1_12_R1")) {
            this.LH12R1.restoreGenerator();
        } else if (this.plugin.version.equals("v1_13_R1")) {
            this.LH13R1.restoreGenerator();
        } else if (this.plugin.version.equals("v1_13_R2")) {
            if (this.plugin.serverVersion.getRevision().equals("2")) {
                this.LH13R2_2.restoreGenerator();
            } else {
                this.LH13R2.restoreGenerator();
            }

        }
    }
}
