package com.minefit.xerxestireiron.tallnether;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

public class ManageHell {

    private final TallNether plugin;
    private com.minefit.xerxestireiron.tallnether.v1_12_R1.LoadHell LH12R1;
    private com.minefit.xerxestireiron.tallnether.v1_13_R1.LoadHell LH13R1;
    private com.minefit.xerxestireiron.tallnether.v1_13_R2.LoadHell LH13R2;
    private com.minefit.xerxestireiron.tallnether.v1_13_R2_2.LoadHell LH13R2_2;
    private com.minefit.xerxestireiron.tallnether.v1_14_R1.LoadHell LH14R1;
    private com.minefit.xerxestireiron.tallnether.v1_15_R1.LoadHell LH15R1;
    private com.minefit.xerxestireiron.tallnether.v1_15_R1_2.LoadHell LH15R1_2;
    private com.minefit.xerxestireiron.tallnether.v1_16_R1.LoadHell LH16R1;
    private com.minefit.xerxestireiron.tallnether.v1_16_R2.LoadHell LH16R2;
    private com.minefit.xerxestireiron.tallnether.v1_16_R3.LoadHell LH16R3;

    public ManageHell(TallNether instance) {
        this.plugin = instance;
    }

    public void overrideGenerator(World world) {
        if (world.getEnvironment() != Environment.NETHER) {
            return;
        }

        ConfigurationSection worldConfig = this.plugin.getConfig().getConfigurationSection("worlds." + world.getName());

        if (this.plugin.version.equals("v1_12_R1")) {
            if (this.LH12R1 == null) {
                this.LH12R1 = new com.minefit.xerxestireiron.tallnether.v1_12_R1.LoadHell(world, worldConfig,
                        this.plugin.isPaper(), this.plugin.getName());
            }

            this.LH12R1.overrideGenerator(world);
        } else if (this.plugin.version.equals("v1_13_R1")) {
            if (this.LH13R1 == null) {
                this.LH13R1 = new com.minefit.xerxestireiron.tallnether.v1_13_R1.LoadHell(world, worldConfig,
                        this.plugin.isPaper(), this.plugin.getName());
                this.LH13R1.overrideDecorators();
            }

            this.LH13R1.addWorld(world, worldConfig);
            this.LH13R1.overrideGenerator(world);
        } else if (this.plugin.version.equals("v1_13_R2")) {
            if (Bukkit.getVersion().contains("1.13.2")) {
                if (this.LH13R2_2 == null) {
                    this.LH13R2_2 = new com.minefit.xerxestireiron.tallnether.v1_13_R2_2.LoadHell(world, worldConfig,
                            this.plugin.isPaper(), this.plugin.getName());
                    this.LH13R2_2.overrideDecorators();
                }

                this.LH13R2_2.addWorld(world, worldConfig);
                this.LH13R2_2.overrideGenerator(world);
            } else {
                if (this.LH13R2 == null) {
                    this.LH13R2 = new com.minefit.xerxestireiron.tallnether.v1_13_R2.LoadHell(world, worldConfig,
                            this.plugin.isPaper(), this.plugin.getName());
                    this.LH13R2.overrideDecorators();
                }

                this.LH13R2.addWorld(world, worldConfig);
                this.LH13R2.overrideGenerator(world);
            }
        } else if (this.plugin.version.equals("v1_14_R1")) {
            if (this.LH14R1 == null) {
                this.LH14R1 = new com.minefit.xerxestireiron.tallnether.v1_14_R1.LoadHell(worldConfig,
                        this.plugin.isPaper(), this.plugin.getName());
                this.LH14R1.overrideDecorators(true);
            }

            this.LH14R1.addWorld(world, worldConfig);
            this.LH14R1.overrideGenerator(world);
        } else if (this.plugin.version.equals("v1_15_R1")) {
            if (Bukkit.getVersion().contains("1.15.2")) {
                if (this.LH15R1_2 == null) {
                    this.LH15R1_2 = new com.minefit.xerxestireiron.tallnether.v1_15_R1_2.LoadHell(worldConfig,
                            this.plugin.isPaper(), this.plugin.getName());
                    this.LH15R1_2.overrideDecorators(true);
                }

                this.LH15R1_2.addWorld(world, worldConfig);
                this.LH15R1_2.overrideGenerator(world);
            } else {
                if (this.LH15R1 == null) {
                    this.LH15R1 = new com.minefit.xerxestireiron.tallnether.v1_15_R1.LoadHell(worldConfig,
                            this.plugin.isPaper(), this.plugin.getName());
                    this.LH15R1.overrideDecorators(true);
                }

                this.LH15R1.addWorld(world, worldConfig);
                this.LH15R1.overrideGenerator(world);
            }

        } else if (this.plugin.version.equals("v1_16_R1")) {
            if (this.LH16R1 == null) {
                this.LH16R1 = new com.minefit.xerxestireiron.tallnether.v1_16_R1.LoadHell(worldConfig,
                        this.plugin.isPaper(), this.plugin.getName());
                this.LH16R1.overrideDecorators(true);
            }

            this.LH16R1.addWorld(world, worldConfig);
            this.LH16R1.overrideGenerator(world);
        } else if (this.plugin.version.equals("v1_16_R2")) {
            if (this.LH16R2 == null) {
                this.LH16R2 = new com.minefit.xerxestireiron.tallnether.v1_16_R2.LoadHell(worldConfig,
                        this.plugin.isPaper(), this.plugin.getName());
            }

            this.LH16R2.addWorld(world, worldConfig);
            this.LH16R2.overrideGenerator(world);
        } else if (this.plugin.version.equals("v1_16_R3")) {
            if (this.LH16R3 == null) {
                this.LH16R3 = new com.minefit.xerxestireiron.tallnether.v1_16_R3.LoadHell(worldConfig,
                        this.plugin.isPaper(), this.plugin.getName());
            }

            this.LH16R3.addWorld(world, worldConfig);
            this.LH16R3.overrideGenerator(world);
        }
    }

