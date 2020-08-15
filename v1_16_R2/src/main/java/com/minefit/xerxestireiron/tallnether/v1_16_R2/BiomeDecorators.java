package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import com.google.common.collect.ImmutableSet;

import net.minecraft.server.v1_16_R2.BiomeDecoratorGroups;
import net.minecraft.server.v1_16_R2.Blocks;
import net.minecraft.server.v1_16_R2.FluidTypes;
import net.minecraft.server.v1_16_R2.WorldGenBlockPlacerSimple;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorHeightAverageConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R2.WorldGenFeatureEmptyConfiguration2;
import net.minecraft.server.v1_16_R2.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureStateProviderSimpl;
import net.minecraft.server.v1_16_R2.WorldGenStage;
import net.minecraft.server.v1_16_R2.WorldGenerator;

public class BiomeDecorators {
    public static final WorldGenFeatureRandomPatchConfiguration PATCH_FIRE_CONFIG = (new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(Blocks.FIRE.getBlockData()), WorldGenBlockPlacerSimple.c)).a(64)
                    .a(ImmutableSet.of(Blocks.NETHERRACK)).b().d();
    public static final WorldGenFeatureRandomPatchConfiguration PATCH_SOUL_FIRE_CONFIG = (new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(Blocks.SOUL_FIRE.getBlockData()), WorldGenBlockPlacerSimple.c)).a(64)
                    .a(ImmutableSet.of(Blocks.NETHERRACK)).b().d();
    public static final WorldGenFeatureHellFlowingLavaConfiguration SPRING_DELTA_CONFIG = (new WorldGenFeatureHellFlowingLavaConfiguration(
            FluidTypes.LAVA.h(), true, 4, 1, ImmutableSet.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL,
                    Blocks.MAGMA_BLOCK, Blocks.BLACKSTONE)));

    // Setting fire
    public static final WorldGenFeatureConfigured<?, ?> PATCH_FIRE = WorldGenerator.RANDOM_PATCH
            .b(BiomeDecorators.PATCH_FIRE_CONFIG)
            .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration.a, "fire")
                    .b(new WorldGenDecoratorFrequencyConfiguration(10)));

    // Setting soul-fire
    public static final WorldGenFeatureConfigured<?, ?> PATCH_SOUL_FIRE = WorldGenerator.RANDOM_PATCH
            .b(BiomeDecorators.PATCH_SOUL_FIRE_CONFIG)
            .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration.a, "soul-fire")
                    .b(new WorldGenDecoratorFrequencyConfiguration(10)));

    // Setting glowstone1
    public static final WorldGenFeatureConfigured<?, ?> GLOWSTONE_EXTRA = WorldGenerator.GLOWSTONE_BLOB
            .b(WorldGenFeatureConfiguration.k)
            .a(new TallNether_WorldGenDecoratorNetherGlowstone(WorldGenDecoratorFrequencyConfiguration.a, "glowstone1")
                    .b(new WorldGenDecoratorFrequencyConfiguration(10)));

    // Setting glowstone2
    public static final WorldGenFeatureConfigured<?, ?> GLOWSTONE = WorldGenerator.GLOWSTONE_BLOB
            .b(WorldGenFeatureConfiguration.k)
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "glowstone2")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(0, 0, 128)).b(10));

    // Setting magma-block
    public static final WorldGenFeatureConfigured<?, ?> ORE_MAGMA = WorldGenerator.ORE
            .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                    Blocks.MAGMA_BLOCK.getBlockData(), 33))
            .a(new TallNether_WorldGenDecoratorNetherMagma(WorldGenFeatureEmptyConfiguration2.a)
                    .b(WorldGenFeatureEmptyConfiguration2.c))
            .b(4);

    // Setting quartz
    public static final WorldGenFeatureConfigured<?, ?> ORE_QUARTZ_NETHER = WorldGenerator.ORE
            .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                    Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "quartz")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(16));

    // Setting quartz (Basalt Deltas)
    public static final WorldGenFeatureConfigured<?, ?> ORE_QUARTZ_DELTAS = WorldGenerator.ORE
            .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                    Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "quartz")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(32));

    // Setting ancient-debris1
    public static final WorldGenFeatureConfigured<?, ?> ORE_DEBRIS_LARGE = WorldGenerator.NO_SURFACE_ORE
            .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                    Blocks.ANCIENT_DEBRIS.getBlockData(), 3))
            .a(new TallNether_WorldGenDecoratorDepthAverage(WorldGenDecoratorHeightAverageConfiguration.a,
                    "ancient-debris").b(new WorldGenDecoratorHeightAverageConfiguration(16, 8)));

    // Setting ancient-debris2
    public static final WorldGenFeatureConfigured<?, ?> ORE_DEBRIS_SMALL = WorldGenerator.NO_SURFACE_ORE
            .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                    Blocks.ANCIENT_DEBRIS.getBlockData(), 2))
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                    "ancient-debris").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(8, 16, 128)).b(1));

    // Setting nether-gold
    public static final WorldGenFeatureConfigured<?, ?> ORE_GOLD_NETHER = WorldGenerator.ORE
            .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                    Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "nether-gold")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(10));

    // Setting nether-gold (Basalt Deltas)
    public static final WorldGenFeatureConfigured<?, ?> ORE_GOLD_DELTAS = WorldGenerator.ORE
            .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                    Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "nether-gold")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(20));

    // Setting lavafall
    public static final WorldGenFeatureConfigured<?, ?> SPRING_OPEN = WorldGenerator.SPRING_FEATURE
            .b(BiomeDecoratorGroups.a.j)
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "lavafall")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(4, 8, 128)).b(16));

    // Setting lavafall (Basalt Deltas)
    public static final WorldGenFeatureConfigured<?, ?> SPRING_DELTA = WorldGenerator.SPRING_FEATURE
            .b(BiomeDecorators.SPRING_DELTA_CONFIG)
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "lavafall")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(4, 8, 128)).b(16));

    // Setting hidden-lava
    public static final WorldGenFeatureConfigured<?, ?> SPRING_CLOSED = WorldGenerator.SPRING_FEATURE
            .b(BiomeDecoratorGroups.a.j)
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "hidden-lava")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(16));

    // Setting hidden-lava (Basalt Deltas)
    public static final WorldGenFeatureConfigured<?, ?> SPRING_CLOSED_DOUBLE = WorldGenerator.SPRING_FEATURE
            .b(BiomeDecoratorGroups.a.j)
            .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, "hidden-lava")
                    .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(32));

    public static final WorldGenFeatureConfigured<?, ?> BROWN_MUSHROOM_NETHER = BiomeDecoratorGroups.BROWN_MUSHROOM_NETHER;
    public static final WorldGenFeatureConfigured<?, ?> RED_MUSHROOM_NETHER = BiomeDecoratorGroups.RED_MUSHROOM_NETHER;
    public static final WorldGenFeatureConfigured<?, ?> BASALT_BLOBS = BiomeDecoratorGroups.BASALT_BLOBS;
    public static final WorldGenFeatureConfigured<?, ?> BLACKSTONE_BLOBS = BiomeDecoratorGroups.BLACKSTONE_BLOBS;
    public static final WorldGenFeatureConfigured<?, ?> SPRING_LAVA = BiomeDecoratorGroups.SPRING_LAVA;
    public static final WorldGenFeatureConfigured<?, ?> SPRING_LAVA_DOUBLE = BiomeDecoratorGroups.SPRING_LAVA_DOUBLE;

    public BiomeDecorators() {
    }
}
