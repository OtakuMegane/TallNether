package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import net.minecraft.server.v1_13_R1.BiomeBase;
import net.minecraft.server.v1_13_R1.BiomeHell;
import net.minecraft.server.v1_13_R1.WorldGenSurfaceComposite;

public class TallNether_BiomeBase extends BiomeBase {

    @SuppressWarnings("unchecked")
    protected TallNether_BiomeBase() {
        super((new BiomeBase.a()).a(new WorldGenSurfaceComposite(BiomeHell.aF, BiomeHell.at))
                .a(BiomeBase.Precipitation.NONE).a(BiomeBase.Geography.NETHER).a(0.1F).b(0.2F).c(2.0F).d(0.0F)
                .a(4159204).b(329011).a((String) null));
    }



}
