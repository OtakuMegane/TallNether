package com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldGenSurfaces;

import com.google.common.collect.ImmutableList;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceConfigurationBase;

public class TallNether_WorldGenSurfaceBasaltDeltas extends TallNether_WorldGenSurfaceNetherAbstract {

    private static final IBlockData BASALT = TBlocks.BASALT.getBlockData();
    private static final IBlockData BLACKSTONE = TBlocks.BLACKSTONE.getBlockData();
    private static final IBlockData GRAVEL = TBlocks.GRAVEL.getBlockData();
    private static final ImmutableList<IBlockData> FLOOR_BLOCK_STATES = ImmutableList.of(TallNether_WorldGenSurfaceBasaltDeltas.BASALT, TallNether_WorldGenSurfaceBasaltDeltas.BLACKSTONE);
    private static final ImmutableList<IBlockData> CEILING_BLOCK_STATES = ImmutableList.of(TallNether_WorldGenSurfaceBasaltDeltas.BASALT);

    public TallNether_WorldGenSurfaceBasaltDeltas(Codec<WorldGenSurfaceConfigurationBase> codec) {
        super(codec);
    }

    @Override
    protected ImmutableList<IBlockData> a() {
        return TallNether_WorldGenSurfaceBasaltDeltas.FLOOR_BLOCK_STATES;
    }

    @Override
    protected ImmutableList<IBlockData> b() {
        return TallNether_WorldGenSurfaceBasaltDeltas.CEILING_BLOCK_STATES;
    }

    @Override
    protected IBlockData c() {
        return TallNether_WorldGenSurfaceBasaltDeltas.GRAVEL;
    }
}
