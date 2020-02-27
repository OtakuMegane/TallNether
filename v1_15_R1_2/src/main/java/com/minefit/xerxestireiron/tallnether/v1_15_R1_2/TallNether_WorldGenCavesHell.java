package com.minefit.xerxestireiron.tallnether.v1_15_R1_2;

import net.minecraft.server.v1_15_R1.Block;
import net.minecraft.server.v1_15_R1.Blocks;
import net.minecraft.server.v1_15_R1.FluidTypes;
import net.minecraft.server.v1_15_R1.WorldGenCaves;
import net.minecraft.server.v1_15_R1.WorldGenFeatureConfigurationChance;

import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

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
}

