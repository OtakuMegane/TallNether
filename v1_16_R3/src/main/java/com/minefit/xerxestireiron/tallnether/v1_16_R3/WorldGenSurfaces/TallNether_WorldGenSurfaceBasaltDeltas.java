package com.minefit.xerxestireiron.tallnether.v1_16_R3.WorldGenSurfaces;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.WorldGenSurfaceConfigurationBase;

public class TallNether_WorldGenSurfaceBasaltDeltas extends TallNether_WorldGenSurfaceNetherAbstract {

    private static final IBlockData a = Blocks.BASALT.getBlockData();
    private static final IBlockData b = Blocks.BLACKSTONE.getBlockData();
    private static final IBlockData c = Blocks.GRAVEL.getBlockData();
    private static final ImmutableList<IBlockData> d = ImmutableList.of(TallNether_WorldGenSurfaceBasaltDeltas.a, TallNether_WorldGenSurfaceBasaltDeltas.b);
    private static final ImmutableList<IBlockData> e = ImmutableList.of(TallNether_WorldGenSurfaceBasaltDeltas.a);

    public TallNether_WorldGenSurfaceBasaltDeltas(Codec<WorldGenSurfaceConfigurationBase> codec) {
        super(codec);
    }

    @Override
    protected ImmutableList<IBlockData> a() {
        return TallNether_WorldGenSurfaceBasaltDeltas.d;
    }

    @Override
    protected ImmutableList<IBlockData> b() {
        return TallNether_WorldGenSurfaceBasaltDeltas.e;
    }

    @Override
    protected IBlockData c() {
        return TallNether_WorldGenSurfaceBasaltDeltas.c;
    }
}
