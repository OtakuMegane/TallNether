package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.ChunkGenerator;
import net.minecraft.server.v1_16_R2.GeneratorAccess;
import net.minecraft.server.v1_16_R2.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorNetherMagma;
import net.minecraft.server.v1_16_R2.WorldGenFeatureEmptyConfiguration2;

public class TallNether_WorldGenDecoratorNetherMagma extends WorldGenDecoratorNetherMagma {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorNetherMagma(Codec<WorldGenFeatureEmptyConfiguration2> codec) {
        super(codec);
    }

    public Stream<BlockPosition> a(GeneratorAccessSeed generatoraccessseed, ChunkGenerator chunkgenerator, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if(worldConfig == null || worldConfig.isVanilla) {
            int i = generatoraccessseed.getSeaLevel() / 2 + 1;
            int j = i - 5 + random.nextInt(10);

            return Stream.of(new BlockPosition(blockposition.getX(), j, blockposition.getZ()));
        }

        String biomeName = this.configAccessor.biomeClassToConfig(generatoraccessseed.getBiome(blockposition).getClass().getSimpleName());
        BiomeValues biomeConfig = worldConfig.getBiomeValues(biomeName);
        int attempts = biomeConfig.values.get("magma-block-attempts");
        int max = biomeConfig.values.get("magma-block-max-height");
        int min = biomeConfig.values.get("magma-block-min-height");
        int size = biomeConfig.values.get("magma-block-range-size");
        int median = biomeConfig.values.get("magma-block-range-median");
        size = size > 0 ? size : 1;
        return a(generatoraccessseed, chunkgenerator, random, worldgendecoratorfrequencyconfiguration, blockposition, attempts, min, max, median, size);
    }

    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, ChunkGenerator chunkgenerator, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, int attempts, int min, int max, int median, int size) {
        return IntStream.range(0, attempts).mapToObj((j) -> {
            int k = random.nextInt(16) + blockposition.getX();
            int l = random.nextInt(16) + blockposition.getZ();
            int i1 = min + random.nextInt(size);
            return new BlockPosition(k, i1, l);
        });
    }
}