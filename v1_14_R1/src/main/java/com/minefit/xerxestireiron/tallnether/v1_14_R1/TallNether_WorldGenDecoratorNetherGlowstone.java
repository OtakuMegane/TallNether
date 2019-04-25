package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorNetherGlowstone;

public class TallNether_WorldGenDecoratorNetherGlowstone extends WorldGenDecoratorNetherGlowstone {

    private final ConfigValues configValues;

    public TallNether_WorldGenDecoratorNetherGlowstone(Function<Dynamic<?>, ? extends WorldGenDecoratorFrequencyConfiguration> function, ConfigValues configValues) {
        super(function);
        this.configValues = configValues;
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        return IntStream.range(0, random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a) + 1)).mapToObj((i) -> {
            int j = random.nextInt(16);
            int k = random.nextInt(this.configValues.glowstone1MaxHeight) + this.configValues.glowstone1MinHeight;
            int l = random.nextInt(16);

            return blockposition.b(j, k, l);
        });
    }
}
