package com.minefit.xerxestireiron.tallnether.v1_16_R2.Decorators;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorHeightAverageConfiguration;

public class TallNether_WorldGenDecoratorDepthAverage extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorHeightAverageConfiguration> {

    private final String block;

    public TallNether_WorldGenDecoratorDepthAverage(Codec<WorldGenDecoratorHeightAverageConfiguration> codec, String biome, String block) {
        super(codec, biome);
        this.block = block;
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorHeightAverageConfiguration worldgendecoratorheightaverageconfiguration, BlockPosition blockposition, BiomeValues biomevalues, boolean vanilla) {
        if (vanilla) {
            //return a(random, worldgendecoratorheightaverageconfiguration, blockposition);
        }

        int attempts = biomevalues.values.get(this.block + "-attempts");
        int minHeight = biomevalues.values.get(this.block + "-min-height");
        int size = biomevalues.values.get(this.block + "-range-size");
        int rangeSize = size > 0 ? size : 1;

        // TallNether: Double the attempts to scale with the extra height
        return IntStream.range(0, attempts).mapToObj((l) -> {
            int i1 = random.nextInt(16) + blockposition.getX();
            int j1 = random.nextInt(16) + blockposition.getZ();
            int k1 = minHeight + random.nextInt(rangeSize);

            return new BlockPosition(i1, k1, j1);
        });
    }

    // TallNether: Vanilla generation
    public Stream<BlockPosition> a(Random random, WorldGenDecoratorHeightAverageConfiguration worldgendecoratorheightaverageconfiguration, BlockPosition blockposition) {
        int i = worldgendecoratorheightaverageconfiguration.c;
        int j = worldgendecoratorheightaverageconfiguration.d;
        int k = blockposition.getX();
        int l = blockposition.getZ();
        int i1 = random.nextInt(j) + random.nextInt(j) - j + i;

        return Stream.of(new BlockPosition(k, i1, l));
    }
}