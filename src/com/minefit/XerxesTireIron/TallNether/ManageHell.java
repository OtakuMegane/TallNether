package com.minefit.XerxesTireIron.TallNether;

import org.bukkit.World;

public class ManageHell {

    private TallNether plugin;
    private World world;
    private com.minefit.XerxesTireIron.TallNether.v1_8_R2.LoadHell LH8R2;
    private com.minefit.XerxesTireIron.TallNether.v1_8_R3.LoadHell LH8R3;
    private com.minefit.XerxesTireIron.TallNether.v1_9_R1.LoadHell LH9R1;

    public ManageHell(World world, TallNether instance) {
        this.plugin = instance;
        this.world = world;

        if (this.plugin.version.equals("v1_8_R2")) {
            this.LH8R2 = new com.minefit.XerxesTireIron.TallNether.v1_8_R2.LoadHell(this.world, this.plugin);
        } else if (this.plugin.version.equals("v1_8_R3")) {
            this.LH8R3 = new com.minefit.XerxesTireIron.TallNether.v1_8_R3.LoadHell(this.world, this.plugin);
        } else if (this.plugin.version.equals("v1_9_R1")) {
            this.LH9R1 = new com.minefit.XerxesTireIron.TallNether.v1_9_R1.LoadHell(this.world, this.plugin);
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
        }
    }
}