    public void removeWorld(World world) {
        if (this.plugin.version.equals("v1_12_R1")) {
            ;
        } else if (this.plugin.version.equals("v1_13_R1")) {
            this.LH13R1.removeWorld(world);
        } else if (this.plugin.version.equals("v1_13_R2")) {
            if (Bukkit.getVersion().contains("1.13.2")) {
                this.LH13R2_2.removeWorld(world);
            } else {
                this.LH13R2.removeWorld(world);
            }
        } else if (this.plugin.version.equals("v1_14_R1")) {
            this.LH14R1.removeWorld(world);
        } else if (this.plugin.version.equals("v1_15_R1")) {
            if (Bukkit.getVersion().contains("1.15.2")) {
                this.LH15R1_2.removeWorld(world);
            } else {
                this.LH15R1.removeWorld(world);
            }
        } else if (this.plugin.version.equals("v1_16_R1")) {
            this.LH16R1.removeWorld(world);
        } else if (this.plugin.version.equals("v1_16_R2")) {
            this.LH16R2.removeWorld(world);
        }
    }

    public void restoreGenerator(World world) {
        if (this.plugin.version.equals("v1_12_R1")) {
            this.LH12R1.restoreGenerator();
        } else if (this.plugin.version.equals("v1_13_R1")) {
            this.LH13R1.restoreGenerator(world);
        } else if (this.plugin.version.equals("v1_13_R2")) {
            if (Bukkit.getVersion().contains("1.13.2")) {
                this.LH13R2_2.restoreGenerator(world);
            } else {
                this.LH13R2.restoreGenerator(world);
            }
        } else if (this.plugin.version.equals("v1_14_R1")) {
            this.LH14R1.restoreGenerator(world);
        } else if (this.plugin.version.equals("v1_15_R1")) {
            if (Bukkit.getVersion().contains("1.15.2")) {
                this.LH15R1_2.restoreGenerator(world);
            } else {
                this.LH15R1.restoreGenerator(world);
            }
        } else if (this.plugin.version.equals("v1_16_R1")) {
            this.LH16R1.restoreGenerator(world);
        } else if (this.plugin.version.equals("v1_16_R2")) {
            this.LH16R2.restoreGenerator(world);
        } else if (this.plugin.version.equals("v1_16_R3")) {
            this.LH16R3.restoreGenerator(world);
        }
    }

    // Should be called on plugin disable or server shutdown
    public void unloadHell() {
        if (this.plugin.version.equals("v1_12_R1")) {
            this.LH12R1 = null;
        } else if (this.plugin.version.equals("v1_13_R1")) {
            this.LH13R1 = null;
        } else if (this.plugin.version.equals("v1_13_R2")) {
            if (Bukkit.getVersion().contains("1.13.2")) {
                this.LH13R2_2 = null;
            } else {
                this.LH13R2 = null;
            }
        } else if (this.plugin.version.equals("v1_14_R1")) {
            this.LH14R1 = null;
        } else if (this.plugin.version.equals("v1_15_R1")) {
            if (Bukkit.getVersion().contains("1.15.2")) {
                this.LH15R1_2 = null;
            } else {
                this.LH15R1 = null;
            }
        } else if (this.plugin.version.equals("v1_16_R1")) {
            this.LH16R1 = null;
        } else if (this.plugin.version.equals("v1_16_R2")) {
            this.LH16R2 = null;
        } else if (this.plugin.version.equals("v1_16_R3")) {
            this.LH16R3 = null;
        }
    }
}
