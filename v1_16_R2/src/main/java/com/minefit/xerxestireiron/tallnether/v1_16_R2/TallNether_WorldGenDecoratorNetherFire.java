package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import com.google.common.collect.Lists;
import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;

public class TallNether_WorldGenDecoratorNetherFire extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorFrequencyConfiguration> {

    private final String blockType;

    public TallNether_WorldGenDecoratorNetherFire(Codec<WorldGenDecoratorFrequencyConfiguration> codec, String biomeName, String blockType) {
        super(codec, biomeName);
        this.blockType = blockType;
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, BiomeValues biomeValues)             {

        List<BlockPosition> list = Lists.newArrayList();
        int attempts = biomeValues.values.get(this.blockType + "-attempts");
        attempts = attempts > 1 ? attempts : 1;
        int maxHeight = biomeValues.values.get(this.blockType + "-max-height");
        maxHeight = maxHeight > 0 ? maxHeight : 1;
        int minHeight = biomeValues.values.get(this.blockType + "-min-height");

        for (int i = 0; i < random.nextInt(random.nextInt(attempts) + 1) + 1; ++i) {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(maxHeight) + minHeight;

            list.add(new BlockPosition(j, l, k));
        }

        return list.stream();
    }

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