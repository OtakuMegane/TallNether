package com.minefit.xerxestireiron.tallnether.v1_15_R1;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.ChunkGenerator;
import net.minecraft.server.v1_15_R1.GeneratorAccess;
import net.minecraft.server.v1_15_R1.GeneratorSettingsDefault;
import net.minecraft.server.v1_15_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenDecoratorNetherMagma;

public class TallNether_WorldGenDecoratorNetherMagma extends WorldGenDecoratorNetherMagma {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorNetherMagma(Function<Dynamic<?>, ? extends WorldGenDecoratorFrequencyConfiguration> function) {
        super(function);
    }

    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettingsDefault> chunkgenerator, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        int attempts = this.configAccessor.getConfig(worldName).magmaAttempts;
        int min = this.configAccessor.getConfig(worldName).magmaMinHeight;
        int max = this.configAccessor.getConfig(worldName).magmaMaxHeight;
        int median = this.configAccessor.getConfig(worldName).magmaRangeMedian;
        int size = this.configAccessor.getConfig(worldName).magmaRangeSize;
        size = size >0 ? size : 1;
        return a(generatoraccess, chunkgenerator, random, worldgendecoratorfrequencyconfiguration, blockposition, attempts, min, max, median, size);
    }

    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettingsDefault> chunkgenerator, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, int attempts, int min, int max, int median, int size) {
        int i = generatoraccess.getSeaLevel() / 2 + 1;

        return IntStream.range(0, attempts).mapToObj((j) -> {
            int k = random.nextInt(16);
            int l = min + random.nextInt(size);
            int i1 = random.nextInt(16);

            return blockposition.b(k, l, i1);
        });
    }
}