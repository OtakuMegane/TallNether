package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R2.WorldGenFeatureChanceDecoratorRangeConfiguration;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TallNether_WorldGenDecoratorRange extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenFeatureChanceDecoratorRangeConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String blockType;

    public TallNether_WorldGenDecoratorRange(Codec<WorldGenFeatureChanceDecoratorRangeConfiguration> codec, String blockType) {
        super(codec);
        this.blockType = blockType;
    }

    // TallNether: Methods added to allow per-world config access through GeneratorAccess
    public Stream<BlockPosition> a(GeneratorAccessSeed generatoraccessseed, Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig == null || worldConfig.isVanilla) {
            return a(random, worldgenfeaturechancedecoratorrangeconfiguration, blockposition);
        }

        String biomeName = this.configAccessor.biomeClassToConfig(generatoraccessseed.getBiome(blockposition).getClass().getSimpleName());
        BiomeValues biomeConfig = worldConfig.getBiomeValues(biomeName);
        int attempts;
        int max;
        int min;

        if (this.blockType.equals("quartz")) {
            attempts = biomeConfig.values.get("quartz-attempts");
            max = biomeConfig.values.get("quartz-max-height");
            min = biomeConfig.values.get("quartz-min-height");
        } else if (this.blockType.equals("glowstone2")) {
            attempts = biomeConfig.values.get("glowstone2-attempts");
            max = biomeConfig.values.get("glowstone2-max-height");
            min = biomeConfig.values.get("glowstone2-min-height");
        } else if (this.blockType.equals("hidden-lava")) {
            attempts = biomeConfig.values.get("hidden-lava-attempts");
            max = biomeConfig.values.get("hidden-lava-max-height");
            min = biomeConfig.values.get("hidden-lava-min-height");
        } else if (this.blockType.equals("lavafall")) {
            attempts = biomeConfig.values.get("lavafall-attempts");
            max = biomeConfig.values.get("lavafall-max-height");
            min = biomeConfig.values.get("lavafall-min-height");
        } else if (this.blockType.equals("nether-gold")) {
            attempts = biomeConfig.values.get("nether-gold-attempts");
            max = biomeConfig.values.get("nether-gold-max-height");
            min = biomeConfig.values.get("nether-gold-min-height");
        } else if(this.blockType.equals("soul-sand-patch")) {
            attempts = 24;
            max = 256;
            min = 0;
        } else if(this.blockType.equals("ancient-debris")) {
            attempts = biomeConfig.values.get("ancient-debris2-attempts");
            max = biomeConfig.values.get("ancient-debris2-max-height");
            min = biomeConfig.values.get("ancient-debris2-min-height");
        } else if(this.blockType.equals("gravel-patch")) {
            attempts = biomeConfig.values.get("gravel-patch-attempts");
            max = biomeConfig.values.get("gravel-patch-max-height");
            min = biomeConfig.values.get("gravel-patch-min-height");
        } else if(this.blockType.equals("blackstone-patch")) {
            attempts = biomeConfig.values.get("blackstone-patch-attempts");
            max = biomeConfig.values.get("blackstone-patch-max-height");
            min = biomeConfig.values.get("blackstone-patch-min-height");
        } else if (this.blockType.equals("twisting-vines")) {
            attempts = 20;
            max = 256;
            min = 0;
        } else if (this.blockType.equals("weeping-vines")) {
            attempts = 20;
            max = 256;
            min = 0;
        } else {
            return a(random, worldgenfeaturechancedecoratorrangeconfiguration, blockposition);
        }

        max = max > 0 ? max : 1;
        attempts = attempts > 0 ? attempts : 1;
        return a(random, worldgenfeaturechancedecoratorrangeconfiguration, blockposition, attempts, max, min);
    }

    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition, int attempts, int max, int min) {
        return IntStream.range(0, attempts).mapToObj((i) -> {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(max) + min;

            return new BlockPosition(j, l, k);
        });
    }

    // TallNether: Vanilla generation
    @Override
    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition) {
        int i = blockposition.getX();
        int j = blockposition.getZ();
        int k = random.nextInt(worldgenfeaturechancedecoratorrangeconfiguration.e - worldgenfeaturechancedecoratorrangeconfiguration.d) + worldgenfeaturechancedecoratorrangeconfiguration.c;

        return Stream.of(new BlockPosition(i, k, j));
    }
}
