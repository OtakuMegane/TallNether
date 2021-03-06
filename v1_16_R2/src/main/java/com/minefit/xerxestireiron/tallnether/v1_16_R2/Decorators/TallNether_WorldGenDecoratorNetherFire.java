package com.minefit.xerxestireiron.tallnether.v1_16_R2.Decorators;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;

public class TallNether_WorldGenDecoratorNetherFire extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorFrequencyConfiguration> {

    private final String block;

    public TallNether_WorldGenDecoratorNetherFire(Codec<WorldGenDecoratorFrequencyConfiguration> codec, String biome, String block) {
        super(codec, biome);
        this.block = block;
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, BiomeValues biomevalues, boolean vanilla)             {
        if (vanilla) {
            return a(random, worldgendecoratorfrequencyconfiguration, blockposition);
        }

        List<BlockPosition> list = Lists.newArrayList();
        int attempts = biomevalues.values.get(this.block + "-attempts");
        attempts = attempts > 1 ? attempts : 1;
        int maxHeight = biomevalues.values.get(this.block + "-max-height");
        maxHeight = maxHeight > 0 ? maxHeight : 1;
        int minHeight = biomevalues.values.get(this.block + "-min-height");

        for (int i = 0; i < random.nextInt(random.nextInt(attempts) + 1) + 1; ++i) {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(maxHeight) + minHeight;

            list.add(new BlockPosition(j, l, k));
        }

        return list.stream();
    }

    // TallNether: Vanilla generation
    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        List<BlockPosition> list = Lists.newArrayList();

        for (int i = 0; i < random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a().a(random)) + 1) + 1; ++i) {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(120) + 4;

            list.add(new BlockPosition(j, l, k));
        }

        return list.stream();
    }
}