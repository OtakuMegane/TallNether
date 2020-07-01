package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.datafixers.Dynamic;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R1.BlockPosition;
import net.minecraft.server.v1_16_R1.GeneratorAccess;
import net.minecraft.server.v1_16_R1.WorldGenFeatureChanceDecoratorRangeConfiguration;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TallNether_WorldGenDecoratorNetherChance extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenFeatureChanceDecoratorRangeConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String blockType;

    public TallNether_WorldGenDecoratorNetherChance(Codec<WorldGenFeatureChanceDecoratorRangeConfiguration> codec, String blockType) {
        super(codec);
        this.blockType = blockType;
    }

    // TallNether: Methods added to allow per-world config access through GeneratorAccess
    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        ConfigValues worldConfig = this.configAccessor.getConfig(worldName);
        int attempts;
        int innerRand;
        int outerRand;

        if(this.blockType.equals("red-shroom")) {
            attempts = worldConfig.redShroomAttempts;
            innerRand = worldConfig.redShroomMaxHeight - worldConfig.redShroomMaxMinus;
            outerRand = worldConfig.redShroomMinHeight;
        } else if(this.blockType.equals("brown-shroom")) {
            attempts = worldConfig.brownShroomAttempts;
            innerRand = worldConfig.brownShroomMaxHeight - worldConfig.brownShroomMaxMinus;
            outerRand = worldConfig.brownShroomMinHeight;
        } else {
            return a(random, worldgenfeaturechancedecoratorrangeconfiguration, blockposition);
        }

        innerRand = innerRand > 0 ? innerRand : 1;
        outerRand = outerRand > 0 ? outerRand : 1;

        return a(random, worldgenfeaturechancedecoratorrangeconfiguration, blockposition, attempts, innerRand, outerRand);
    }

    // TallNether: We use this to continue using the attempts style of generation
    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition, int attempts, int innerRand, int outerRand) {
        return IntStream.range(0, attempts).mapToObj((i) -> {
            int j = random.nextInt(16);
            int k = random.nextInt(innerRand) + outerRand;
            int l = random.nextInt(16);

            return blockposition.b(j, k, l);
        });
    }

    // TallNether: This is the vanilla generation
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
