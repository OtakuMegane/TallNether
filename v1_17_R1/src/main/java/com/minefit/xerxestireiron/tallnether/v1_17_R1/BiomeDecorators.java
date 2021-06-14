package com.minefit.xerxestireiron.tallnether.v1_17_R1;

import com.google.common.collect.ImmutableSet;
import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators.TallNether_WorldGenDecoratorCount;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators.TallNether_WorldGenDecoratorRange;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators.TallNether_WorldGenLightStone1;
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
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderSimpl;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorConfigured;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorCount;

public class BiomeDecorators {

    private final WorldGenFeatureRandomPatchConfiguration PATCH_FIRE_CONFIG = new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(TBlocks.FIRE.getBlockData()), WorldGenBlockPlacerSimple.c).a(64)
                    .a(ImmutableSet.of(TBlocks.NETHERRACK)).b().d();
    private final WorldGenFeatureRandomPatchConfiguration PATCH_SOUL_FIRE_CONFIG = new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(TBlocks.SOUL_FIRE.getBlockData()), WorldGenBlockPlacerSimple.c).a(64)
                    .a(ImmutableSet.of(TBlocks.SOUL_SOIL)).b().d();
    private final WorldGenFeatureHellFlowingLavaConfiguration SPRING_DELTA_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            TFluidTypes.LAVA.h(), true, 4, 1,
            ImmutableSet.of(TBlocks.NETHERRACK, TBlocks.SOUL_SAND, TBlocks.GRAVEL, TBlocks.MAGMA_BLOCK, TBlocks.BLACKSTONE));
    private final WorldGenFeatureHellFlowingLavaConfiguration SPRING_OPEN_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            TFluidTypes.LAVA.h(), false, 4, 1, ImmutableSet.of(TBlocks.NETHERRACK));
    private final WorldGenFeatureHellFlowingLavaConfiguration CLOSED_NETHER_SPRING_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            TFluidTypes.LAVA.h(), false, 5, 0, ImmutableSet.of(TBlocks.NETHERRACK));

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

    public final WorldGenFeatureConfigured<?, ?> TALLNETHER_GLOWSTONE1; // Setting glowstone1
    public final WorldGenFeatureConfigured<?, ?> TALLNETHER_GLOWSTONE2; // Setting glowstone2

    // From BiomeDecoratorGroups.b
    // Of course they made them protected because reasons >:|
    public final WorldGenFeatureChanceDecoratorRangeConfiguration FULL_RANGE = new WorldGenFeatureChanceDecoratorRangeConfiguration(UniformHeight.a(VerticalAnchor.a(), VerticalAnchor.b()));
    public final WorldGenFeatureChanceDecoratorRangeConfiguration RANGE_10_10 = new WorldGenFeatureChanceDecoratorRangeConfiguration(UniformHeight.a(VerticalAnchor.b(10), VerticalAnchor.c(10)));
    public final WorldGenFeatureChanceDecoratorRangeConfiguration RANGE_8_8 = new WorldGenFeatureChanceDecoratorRangeConfiguration(UniformHeight.a(VerticalAnchor.b(8), VerticalAnchor.c(8)));
    public final WorldGenFeatureChanceDecoratorRangeConfiguration RANGE_4_4 = new WorldGenFeatureChanceDecoratorRangeConfiguration(UniformHeight.a(VerticalAnchor.b(4), VerticalAnchor.c(4)));


    @SuppressWarnings({ "rawtypes" })
    public BiomeDecorators(String biome) {
        WorldGenFeatureChanceDecoratorRangeConfiguration magmaHeightProvider = new WorldGenFeatureChanceDecoratorRangeConfiguration(UniformHeight.a(VerticalAnchor.a(27), VerticalAnchor.a(36)));
        WorldGenFeatureChanceDecoratorRangeConfiguration largeDebrisHeightProvider = new WorldGenFeatureChanceDecoratorRangeConfiguration(UniformHeight.a(VerticalAnchor.a(8), VerticalAnchor.a(24)));

        IntProvider intProvider = (IntProvider) ConstantInt.a(64);

        // Equal to WorldGenDecorator.COUNT
        TallNether_WorldGenDecoratorCount testCount = new TallNether_WorldGenDecoratorCount(WorldGenDecoratorFrequencyConfiguration.a);

        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> test2 = testCount.a(new WorldGenDecoratorFrequencyConfiguration(intProvider));

        this.GLOWSTONE_EXTRA = (WorldGenFeatureConfigured) ((WorldGenFeatureConfigured) ((WorldGenFeatureConfigured) (new TallNether_WorldGenLightStone1(
                WorldGenFeatureEmptyConfiguration.a)).b(TWorldGenFeatureConfiguration.NONE).a((this.RANGE_4_4)).a()).a((IntProvider) BiasedToBottomInt.a(0, 9)));
        this.GLOWSTONE = (WorldGenFeatureConfigured) ((WorldGenFeatureConfigured) ((WorldGenFeatureConfigured) (new TallNether_WorldGenLightStone1(
                WorldGenFeatureEmptyConfiguration.a)).b(TWorldGenFeatureConfiguration.NONE).a(this.FULL_RANGE)).a()).b(10);
        this.SPRING_DELTA = TWorldGenerator.SPRING.b(this.SPRING_DELTA_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "lavafall").a(this.RANGE_4_4).a()); // .b(16)
        this.SPRING_CLOSED = TWorldGenerator.SPRING.b(this.CLOSED_NETHER_SPRING_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "hidden-lava").a(this.RANGE_10_10).a()); // .b(16)
        this.SPRING_CLOSED_DOUBLE = TWorldGenerator.SPRING.b(this.CLOSED_NETHER_SPRING_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "hidden-lava").a(this.RANGE_10_10).a()); // .b(32)
        this.SPRING_OPEN = TWorldGenerator.SPRING.b(this.SPRING_OPEN_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "lavafall").a(this.RANGE_4_4).a()); // .b(8)
        this.PATCH_FIRE = TWorldGenerator.RANDOM_PATCH.b(this.PATCH_FIRE_CONFIG)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "fire").a(this.RANGE_4_4).a());
        this.PATCH_SOUL_FIRE = TWorldGenerator.RANDOM_PATCH.b(this.PATCH_SOUL_FIRE_CONFIG)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "fire").a(this.RANGE_4_4).a());
        this.ORE_MAGMA = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d, TBlocks.MAGMA_BLOCK.getBlockData(), 33))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "magma-block").a(magmaHeightProvider).a()); // .b(4)
        this.ORE_GOLD_DELTAS = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d, TBlocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "nether-gold").a(this.RANGE_10_10).a()); // .b(20)
        this.ORE_QUARTZ_DELTAS = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d, TBlocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "quartz").a(this.RANGE_10_10).a()); // .b(32)
        this.ORE_GOLD_NETHER = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d, TBlocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "nether-gold").a(this.RANGE_10_10).a()); // .b(10)
        this.ORE_QUARTZ_NETHER = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d, TBlocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "quartz").a(this.RANGE_10_10).a()).a(test2); // .b(16)
        this.ORE_DEBRIS_LARGE = TWorldGenerator.SCATTERED_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d, TBlocks.ANCIENT_DEBRIS.getBlockData(), 3, 1.0F))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "ancient-debris1").a(largeDebrisHeightProvider).a());
        this.ORE_DEBRIS_SMALL = TWorldGenerator.SCATTERED_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d, TBlocks.ANCIENT_DEBRIS.getBlockData(), 2, 1.0F))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "ancient-debris2").a(this.RANGE_8_8).a());
        this.TALLNETHER_GLOWSTONE1 = TWorldGenerator.GLOWSTONE_BLOB.b(TWorldGenFeatureConfiguration.NONE)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "glowstone1").a(this.RANGE_4_4).a());
        this.TALLNETHER_GLOWSTONE2 = TWorldGenerator.GLOWSTONE_BLOB.b(TWorldGenFeatureConfiguration.NONE)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "glowstone2").a(this.FULL_RANGE).a());

        // Fallback style to try if my own strusture doesn't work
        /*this.ORE_MAGMA = ((WorldGenFeatureConfigured) TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        TBlocks.MAGMA_BLOCK.getBlockData(), 33)).a(VerticalAnchor.a(27), VerticalAnchor.a(36)))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "magma-block").a()); // .b(4)
*/
        }
}
