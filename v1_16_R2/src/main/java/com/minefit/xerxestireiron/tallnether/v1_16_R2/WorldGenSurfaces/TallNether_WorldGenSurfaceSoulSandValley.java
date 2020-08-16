package com.minefit.xerxestireiron.tallnether.v1_16_R2.WorldGenSurfaces;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.Blocks;
import net.minecraft.server.v1_16_R2.IBlockData;
import net.minecraft.server.v1_16_R2.WorldGenSurfaceConfigurationBase;

public class TallNether_WorldGenSurfaceSoulSandValley extends TallNether_WorldGenSurfaceNetherAbstract {

    private static final IBlockData a = Blocks.SOUL_SAND.getBlockData();
    private static final IBlockData b = Blocks.SOUL_SOIL.getBlockData();
    private static final IBlockData c = Blocks.GRAVEL.getBlockData();
    private static final ImmutableList<IBlockData> d = ImmutableList.of(TallNether_WorldGenSurfaceSoulSandValley.a, TallNether_WorldGenSurfaceSoulSandValley.b);

    public TallNether_WorldGenSurfaceSoulSandValley(Codec<WorldGenSurfaceConfigurationBase> codec) {
        super(codec);
    }

    @Override
    protected ImmutableList<IBlockData> a() {
        return TallNether_WorldGenSurfaceSoulSandValley.d;
    }

    @Override
    protected ImmutableList<IBlockData> b() {
        return TallNether_WorldGenSurfaceSoulSandValley.d;
    }

    @Override
    protected IBlockData c() {
        return TallNether_WorldGenSurfaceSoulSandValley.c;
    }
}
