package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import net.minecraft.server.v1_14_R1.GeneratorSettingsNether;

public class TallNether_GeneratorSettingsNether extends GeneratorSettingsNether {

    public TallNether_GeneratorSettingsNether() {}

    @Override
    public int u() {
        return 0;
    }

    // TallNether: Default 127, change to 255
    @Override
    public int t() {
        return 255;
    }
}

