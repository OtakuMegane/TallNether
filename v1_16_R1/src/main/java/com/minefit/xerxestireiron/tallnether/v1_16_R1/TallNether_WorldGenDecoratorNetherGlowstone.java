package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R1.BlockPosition;
import net.minecraft.server.v1_16_R1.GeneratorAccess;
import net.minecraft.server.v1_16_R1.WorldGenDecoratorFrequencyConfiguration;

public class TallNether_WorldGenDecoratorNetherGlowstone extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorFrequencyConfiguration>  {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorNetherGlowstone(Codec<WorldGenDecoratorFrequencyConfiguration> codec) {
        super(codec);
    }

    // TallNether: Methods added to allow per-world config access through GeneratorAccess
    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        ConfigValues worldConfig = this.configAccessor.getConfig(worldName);
        int attempts = worldConfig.glowstone1Attempts;
        int min = worldConfig.glowstone1MinHeight;
        int max = worldConfig.glowstone1MaxHeight;
        max = max > 0 ? max : 1;
        attempts = attempts > 0 ? attempts : 1;
        return a(random, worldgendecoratorfrequencyconfiguration, blockposition, attempts, min, max);
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, int attempts, int min, int max) {
        return IntStream.range(0, random.nextInt(random.nextInt(attempts) + 1)).mapToObj((i) -> {
            int j = random.nextInt(16);
            int k = random.nextInt(16);
            int l = random.nextInt(max) + min;

            return blockposition.b(j, l, k);
        });
    }

    // TallNether: Override default gen so it doesn't actually do anything
    @Override
    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        return IntStream.empty().mapToObj((i) -> {
            return blockposition.b(0, 0, 0);
        });
    }
}
