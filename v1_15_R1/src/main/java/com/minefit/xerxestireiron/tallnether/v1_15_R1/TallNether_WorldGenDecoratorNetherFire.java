package com.minefit.xerxestireiron.tallnether.v1_15_R1;

import com.google.common.collect.Lists;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.datafixers.Dynamic;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.GeneratorAccess;
import net.minecraft.server.v1_15_R1.WorldGenDecoratorFrequencyConfiguration;

public class TallNether_WorldGenDecoratorNetherFire extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorFrequencyConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorNetherFire(Function<Dynamic<?>, ? extends WorldGenDecoratorFrequencyConfiguration> function) {
        super(function);
    }

    // TallNether: Methods added to allow per-world config access through GeneratorAccess
    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        ConfigValues worldConfig = this.configAccessor.getConfig(worldName);
        int attempts = worldConfig.fireAttempts;
        int min = worldConfig.fireMinHeight;
        int max = worldConfig.fireMaxHeight;
        max = max > 0 ? max : 1;
        attempts = attempts > 0 ? attempts : 1;
        return a(random, worldgendecoratorfrequencyconfiguration, blockposition, attempts, min, max);
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, int attempts, int min, int max) {
        List<BlockPosition> list = Lists.newArrayList();

        for (int i = 0; i < random.nextInt(random.nextInt(attempts) + 1) + 1; ++i) {
            int j = random.nextInt(16);
            int k = random.nextInt(max) + min;
            int l = random.nextInt(16);

            list.add(blockposition.b(j, k, l));
        }

        return list.stream();
    }

    // TallNether: Override default gen so it doesn't actually do anything
    @Override
    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition)             {
        List<BlockPosition> list = Lists.newArrayList();
        return list.stream();
    }
}