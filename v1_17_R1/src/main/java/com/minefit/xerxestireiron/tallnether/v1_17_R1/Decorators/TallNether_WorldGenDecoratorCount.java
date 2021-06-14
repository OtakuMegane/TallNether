package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.world.level.levelgen.placement.RepeatingDecorator;

public class TallNether_WorldGenDecoratorCount extends RepeatingDecorator<WorldGenDecoratorFrequencyConfiguration> {

    public TallNether_WorldGenDecoratorCount(Codec<WorldGenDecoratorFrequencyConfiguration> codec) {
        super(codec);
    }

    protected int a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        System.out.println(blockposition);
        return worldgendecoratorfrequencyconfiguration.a().a(random);
    }
}
