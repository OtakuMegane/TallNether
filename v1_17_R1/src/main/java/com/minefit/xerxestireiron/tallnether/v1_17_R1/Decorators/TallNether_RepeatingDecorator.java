package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorContext;

public abstract class TallNether_RepeatingDecorator<DC extends WorldGenFeatureDecoratorConfiguration> extends WorldGenDecorator<DC> {

    public TallNether_RepeatingDecorator(Codec<DC> codec) {
        super(codec);
    }

    protected abstract int a(Random random, DC dc, BlockPosition blockposition);

    protected abstract int a(Random random, DC dc, BlockPosition blockposition, WorldGenDecoratorContext worldgendecoratorcontext);

    @Override
    public Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc, BlockPosition blockposition) {
        return IntStream.range(0, this.a(random, dc, blockposition, worldgendecoratorcontext)).mapToObj((i) -> {
            return blockposition;
        });
    }
}
