package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.util.MathHelper;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.world.level.levelgen.placement.VerticalDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorContext;

public class TallNether_WorldGenDecoratorRange extends VerticalDecorator<WorldGenFeatureChanceDecoratorRangeConfiguration> {

    private final String block;
    private final String biome;
    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorRange(Codec<WorldGenFeatureChanceDecoratorRangeConfiguration> codec, String biome, String block) {
        super(codec);
        this.block = block;
        this.biome = biome;
    }

    protected int a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, int i) {
        String worldName = worldgendecoratorcontext.d().getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig.isVanilla) {
            return worldgenfeaturechancedecoratorrangeconfiguration.c.a(random, worldgendecoratorcontext); // c = height
        }

        BiomeValues biomeValues = worldConfig.getBiomeValues(this.biome);
        int minHeight = biomeValues.values.get(this.block + "-min-height");
        int maxHeight = biomeValues.values.get(this.block + "-max-height");
        minHeight = minHeight > maxHeight ? maxHeight : minHeight;
        return MathHelper.b(random, minHeight, maxHeight);
    }
}
