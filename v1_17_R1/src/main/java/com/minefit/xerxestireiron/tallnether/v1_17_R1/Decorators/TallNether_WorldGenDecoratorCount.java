package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorContext;

public class TallNether_WorldGenDecoratorCount extends TallNether_RepeatingDecorator<WorldGenDecoratorFrequencyConfiguration> {

    private final String block;
    private final String biome;
    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorCount(Codec<WorldGenDecoratorFrequencyConfiguration> codec, String biome, String block) {
        super(codec);
        this.block = block;
        this.biome = biome;
    }

    protected int a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        return worldgendecoratorfrequencyconfiguration.a().a(random);
    }

    protected int a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, WorldGenDecoratorContext worldgendecoratorcontext) {
        String worldName = worldgendecoratorcontext.d().getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig.isVanilla) {
            return a(random, worldgendecoratorfrequencyconfiguration, blockposition);
        }

        BiomeValues biomeValues = worldConfig.getBiomeValues(this.biome);
        int attempts = biomeValues.values.get(this.block + "-attempts");
        return attempts;
    }
}
