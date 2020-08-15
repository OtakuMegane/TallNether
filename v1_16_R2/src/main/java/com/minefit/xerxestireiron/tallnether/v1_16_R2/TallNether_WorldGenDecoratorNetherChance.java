package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.GeneratorAccess;
import net.minecraft.server.v1_16_R2.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R2.WorldGenFeatureChanceDecoratorRangeConfiguration;

import java.util.Random;
import java.util.stream.Stream;

public class TallNether_WorldGenDecoratorNetherChance extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenFeatureChanceDecoratorRangeConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String blockType;

    public TallNether_WorldGenDecoratorNetherChance(Codec<WorldGenFeatureChanceDecoratorRangeConfiguration> codec, String blockType) {
        super(codec);
        this.blockType = blockType;
    }

    // TallNether: Methods added to allow per-world config access through GeneratorAccess
    public Stream<BlockPosition> a(GeneratorAccessSeed generatoraccessseed, Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig == null || worldConfig.isVanilla) {
            return a(random, worldgenfeaturechancedecoratorrangeconfiguration, blockposition);
        } else {
            float chance = 1.0F;
            int max = 256;
            return a(random, worldgenfeaturechancedecoratorrangeconfiguration, blockposition, chance, max);
        }
    }

    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition, float chance, int max) {
        if (random.nextFloat() < chance) {
            int i = random.nextInt(16) + blockposition.getX();
            int j = random.nextInt(16) + blockposition.getZ();
            int k = random.nextInt(max);

            return Stream.of(new BlockPosition(i, k, j));
        } else {
            return Stream.empty();
        }
    }

    // TallNether: Vanilla generation
    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition) {
        if (random.nextFloat() < worldgenfeaturechancedecoratorrangeconfiguration.b) {
            int i = random.nextInt(16) + blockposition.getX();
            int j = random.nextInt(16) + blockposition.getZ();
            int k = random.nextInt(worldgenfeaturechancedecoratorrangeconfiguration.e - worldgenfeaturechancedecoratorrangeconfiguration.d) + worldgenfeaturechancedecoratorrangeconfiguration.c;

            return Stream.of(new BlockPosition(i, k, j));
        } else {
            return Stream.empty();
        }
    }
}
