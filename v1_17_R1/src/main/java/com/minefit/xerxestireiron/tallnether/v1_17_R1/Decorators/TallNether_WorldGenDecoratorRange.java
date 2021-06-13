package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.world.level.levelgen.placement.VerticalDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorContext;

public class TallNether_WorldGenDecoratorRange extends VerticalDecorator<WorldGenFeatureChanceDecoratorRangeConfiguration> {

    public TallNether_WorldGenDecoratorRange(Codec<WorldGenFeatureChanceDecoratorRangeConfiguration> codec) {
        super(codec);
    }

    protected int a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, int i) {
        return worldgenfeaturechancedecoratorrangeconfiguration.c.a(random, worldgendecoratorcontext);
    }
}