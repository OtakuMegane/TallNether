package com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition;

import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;

// net.minecraft.world.level.levelgen.feature.Feature
public class TWorldGenerator {

    public static final WorldGenerator<WorldGenFeatureRandomPatchConfiguration> RANDOM_PATCH = WorldGenerator.j;
    public static final WorldGenerator<WorldGenFeatureHellFlowingLavaConfiguration> SPRING = WorldGenerator.l;
    public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> GLOWSTONE_BLOB = WorldGenerator.u;
    public static final WorldGenerator<WorldGenFeatureOreConfiguration> ORE = WorldGenerator.K;
}
