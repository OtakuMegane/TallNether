package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.ChunkGenerator;
import net.minecraft.server.v1_16_R2.GeneratorAccess;
import net.minecraft.server.v1_16_R2.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R2.GeneratorSettingBase;
import net.minecraft.server.v1_16_R2.WorldGenDecorator;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorContext;
import net.minecraft.server.v1_16_R2.WorldGenFeatureDecoratorConfiguration;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class TallNether_WorldGenDecoratorFeatureSimple<DC extends WorldGenFeatureDecoratorConfiguration> extends WorldGenDecorator<DC> {

    public TallNether_WorldGenDecoratorFeatureSimple(Codec<DC> codec) {
        super(codec);
    }

    @Override
    public final Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc, BlockPosition blockposition) {
        GeneratorAccessSeed generatoraccessseed;

        try {
            Field aField = ReflectionHelper.getField(worldgendecoratorcontext.getClass(), "a", true);
            aField.setAccessible(true);
            generatoraccessseed = (GeneratorAccessSeed) aField.get(worldgendecoratorcontext);
        } catch (Exception e) {
            return this.a(random, dc, blockposition);
        }

        return this.a(generatoraccessseed, random, dc, blockposition);
    }


    protected abstract Stream<BlockPosition> a(Random random, DC dc, BlockPosition blockposition);

    // TallNether: Provides GeneratorAccess to methods in modified decorator classes so they can check which world and biome is being generated
    protected abstract Stream<BlockPosition> a(GeneratorAccessSeed generatoraccessseed, Random random, DC dc, BlockPosition blockposition);
}