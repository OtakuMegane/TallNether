package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import com.google.common.collect.Lists;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.datafixers.Dynamic;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorNetherFire;

public class TallNether_WorldGenDecoratorNetherFire extends WorldGenDecoratorNetherFire {

    private final ConfigValues configValues;

    public TallNether_WorldGenDecoratorNetherFire(Function<Dynamic<?>, ? extends WorldGenDecoratorFrequencyConfiguration> function, ConfigValues configValues) {
        super(function);
        this.configValues = configValues;
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        List<BlockPosition> list = Lists.newArrayList();

        for (int i = 0; i < random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a) + 1) + 1; ++i) {
            int j = random.nextInt(16);
            int k = random.nextInt(this.configValues.fireMaxHeight) + this.configValues.fireMinHeight;
            int l = random.nextInt(16);

            list.add(blockposition.b(j, k, l));
        }

        return list.stream();
    }
}