package com.minefit.xerxestireiron.tallnether.v1_15_R1_2;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_15_R1.BiomeBase;
import net.minecraft.server.v1_15_R1.Block;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.Blocks;
import net.minecraft.server.v1_15_R1.FluidTypes;
import net.minecraft.server.v1_15_R1.IBlockData;
import net.minecraft.server.v1_15_R1.IChunkAccess;
import net.minecraft.server.v1_15_R1.WorldGenCaves;
import net.minecraft.server.v1_15_R1.WorldGenCavesHell;
import net.minecraft.server.v1_15_R1.WorldGenFeatureConfigurationChance;

public class TallNether_WorldGenCavesHell extends WorldGenCaves {

    public TallNether_WorldGenCavesHell(Function<Dynamic<?>, ? extends WorldGenFeatureConfigurationChance> function) {
        // TallNether: Minecraft default is 128, change to 256
        super(function, 256);
        this.j = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, new Block[] { Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.NETHERRACK});
        this.k = ImmutableSet.of(FluidTypes.LAVA, FluidTypes.WATER);
    }

    @Override
    protected int a() {
        return 10;
    }

    @Override
    protected float a(Random random) {
        return (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
    }

    @Override
    protected double b() {
        return 5.0D;
    }

    @Override
    protected int b(Random random) {
        return random.nextInt(this.l);
    }

    @Override
    protected boolean a(IChunkAccess ichunkaccess, Function<BlockPosition, BiomeBase> function, BitSet bitset, Random random, BlockPosition.MutableBlockPosition blockposition_mutableblockposition, BlockPosition.MutableBlockPosition blockposition_mutableblockposition1, BlockPosition.MutableBlockPosition blockposition_mutableblockposition2, int i, int j, int k, int l, int i1, int j1, int k1, int l1, AtomicBoolean atomicboolean) {
        int i2 = j1 | l1 << 4 | k1 << 8;

        if (bitset.get(i2)) {
            return false;
        } else {
            bitset.set(i2);
            blockposition_mutableblockposition.d(l, k1, i1);
            if (this.a(ichunkaccess.getType(blockposition_mutableblockposition))) {
                IBlockData iblockdata;

                if (k1 <= 31) {
                    iblockdata = WorldGenCavesHell.i.getBlockData();
                } else {
                    iblockdata = WorldGenCavesHell.g;
                }

                ichunkaccess.setType(blockposition_mutableblockposition, iblockdata, false);
                return true;
            } else {
                return false;
            }
        }
    }
}

