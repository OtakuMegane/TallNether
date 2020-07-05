package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R1.BlockPosition;
import net.minecraft.server.v1_16_R1.GeneratorAccess;
import net.minecraft.server.v1_16_R1.WorldGenDecoratorHeightAverageConfiguration;

public class TallNether_WorldGenDecoratorHeightAverage extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorHeightAverageConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String blockType;

    public TallNether_WorldGenDecoratorHeightAverage(Codec<WorldGenDecoratorHeightAverageConfiguration> codec, String blockType) {
        super(codec);
        this.blockType = blockType;
    }

    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, Random random, WorldGenDecoratorHeightAverageConfiguration worldgendecoratorheightaverageconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if(worldConfig == null || worldConfig.isVanilla) {
            return a(random, worldgendecoratorheightaverageconfiguration, blockposition);
        }

        String biomeName = this.configAccessor.biomeClassToConfig(generatoraccess.getBiome(blockposition).getClass().getSimpleName());
        BiomeValues biomeConfig = worldConfig.getBiomeValues(biomeName);
        int attempts;
        int min;
        int max;
        int size;
        int median;

        if (this.blockType.equals("ancient-debris")) {
            attempts = biomeConfig.values.get("ancient-debris1-attempts");
            max = biomeConfig.values.get("ancient-debris1-max-height");
            min = biomeConfig.values.get("ancient-debris1-min-height");
            size = biomeConfig.values.get("ancient-debris1-range-size");
            median = biomeConfig.values.get("ancient-debris1-range-median");
        } else {
            return a(random, worldgendecoratorheightaverageconfiguration, blockposition);
        }

        size = size > 0 ? size : 1;
        return a(random, worldgendecoratorheightaverageconfiguration, blockposition, attempts, min, max, median, size);
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorHeightAverageConfiguration worldgendecoratorheightaverageconfiguration, BlockPosition blockposition, int attempts, int min, int max, int median, int size) {
        return IntStream.range(0, attempts).mapToObj((l) -> {
            int i1 = random.nextInt(16) + blockposition.getX();
            int j1 = random.nextInt(16) + blockposition.getZ();
            int k1 = min + random.nextInt(size);

            return new BlockPosition(i1, k1, j1);
        });
    }

    // TallNether: Vanilla generation
    @Override
    protected Stream<BlockPosition> a(Random random, WorldGenDecoratorHeightAverageConfiguration worldgendecoratorheightaverageconfiguration, BlockPosition blockposition) {
        int i = worldgendecoratorheightaverageconfiguration.b;
        int j = worldgendecoratorheightaverageconfiguration.c;
        int k = worldgendecoratorheightaverageconfiguration.d;

        return IntStream.range(0, i).mapToObj((l) -> {
            int i1 = random.nextInt(16) + blockposition.getX();
            int j1 = random.nextInt(16) + blockposition.getZ();
            int k1 = random.nextInt(k) + random.nextInt(k) - k + j;

            return new BlockPosition(i1, k1, j1);
        });
    }
}