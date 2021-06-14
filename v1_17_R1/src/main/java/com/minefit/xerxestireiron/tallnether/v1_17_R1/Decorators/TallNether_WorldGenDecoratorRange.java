package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorContext;

public class TallNether_WorldGenDecoratorRange extends TallNether_VerticalDecorator<WorldGenFeatureChanceDecoratorRangeConfiguration> {

    private final String blockName;
    private final String biomeName;
    private final boolean vanillaSkip;

    public TallNether_WorldGenDecoratorRange(Codec<WorldGenFeatureChanceDecoratorRangeConfiguration> codec, String biomeName, String blockName) {
        super(codec);
        this.blockName = blockName;
        this.biomeName = biomeName;
        // We add extra glowstone decorators until we can figure out how to apply settings to the normal decorator
        this.vanillaSkip = blockName.equals("glowstone1") || blockName.equals("glowstone2");
    }

    protected int a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, int i) {
        return worldgenfeaturechancedecoratorrangeconfiguration.c.a(random, worldgendecoratorcontext); // c = height
    }

    // Custom method for TallNether to accomodate per-world settings
    protected Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition, WorldConfig worldConfig) {
        if(worldConfig.isVanilla && this.vanillaSkip) {
            return Stream.empty();
        }

        BiomeValues biomeValues = worldConfig.getBiomeValues(this.biomeName);
        int attempts = biomeValues.values.get(this.blockName + "-attempts");
        int minHeight = biomeValues.values.get(this.blockName + "-min-height");
        int max = biomeValues.values.get(this.blockName + "-max-height");
        int maxHeight = max > 0 ? max - minHeight : 1;

        return IntStream.range(0, attempts).mapToObj((i) -> {
            int j = random.nextInt(8) + blockposition.getX();
            int k = random.nextInt(8) + blockposition.getZ();
            int l = random.nextInt(maxHeight) + minHeight;

            return new BlockPosition(j, l, k);
        });
    }
}
