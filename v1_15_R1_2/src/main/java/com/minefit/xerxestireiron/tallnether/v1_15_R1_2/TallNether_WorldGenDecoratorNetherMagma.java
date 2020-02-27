package com.minefit.xerxestireiron.tallnether.v1_15_R1_2;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
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

    // TallNether: Methods added to allow per-world config access through GeneratorAccess
    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettingsDefault> chunkgenerator, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        ConfigValues worldConfig = this.configAccessor.getConfig(worldName);
        int attempts = worldConfig.magmaAttempts;
        int min = worldConfig.magmaMinHeight;
        int max = worldConfig.magmaMaxHeight;
        int median = worldConfig.magmaRangeMedian;
        int size = worldConfig.magmaRangeSize;
        size = size >0 ? size : 1;
        return a(generatoraccess, chunkgenerator, random, worldgendecoratorfrequencyconfiguration, blockposition, attempts, min, max, median, size);
    }

    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettingsDefault> chunkgenerator, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, int attempts, int min, int max, int median, int size) {
        return IntStream.range(0, attempts).mapToObj((j) -> {
            int k = random.nextInt(16);
            int l = min + random.nextInt(size);
            int i1 = random.nextInt(16);

            return blockposition.b(k, l, i1);
        });
    }
}