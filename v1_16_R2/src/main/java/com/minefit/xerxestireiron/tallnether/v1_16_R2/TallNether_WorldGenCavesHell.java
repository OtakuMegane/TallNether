package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import net.minecraft.server.v1_16_R2.BiomeBase;
import net.minecraft.server.v1_16_R2.Block;
import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.Blocks;
import net.minecraft.server.v1_16_R2.FluidTypes;
import net.minecraft.server.v1_16_R2.IBlockData;
import net.minecraft.server.v1_16_R2.IChunkAccess;
import net.minecraft.server.v1_16_R2.WorldGenCaves;
import net.minecraft.server.v1_16_R2.WorldGenCavesHell;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfigurationChance;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

import org.apache.commons.lang.mutable.MutableBoolean;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

public class TallNether_WorldGenCavesHell extends WorldGenCaves {

    public TallNether_WorldGenCavesHell(Codec<WorldGenFeatureConfigurationChance> codec) {
        // TallNether: Minecraft default is 128, change to 256
        super(codec, 256);
        this.j = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, new Block[]{Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SOUL_SOIL, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.BASALT, Blocks.BLACKSTONE});
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

    protected boolean a(IChunkAccess ichunkaccess, Function<BlockPosition, BiomeBase> function, BitSet bitset, Random random, BlockPosition.MutableBlockPosition blockposition_mutableblockposition, BlockPosition.MutableBlockPosition blockposition_mutableblockposition1, BlockPosition.MutableBlockPosition blockposition_mutableblockposition2, int i, int j, int k, int l, int i1, int j1, int k1, int l1, MutableBoolean mutableboolean) {
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

