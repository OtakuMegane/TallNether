package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorContext;

public abstract class TallNether_VerticalDecorator<DC extends WorldGenFeatureDecoratorConfiguration>
        extends WorldGenDecorator<DC> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_VerticalDecorator(Codec<DC> codec) {
        super(codec);
    }

    protected abstract int a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc, int i);

    // Custom method for TallNether
    protected abstract Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc, BlockPosition blockposition, WorldConfig worldConfig);

    // Custom for TallNether to pass blockposition and world config
    @Override
    public final Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc, BlockPosition blockposition) {
        GeneratorAccessSeed generatorAccessSeed = worldgendecoratorcontext.d();
        String worldName = generatorAccessSeed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);
        return this.a(worldgendecoratorcontext, random, dc, blockposition, worldConfig);

        // The vanilla return
        //return Stream.of(new BlockPosition(blockposition.getX(), this.a(worldgendecoratorcontext, random, dc, blockposition.getY()), blockposition.getZ()));
    }
}