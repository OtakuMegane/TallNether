package com.minefit.xerxestireiron.tallnether.v1_15_R1_2;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.GeneratorAccess;
import net.minecraft.server.v1_15_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenDecoratorNetherGlowstone;

public class TallNether_WorldGenDecoratorNetherGlowstone extends WorldGenDecoratorNetherGlowstone {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorNetherGlowstone(Function<Dynamic<?>, ? extends WorldGenDecoratorFrequencyConfiguration> function) {
        super(function);
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
            int k = random.nextInt(max) + min;
            int l = random.nextInt(16);

            return blockposition.b(j, k, l);
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
