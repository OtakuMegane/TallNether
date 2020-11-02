package com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.WorldGenDecoratorFrequencyConfiguration;

public class TallNether_WorldGenDecoratorNetherGlowstone extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorFrequencyConfiguration>  {

    private final String block;

    public TallNether_WorldGenDecoratorNetherGlowstone(Codec<WorldGenDecoratorFrequencyConfiguration> codec, String biome, String block) {
        super(codec, biome);
        this.block = block;
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, BiomeValues biomevalues, boolean vanilla) {
        if (vanilla) {
            return a(random, worldgendecoratorfrequencyconfiguration, blockposition);
        }

        int minHeight = biomevalues.values.get(this.block + "-min-height");
        int max = biomevalues.values.get(this.block + "-max-height");
        int maxHeight = max > 0 ? max : 1;
        int attempts = biomevalues.values.get(this.block + "-attempts");
        int maxAttempts = attempts > 0 ? attempts : 1;

        return IntStream.range(0, random.nextInt(random.nextInt(maxAttempts) + 1)).mapToObj((i) -> {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(maxHeight) + minHeight;

            return new BlockPosition(j, l, k);
        });
    }

    // TallNether: Vanilla generation
    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        return IntStream.range(0, random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a().a(random)) + 1)).mapToObj((i) -> {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(120) + 4;

            return new BlockPosition(j, l, k);
        });
    }
}
