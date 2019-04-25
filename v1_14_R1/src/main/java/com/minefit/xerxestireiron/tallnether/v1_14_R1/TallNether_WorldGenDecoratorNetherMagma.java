package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.ChunkGenerator;
import net.minecraft.server.v1_14_R1.GeneratorAccess;
import net.minecraft.server.v1_14_R1.GeneratorSettingsDefault;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorNetherMagma;

public class TallNether_WorldGenDecoratorNetherMagma extends WorldGenDecoratorNetherMagma {
    private final ConfigValues configValues;

    public TallNether_WorldGenDecoratorNetherMagma(Function<Dynamic<?>, ? extends WorldGenDecoratorFrequencyConfiguration> function, ConfigValues configValues) {
        super(function);
        this.configValues = configValues;
    }

    @Override
    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettingsDefault> chunkgenerator, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        int i = generatoraccess.getSeaLevel() / 2 + 1;

        return IntStream.range(0, worldgendecoratorfrequencyconfiguration.a).mapToObj((j) -> {
            int k = random.nextInt(16);
            int l = this.configValues.magmaMinHeight + random.nextInt(this.configValues.magmaRangeSize);
            int i1 = random.nextInt(16);

            return blockposition.b(k, l, i1);
        });
    }
}