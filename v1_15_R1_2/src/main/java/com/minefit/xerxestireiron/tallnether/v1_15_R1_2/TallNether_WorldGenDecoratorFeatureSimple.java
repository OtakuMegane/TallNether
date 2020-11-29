package com.minefit.xerxestireiron.tallnether.v1_15_R1_2;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.ChunkGenerator;
import net.minecraft.server.v1_15_R1.GeneratorAccess;
import net.minecraft.server.v1_15_R1.GeneratorSettingsDefault;
import net.minecraft.server.v1_15_R1.WorldGenDecorator;
import net.minecraft.server.v1_15_R1.WorldGenFeatureDecoratorConfiguration;

public abstract class TallNether_WorldGenDecoratorFeatureSimple<DC extends WorldGenFeatureDecoratorConfiguration> extends WorldGenDecorator<DC> {

    public TallNether_WorldGenDecoratorFeatureSimple(Function<Dynamic<?>, ? extends DC> function) {
        super(function);
    }

    @Override
    public final Stream<BlockPosition> a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettingsDefault> chunkgenerator, Random random, DC dc, BlockPosition blockposition) {
        return this.a(generatoraccess, random, dc, blockposition);
    }

    protected abstract Stream<BlockPosition> a(Random random, DC dc, BlockPosition blockposition);

    // TallNether: Provides GeneratorAccess to methods in modified decorator classes so they can check which world is being generated
    protected abstract Stream<BlockPosition> a(GeneratorAccess generatorAccess, Random random, DC dc, BlockPosition blockposition);
}