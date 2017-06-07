package com.minefit.XerxesTireIron.TallNether;

import org.bukkit.World;

public class ManageHell {

    private final TallNether plugin;
    private final World world;
    private com.minefit.XerxesTireIron.TallNether.v1_8_R2.LoadHell LH8R2;
    private com.minefit.XerxesTireIron.TallNether.v1_8_R3.LoadHell LH8R3;
    private com.minefit.XerxesTireIron.TallNether.v1_9_R1.LoadHell LH9R1;
    private com.minefit.XerxesTireIron.TallNether.v1_9_R2.LoadHell LH9R2;
    private com.minefit.XerxesTireIron.TallNether.v1_10_R1.LoadHell LH10R1;
    private com.minefit.XerxesTireIron.TallNether.v1_11_R1.LoadHell LH11R1;
    private com.minefit.XerxesTireIron.TallNether.v1_12_R1.LoadHell LH12R1;

    public ManageHell(World world, TallNether instance) {
        this.plugin = instance;
        this.world = world;

        if (this.plugin.version.equals("v1_8_R2")) {
            this.LH8R2 = new com.minefit.XerxesTireIron.TallNether.v1_8_R2.LoadHell(this.world, this.plugin);
            this.plugin.getServer().getPluginManager().registerEvents(this.LH8R2, this.plugin);
        } else if (this.plugin.version.equals("v1_8_R3")) {
            this.LH8R3 = new com.minefit.XerxesTireIron.TallNether.v1_8_R3.LoadHell(this.world, this.plugin);
            this.plugin.getServer().getPluginManager().registerEvents(this.LH8R3, this.plugin);
        } else if (this.plugin.version.equals("v1_9_R1")) {
            this.LH9R1 = new com.minefit.XerxesTireIron.TallNether.v1_9_R1.LoadHell(this.world, this.plugin);
            this.plugin.getServer().getPluginManager().registerEvents(this.LH9R1, this.plugin);
        } else if (this.plugin.version.equals("v1_9_R2")) {
            this.LH9R2 = new com.minefit.XerxesTireIron.TallNether.v1_9_R2.LoadHell(this.world, this.plugin);
            this.plugin.getServer().getPluginManager().registerEvents(this.LH9R2, this.plugin);
        } else if (this.plugin.version.equals("v1_10_R1")) {
            this.LH10R1 = new com.minefit.XerxesTireIron.TallNether.v1_10_R1.LoadHell(this.world, this.plugin);
            this.plugin.getServer().getPluginManager().registerEvents(this.LH10R1, this.plugin);
        } else if (this.plugin.version.equals("v1_11_R1")) {
            this.LH11R1 = new com.minefit.XerxesTireIron.TallNether.v1_11_R1.LoadHell(this.world, this.plugin);
            this.plugin.getServer().getPluginManager().registerEvents(this.LH11R1, this.plugin);
        } else if (this.plugin.version.equals("v1_12_R1")) {
            this.LH12R1 = new com.minefit.XerxesTireIron.TallNether.v1_12_R1.LoadHell(this.world, this.plugin);
            this.plugin.getServer().getPluginManager().registerEvents(this.LH12R1, this.plugin);
        }
    }

    // Always good to clean up when disabling a plugin
    // Especially if it's a /reload command
    public void restoreGenerator() {
        if (this.plugin.version.equals("v1_8_R2")) {
            this.LH8R2.restoreGenerator();
        } else if (this.plugin.version.equals("v1_8_R3")) {
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
