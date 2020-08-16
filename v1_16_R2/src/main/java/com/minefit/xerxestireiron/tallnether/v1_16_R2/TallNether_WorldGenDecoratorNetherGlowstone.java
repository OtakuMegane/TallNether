package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFeatureSimple;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;

public class TallNether_WorldGenDecoratorNetherGlowstone extends WorldGenDecoratorFeatureSimple<WorldGenDecoratorFrequencyConfiguration>  {

    private final int minHeight;
    private final int maxHeight;

    public TallNether_WorldGenDecoratorNetherGlowstone(Codec<WorldGenDecoratorFrequencyConfiguration> codec, int minHeight, int maxHeight) {
        super(codec);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        return IntStream.range(0, random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a().a(random)) + 1)).mapToObj((i) -> {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(this.maxHeight) + this.minHeight;

            return new BlockPosition(j, l, k);
        });
    }
}
