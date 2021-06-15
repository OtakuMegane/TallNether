package com.minefit.xerxestireiron.tallnether.v1_17_R1;

import com.google.common.collect.ImmutableSet;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators.TallNether_WorldGenDecoratorCount;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators.TallNether_WorldGenDecoratorRange;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators.TallNether_WorldGenFeatureRandomPatch;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TBiomeDecoratorGroups;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TBlocks;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TFluidTypes;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TWorldGenFeatureConfiguration;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TWorldGenerator;

import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.blockplacers.WorldGenBlockPlacerSimple;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorConfigured;

public class BiomeDecorators {

    private final WorldGenFeatureRandomPatchConfiguration PATCH_FIRE_CONFIG = new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(TBlocks.FIRE.getBlockData()), WorldGenBlockPlacerSimple.c).a(64)
                    .a(ImmutableSet.of(TBlocks.NETHERRACK)).b().d();
    private final WorldGenFeatureRandomPatchConfiguration PATCH_SOUL_FIRE_CONFIG = new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(TBlocks.SOUL_FIRE.getBlockData()), WorldGenBlockPlacerSimple.c).a(64)
                    .a(ImmutableSet.of(TBlocks.SOUL_SOIL)).b().d();
    private final WorldGenFeatureHellFlowingLavaConfiguration SPRING_DELTA_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            TFluidTypes.LAVA.h(), true, 4, 1, ImmutableSet.of(TBlocks.NETHERRACK, TBlocks.SOUL_SAND, TBlocks.GRAVEL,
                    TBlocks.MAGMA_BLOCK, TBlocks.BLACKSTONE));
    private final WorldGenFeatureHellFlowingLavaConfiguration SPRING_OPEN_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            TFluidTypes.LAVA.h(), false, 4, 1, ImmutableSet.of(TBlocks.NETHERRACK));
    private final WorldGenFeatureHellFlowingLavaConfiguration CLOSED_NETHER_SPRING_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            TFluidTypes.LAVA.h(), false, 5, 0, ImmutableSet.of(TBlocks.NETHERRACK));
    private final TallNether_WorldGenFeatureRandomPatch TALLNETHER_RANDOM_PATCH = new TallNether_WorldGenFeatureRandomPatch(
            WorldGenFeatureRandomPatchConfiguration.a);

    public final WorldGenFeatureConfigured<?, ?> DELTA = TBiomeDecoratorGroups.DELTA;
    public final WorldGenFeatureConfigured<?, ?> SMALL_BASALT_COLUMNS = TBiomeDecoratorGroups.SMALL_BASALT_COLUMNS;
    public final WorldGenFeatureConfigured<?, ?> LARGE_BASALT_COLUMNS = TBiomeDecoratorGroups.LARGE_BASALT_COLUMNS;
    public final WorldGenFeatureConfigured<?, ?> BASALT_BLOBS = TBiomeDecoratorGroups.BASALT_BLOBS;
    public final WorldGenFeatureConfigured<?, ?> BLACKSTONE_BLOBS = TBiomeDecoratorGroups.BLACKSTONE_BLOBS;
    public final WorldGenFeatureConfigured<?, ?> GLOWSTONE_EXTRA;
    public final WorldGenFeatureConfigured<?, ?> GLOWSTONE;
    public final WorldGenFeatureConfigured<?, ?> CRIMSON_FOREST_VEGETATION = TBiomeDecoratorGroups.CRIMSON_FOREST_VEGETATION;
    public final WorldGenFeatureConfigured<?, ?> WARPED_FOREST_VEGETATION = TBiomeDecoratorGroups.WARPED_FOREST_VEGETATION;
    public final WorldGenFeatureConfigured<?, ?> NETHER_SPROUTS = TBiomeDecoratorGroups.NETHER_SPROUTS;
    public final WorldGenFeatureConfigured<?, ?> TWISTING_VINES = TBiomeDecoratorGroups.TWISTING_VINES;
    public final WorldGenFeatureConfigured<?, ?> WEEPING_VINES = TBiomeDecoratorGroups.WEEPING_VINES;
    public final WorldGenFeatureConfigured<?, ?> BASALT_PILLAR = TBiomeDecoratorGroups.BASALT_PILLAR;
    public final WorldGenFeatureConfigured<?, ?> SPRING_LAVA_DOUBLE = TBiomeDecoratorGroups.SPRING_LAVA_DOUBLE;
    public final WorldGenFeatureConfigured<?, ?> SPRING_LAVA = TBiomeDecoratorGroups.SPRING_LAVA;
    public final WorldGenFeatureConfigured<?, ?> SPRING_DELTA; // Setting lavafall (Basalt Deltas variant)
    public final WorldGenFeatureConfigured<?, ?> SPRING_CLOSED; // Setting hidden-lava
    public final WorldGenFeatureConfigured<?, ?> SPRING_CLOSED_DOUBLE; // Setting hidden-lava (Basalt Deltas variant)
    public final WorldGenFeatureConfigured<?, ?> SPRING_OPEN; // Setting lavafall
    public final WorldGenFeatureConfigured<?, ?> PATCH_FIRE; // Setting fire
    public final WorldGenFeatureConfigured<?, ?> PATCH_SOUL_FIRE; // Setting soul-fire
    public final WorldGenFeatureConfigured<?, ?> BROWN_MUSHROOM_NORMAL = TBiomeDecoratorGroups.BROWN_MUSHROOM_NORMAL;
    public final WorldGenFeatureConfigured<?, ?> RED_MUSHROOM_NORMAL = TBiomeDecoratorGroups.RED_MUSHROOM_NORMAL;
    public final WorldGenFeatureConfigured<?, ?> PATCH_CRIMSON_ROOTS = TBiomeDecoratorGroups.PATCH_CRIMSON_ROOTS;
    public final WorldGenFeatureConfigured<?, ?> BROWN_MUSHROOM_NETHER = TBiomeDecoratorGroups.BROWN_MUSHROOM_NETHER;
    public final WorldGenFeatureConfigured<?, ?> RED_MUSHROOM_NETHER = TBiomeDecoratorGroups.RED_MUSHROOM_NETHER;
    public final WorldGenFeatureConfigured<?, ?> ORE_MAGMA; // Setting magma-block
    public final WorldGenFeatureConfigured<?, ?> ORE_SOUL_SAND = TBiomeDecoratorGroups.ORE_SOUL_SAND;
    public final WorldGenFeatureConfigured<?, ?> ORE_GOLD_DELTAS; // Setting nether-gold (substitutes in Basalt Delta)
    public final WorldGenFeatureConfigured<?, ?> ORE_QUARTZ_DELTAS; // Setting quartz (substitutes in Basalt Delta)
    public final WorldGenFeatureConfigured<?, ?> ORE_GOLD_NETHER; // Setting nether-gold
    public final WorldGenFeatureConfigured<?, ?> ORE_QUARTZ_NETHER; // Setting quartz
    public final WorldGenFeatureConfigured<?, ?> ORE_GRAVEL_NETHER = TBiomeDecoratorGroups.ORE_GRAVEL_NETHER;
    public final WorldGenFeatureConfigured<?, ?> ORE_BLACKSTONE = TBiomeDecoratorGroups.ORE_BLACKSTONE;
    public final WorldGenFeatureConfigured<?, ?> ORE_DEBRIS_LARGE; // Setting ancient-debris1
    public final WorldGenFeatureConfigured<?, ?> ORE_DEBRIS_SMALL; // Setting ancient-debris2
    public final WorldGenFeatureConfigured<?, ?> CRIMSON_FUNGI = TBiomeDecoratorGroups.CRIMSON_FUNGI;
    public final WorldGenFeatureConfigured<?, ?> WARPED_FUNGI = TBiomeDecoratorGroups.WARPED_FUNGI;

    // From BiomeDecoratorGroups.b
    // Of course they made them protected because reasons >:|
    public final WorldGenFeatureChanceDecoratorRangeConfiguration FULL_RANGE = new WorldGenFeatureChanceDecoratorRangeConfiguration(
            UniformHeight.a(VerticalAnchor.a(), VerticalAnchor.b()));
    public final WorldGenFeatureChanceDecoratorRangeConfiguration RANGE_10_10 = new WorldGenFeatureChanceDecoratorRangeConfiguration(
            UniformHeight.a(VerticalAnchor.b(10), VerticalAnchor.c(10)));
    public final WorldGenFeatureChanceDecoratorRangeConfiguration RANGE_8_8 = new WorldGenFeatureChanceDecoratorRangeConfiguration(
            UniformHeight.a(VerticalAnchor.b(8), VerticalAnchor.c(8)));
    public final WorldGenFeatureChanceDecoratorRangeConfiguration RANGE_4_4 = new WorldGenFeatureChanceDecoratorRangeConfiguration(
            UniformHeight.a(VerticalAnchor.b(4), VerticalAnchor.c(4)));

    //Note: VerticalAnchor.a(int) is absolute, VerticalAnchor.b(int) is offset from bottom, VerticalAnchor.c(int) is offset from top
    // VerrticalAnchor.a() returns VerticalAnchor.b(0) and VerticalAnchor.b() returns VerticalAnchor.c(0)

    public BiomeDecorators(String biome) {

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> glowstone1Count = new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "glowstone1")
                        .a(new WorldGenDecoratorFrequencyConfiguration((IntProvider) BiasedToBottomInt.a(0, 9)));
        this.GLOWSTONE_EXTRA = TWorldGenerator.GLOWSTONE_BLOB.b(TWorldGenFeatureConfiguration.NONE).a(this.RANGE_4_4).a()
                        .a(glowstone1Count);

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> glowstone2Count = new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "glowstone2")
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(10)));
        this.GLOWSTONE = TWorldGenerator.GLOWSTONE_BLOB.b(TWorldGenFeatureConfiguration.NONE).a(this.FULL_RANGE).a()
                        .a(glowstone2Count); // .a(10)

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> lavafallDeltaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "lavafall"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(16)));
        this.SPRING_DELTA = TWorldGenerator.SPRING.b(this.SPRING_DELTA_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "lavafall").a(this.RANGE_4_4).a())
                .a(lavafallDeltaCount); // .b(16)

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> hiddenLavaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "hidden-lava"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(16)));
        this.SPRING_CLOSED = TWorldGenerator.SPRING.b(this.CLOSED_NETHER_SPRING_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "hidden-lava").a(this.RANGE_10_10)
                                .a())
                .a(hiddenLavaCount); // .b(16)

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> hiddenLavaDoubleCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "hidden-lava"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(32)));
        this.SPRING_CLOSED_DOUBLE = TWorldGenerator.SPRING.b(this.CLOSED_NETHER_SPRING_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "hidden-lava").a(this.RANGE_10_10)
                                .a())
                .a(hiddenLavaDoubleCount); // .b(32)

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> lavafallCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "lavafall"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(8)));
        this.SPRING_OPEN = TWorldGenerator.SPRING.b(this.SPRING_OPEN_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "lavafall").a(this.RANGE_4_4).a())
                .a(lavafallCount); // .b(8)

        this.PATCH_FIRE = this.TALLNETHER_RANDOM_PATCH.b(this.PATCH_FIRE_CONFIG).a(
                new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "fire")
                        .a(this.RANGE_4_4).a())
                .c(5);

        this.PATCH_SOUL_FIRE = this.TALLNETHER_RANDOM_PATCH.b(this.PATCH_SOUL_FIRE_CONFIG)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "soul-fire").a(this.RANGE_4_4).a())
                .c(5);

        WorldGenFeatureChanceDecoratorRangeConfiguration magmaHeightProvider = new WorldGenFeatureChanceDecoratorRangeConfiguration(
                UniformHeight.a(VerticalAnchor.a(27), VerticalAnchor.a(36)));
        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> magmaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "magma-block"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(4)));
        this.ORE_MAGMA = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        TBlocks.MAGMA_BLOCK.getBlockData(), 33))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "magma-block").a(magmaHeightProvider)
                                .a())
                .a(magmaCount); // .b(4)

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> goldDeltaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "nether-gold"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(20)));
        this.ORE_GOLD_DELTAS = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        TBlocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "nether-gold").a(this.RANGE_10_10)
                                .a())
                .a(goldDeltaCount); // .b(20)

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> quartzDeltaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "quartz"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(32)));
        this.ORE_QUARTZ_DELTAS = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        TBlocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "quartz").a(this.RANGE_10_10).a())
                .a(quartzDeltaCount); // .b(32)

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> goldCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "nether-gold"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(10)));
        this.ORE_GOLD_NETHER = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        TBlocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "nether-gold").a(this.RANGE_10_10)
                                .a())
                .a(goldCount); // .b(10)

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> quartzCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "quartz"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(16)));
        this.ORE_QUARTZ_NETHER = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        TBlocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "quartz").a(this.RANGE_10_10).a())
                .a(quartzCount); // .b(16)

        WorldGenFeatureChanceDecoratorRangeConfiguration largeDebrisHeightProvider = new WorldGenFeatureChanceDecoratorRangeConfiguration(
                UniformHeight.a(VerticalAnchor.a(8), VerticalAnchor.a(24)));
        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> debrisLargeCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "ancient-debris1"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(1)));
        this.ORE_DEBRIS_LARGE = TWorldGenerator.SCATTERED_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        TBlocks.ANCIENT_DEBRIS.getBlockData(), 3, 1.0F))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "ancient-debris1")
                                .a(largeDebrisHeightProvider).a())
                .a(debrisLargeCount);

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> debrisSmallCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "ancient-debris2"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(1)));
        this.ORE_DEBRIS_SMALL = TWorldGenerator.SCATTERED_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        TBlocks.ANCIENT_DEBRIS.getBlockData(), 2, 1.0F))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "ancient-debris2").a(this.RANGE_8_8)
                                .a())
                .a(debrisSmallCount);
    }
}
