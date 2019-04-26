package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import com.google.common.collect.Lists;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.mojang.datafixers.Dynamic;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.GeneratorAccess;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorNetherFire;

public class TallNether_WorldGenDecoratorNetherFire extends WorldGenDecoratorNetherFire {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorNetherFire(Function<Dynamic<?>, ? extends WorldGenDecoratorFrequencyConfiguration> function) {
        super(function);
    }

    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        int attempts = this.configAccessor.getConfig(worldName).fireAttempts;
        int min = this.configAccessor.getConfig(worldName).fireMinHeight;
        int max = this.configAccessor.getConfig(worldName).fireMaxHeight;
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

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        List<BlockPosition> list = Lists.newArrayList();

        for (int i = 0; i < random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a) + 1) + 1; ++i) {
            int j = random.nextInt(16);
            int k = random.nextInt(120) + 4;
            int l = random.nextInt(16);

            list.add(blockposition.b(j, k, l));
        }

        return list.stream();
    }
}