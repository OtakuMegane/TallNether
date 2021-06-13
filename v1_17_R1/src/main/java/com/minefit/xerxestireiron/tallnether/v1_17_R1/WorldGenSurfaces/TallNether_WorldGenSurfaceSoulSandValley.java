package com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldGenSurfaces;

import com.google.common.collect.ImmutableList;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceConfigurationBase;

public class TallNether_WorldGenSurfaceSoulSandValley extends TallNether_WorldGenSurfaceNetherAbstract {

    private static final IBlockData SOUL_SAND = TBlocks.SOUL_SAND.getBlockData();
    private static final IBlockData SOUL_SOIL = TBlocks.SOUL_SOIL.getBlockData();
    private static final IBlockData GRAVEL = TBlocks.GRAVEL.getBlockData();
    private static final ImmutableList<IBlockData> BLOCK_STATES = ImmutableList.of(TallNether_WorldGenSurfaceSoulSandValley.SOUL_SAND, TallNether_WorldGenSurfaceSoulSandValley.SOUL_SOIL);

    public TallNether_WorldGenSurfaceSoulSandValley(Codec<WorldGenSurfaceConfigurationBase> codec) {
        super(codec);
    }

    @Override
    protected ImmutableList<IBlockData> a() {
        return TallNether_WorldGenSurfaceSoulSandValley.BLOCK_STATES;
    }

    @Override
    protected ImmutableList<IBlockData> b() {
        return TallNether_WorldGenSurfaceSoulSandValley.BLOCK_STATES;
    }

    @Override
    protected IBlockData c() {
        return TallNether_WorldGenSurfaceSoulSandValley.GRAVEL;
    }
}
