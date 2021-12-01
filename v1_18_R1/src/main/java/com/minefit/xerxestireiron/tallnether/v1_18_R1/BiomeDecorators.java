package com.minefit.xerxestireiron.tallnether.v1_18_R1;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.Decorators.TallNether_CountPlacement;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.Decorators.TallNether_InSquarePlacement;

import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.material.Fluids;

public class BiomeDecorators {

    /*private final WorldGenFeatureRandomPatchConfiguration PATCH_FIRE_CONFIG = new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(Blocks.FIRE.getBlockData()), WorldGenBlockPlacerSimple.c).a(64)
                    .a(ImmutableSet.of(Blocks.NETHERRACK)).b().d();
    private final WorldGenFeatureRandomPatchConfiguration PATCH_SOUL_FIRE_CONFIG = new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(Blocks.SOUL_FIRE.getBlockData()), WorldGenBlockPlacerSimple.c).a(64)
                    .a(ImmutableSet.of(Blocks.SOUL_SOIL)).b().d();
    private final WorldGenFeatureHellFlowingLavaConfiguration SPRING_DELTA_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            Fluids.LAVA.h(), true, 4, 1, ImmutableSet.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL,
                    Blocks.MAGMA_BLOCK, Blocks.BLACKSTONE));
    private final WorldGenFeatureHellFlowingLavaConfiguration SPRING_OPEN_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            Fluids.LAVA.h(), false, 4, 1, ImmutableSet.of(Blocks.NETHERRACK));
    private final WorldGenFeatureHellFlowingLavaConfiguration CLOSED_NETHER_SPRING_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            Fluids.LAVA.h(), false, 5, 0, ImmutableSet.of(Blocks.NETHERRACK));
    private final TallNether_WorldGenFeatureRandomPatch TALLNETHER_RANDOM_PATCH = new TallNether_WorldGenFeatureRandomPatch(
            WorldGenFeatureRandomPatchConfiguration.a);*/

    public final PlacedFeature DELTA = NetherPlacements.DELTA;
    public final PlacedFeature SMALL_BASALT_COLUMNS = NetherPlacements.SMALL_BASALT_COLUMNS;
    public final PlacedFeature LARGE_BASALT_COLUMNS = NetherPlacements.LARGE_BASALT_COLUMNS;
    public final PlacedFeature BASALT_BLOBS = NetherPlacements.BASALT_BLOBS;
    public final PlacedFeature BLACKSTONE_BLOBS = NetherPlacements.BLACKSTONE_BLOBS;
    public final PlacedFeature GLOWSTONE_EXTRA;
    public final PlacedFeature GLOWSTONE;
    public final PlacedFeature CRIMSON_FOREST_VEGETATION = NetherPlacements.CRIMSON_FOREST_VEGETATION;
    public final PlacedFeature WARPED_FOREST_VEGETATION = NetherPlacements.WARPED_FOREST_VEGETATION;
    public final PlacedFeature NETHER_SPROUTS = NetherPlacements.NETHER_SPROUTS;
    public final PlacedFeature TWISTING_VINES = NetherPlacements.TWISTING_VINES;
    public final PlacedFeature WEEPING_VINES = NetherPlacements.WEEPING_VINES;
    public final PlacedFeature BASALT_PILLAR = NetherPlacements.BASALT_PILLAR;
    public final PlacedFeature SPRING_LAVA = MiscOverworldPlacements.SPRING_LAVA;
    public final PlacedFeature SPRING_DELTA; // Setting lavafall (Basalt Deltas variant)
    public final PlacedFeature SPRING_CLOSED; // Setting hidden-lava
    public final PlacedFeature SPRING_CLOSED_DOUBLE; // Setting hidden-lava (Basalt Deltas variant)
    public final PlacedFeature SPRING_OPEN; // Setting lavafall
    public final PlacedFeature PATCH_FIRE; // Setting fire
    public final PlacedFeature PATCH_SOUL_FIRE; // Setting soul-fire
    public final PlacedFeature BROWN_MUSHROOM_NORMAL = VegetationPlacements.BROWN_MUSHROOM_NORMAL;
    public final PlacedFeature RED_MUSHROOM_NORMAL = VegetationPlacements.RED_MUSHROOM_NORMAL;
    public final PlacedFeature PATCH_CRIMSON_ROOTS = NetherPlacements.PATCH_CRIMSON_ROOTS;
    public final PlacedFeature BROWN_MUSHROOM_NETHER = VegetationPlacements.BROWN_MUSHROOM_NETHER;
    public final PlacedFeature RED_MUSHROOM_NETHER = VegetationPlacements.RED_MUSHROOM_NETHER;
    public final PlacedFeature ORE_MAGMA; // Setting magma-block
    public final PlacedFeature ORE_SOUL_SAND = OrePlacements.ORE_SOUL_SAND;
    public final PlacedFeature ORE_GOLD_DELTAS; // Setting nether-gold (substitutes in Basalt Delta)
    public final PlacedFeature ORE_QUARTZ_DELTAS; // Setting quartz (substitutes in Basalt Delta)
    public final PlacedFeature ORE_GOLD_NETHER; // Setting nether-gold
    public final PlacedFeature ORE_QUARTZ_NETHER; // Setting quartz
    public final PlacedFeature ORE_GRAVEL_NETHER = OrePlacements.ORE_GRAVEL_NETHER;
    public final PlacedFeature ORE_BLACKSTONE = OrePlacements.ORE_BLACKSTONE;
    public final PlacedFeature ORE_ANCIENT_DEBRIS_LARGE; // Setting ancient-debris1
    public final PlacedFeature ORE_ANCIENT_DEBRIS_SMALL; // Setting ancient-debris2
    public final PlacedFeature CRIMSON_FUNGI = TreePlacements.CRIMSON_FUNGI;
    public final PlacedFeature WARPED_FUNGI = TreePlacements.WARPED_FUNGI;

    public BiomeDecorators(String biome) {

        // NOTES:
        // Range covers the height/location
        // Count covers how many times

        // Setting: glowstone1
        ConfiguredFeature<NoneFeatureConfiguration, ?> glowstoneExtra = Feature.GLOWSTONE_BLOB.configured(FeatureConfiguration.NONE);
        this.GLOWSTONE_EXTRA = NetherFeatures.GLOWSTONE_EXTRA
                .placed(new PlacementModifier[]{
                        new TallNether_CountPlacement(BiasedToBottomInt.of(0, 9), biome, "glowstone1"),
                        new TallNether_InSquarePlacement(biome, "glowstone1"), PlacementUtils.RANGE_4_4, BiomeFilter.biome()});

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> glowstone1Count = new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "glowstone1")
                        .a(new WorldGenDecoratorFrequencyConfiguration((IntProvider) BiasedToBottomInt.a(0, 9)));
        this.GLOWSTONE_EXTRA = TWorldGenerator.GLOWSTONE_BLOB.b(TWorldGenFeatureConfiguration.NONE).a(this.RANGE_4_4).a()
                        .a(glowstone1Count);*/

        // Setting: glowstone2
        this.GLOWSTONE = NetherFeatures.GLOWSTONE_EXTRA.placed(new PlacementModifier[]{
                new TallNether_CountPlacement(ConstantInt.of(10), biome, "glowstone2"), new TallNether_InSquarePlacement(biome, "glowstone2"), PlacementUtils.FULL_RANGE, BiomeFilter.biome()});
        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> glowstone2Count = new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "glowstone2")
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(10)));
        this.GLOWSTONE = TWorldGenerator.GLOWSTONE_BLOB.b(TWorldGenFeatureConfiguration.NONE).a(this.FULL_RANGE).a()
                        .a(glowstone2Count); // .a(10)*/


        // Setting lavafall
        ConfiguredFeature<SpringConfiguration, ?> springLavaNether = Feature.SPRING.configured(new SpringConfiguration(Fluids.LAVA.defaultFluidState(), true, 4, 1,
                        ImmutableSet.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL, Blocks.MAGMA_BLOCK,
                                Blocks.BLACKSTONE)));
        this.SPRING_DELTA = NetherFeatures.SPRING_LAVA_NETHER.placed(new PlacementModifier[]{new TallNether_CountPlacement(ConstantInt.of(16), biome, "lavafall"),
                        new TallNether_InSquarePlacement(biome, "lavafall"), PlacementUtils.RANGE_4_4, BiomeFilter.biome()});

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> lavafallDeltaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "lavafall"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(16)));
        this.SPRING_DELTA = TWorldGenerator.SPRING.b(this.SPRING_DELTA_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "lavafall").a(this.RANGE_4_4).a())
                .a(lavafallDeltaCount); // .b(16)*/

        this.SPRING_OPEN = NetherFeatures.SPRING_NETHER_OPEN.placed(new PlacementModifier[]{new TallNether_CountPlacement(ConstantInt.of(8), biome, "lavafall"),
                        new TallNether_InSquarePlacement(biome, "lavafall"), PlacementUtils.RANGE_4_4, BiomeFilter.biome()});

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> lavafallCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "lavafall"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(8)));
        this.SPRING_OPEN = TWorldGenerator.SPRING.b(this.SPRING_OPEN_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "lavafall").a(this.RANGE_4_4).a())
                .a(lavafallCount); // .b(8)*/


        // Setting: hidden-lava
        ConfiguredFeature<SpringConfiguration, ?> springNetherClosed = Feature.SPRING.configured(new SpringConfiguration(Fluids.LAVA.defaultFluidState(), false, 5, 0,
                        ImmutableSet.of(Blocks.NETHERRACK)));
        this.SPRING_CLOSED = NetherFeatures.SPRING_NETHER_CLOSED.placed(new PlacementModifier[]{new TallNether_CountPlacement(ConstantInt.of(16), biome, "hidden-lava"),
                        new TallNether_InSquarePlacement(biome, "hidden-lava"), PlacementUtils.RANGE_10_10, BiomeFilter.biome()});

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> hiddenLavaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "hidden-lava"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(16)));
        this.SPRING_CLOSED = TWorldGenerator.SPRING.b(this.CLOSED_NETHER_SPRING_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "hidden-lava").a(this.RANGE_10_10)
                                .a())
                .a(hiddenLavaCount); // .b(16)*/

        this.SPRING_CLOSED_DOUBLE = NetherFeatures.SPRING_NETHER_CLOSED.placed(new PlacementModifier[]{new TallNether_CountPlacement(ConstantInt.of(32), biome, "hidden-lava"),
                        new TallNether_InSquarePlacement(biome, "hidden-lava"), PlacementUtils.RANGE_10_10, BiomeFilter.biome()});

        /*WorldGenDecoratorFrequencyConfiguration.a, biome, "hidden-lava"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(32)));
        this.SPRING_CLOSED_DOUBLE = TWorldGenerator.SPRING.b(this.CLOSED_NETHER_SPRING_CONFIG)
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "hidden-lava").a(this.RANGE_10_10)
                                .a())
                .a(hiddenLavaDoubleCount); // .b(32)*/


        // Setting: fire
        List<PlacementModifier> firePlacement = (List<PlacementModifier>) ImmutableList.of(new TallNether_CountPlacement(UniformInt.of(0, 5), biome, "fire"),
                new TallNether_InSquarePlacement(biome, "fire"), PlacementUtils.RANGE_4_4, BiomeFilter.biome());
        this.PATCH_FIRE = NetherFeatures.PATCH_FIRE.placed(firePlacement);

        /*this.PATCH_FIRE = this.TALLNETHER_RANDOM_PATCH.b(this.PATCH_FIRE_CONFIG).a(
                new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "fire")
                        .a(this.RANGE_4_4).a())
                .c(5);*/


     // Setting: soul-fire
        List<PlacementModifier> soulFirePlacement = (List<PlacementModifier>) ImmutableList.of(
                new TallNether_CountPlacement(UniformInt.of(0, 5), biome, "soul-fire"),
                new TallNether_InSquarePlacement(biome, "soul-fire"), PlacementUtils.RANGE_4_4, BiomeFilter.biome());
        this.PATCH_SOUL_FIRE = NetherFeatures.PATCH_SOUL_FIRE.placed(soulFirePlacement);

        /*this.PATCH_SOUL_FIRE = this.TALLNETHER_RANDOM_PATCH.b(this.PATCH_SOUL_FIRE_CONFIG)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "soul-fire").a(this.RANGE_4_4).a())
                .c(5);*/

        // Setting: magma-block
        /*fORE_MAGMA = FeatureUtils.register("ore_magma",
            Feature.ORE.configured(new OreConfiguration(OreFeatures.NETHERRACK, Blocks.MAGMA_BLOCK.defaultBlockState(), 33)));*/
        List<PlacementModifier> oreMagma = (List<PlacementModifier>) ImmutableList.of(new TallNether_CountPlacement(ConstantInt.of(4), biome, "magma-block"),
                new TallNether_InSquarePlacement(biome, "magma-block"),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(27), VerticalAnchor.absolute(36)),
                BiomeFilter.biome());
        this.ORE_MAGMA = OreFeatures.ORE_MAGMA.placed(oreMagma);

        /*WorldGenFeatureChanceDecoratorRangeConfiguration magmaHeightProvider = new WorldGenFeatureChanceDecoratorRangeConfiguration(
                UniformHeight.a(VerticalAnchor.a(27), VerticalAnchor.a(36)));
        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> magmaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "magma-block"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(4)));
        this.ORE_MAGMA = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        Blocks.MAGMA_BLOCK.getBlockData(), 33))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "magma-block").a(magmaHeightProvider)
                                .a())
                .a(magmaCount); // .b(4)*/


        // Setting: nether-gold
        List<PlacementModifier> oreGoldDeltas = (List<PlacementModifier>) ImmutableList.of(new TallNether_CountPlacement(ConstantInt.of(20), biome, "nether-gold"),
                new TallNether_InSquarePlacement(biome, "nether-gold"), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
        this.ORE_GOLD_DELTAS = OreFeatures.ORE_NETHER_GOLD.placed(oreGoldDeltas);

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> goldDeltaCount = (new TallNether_WorldGenDecoratorCount(
        WorldGenDecoratorFrequencyConfiguration.a, biome, "nether-gold"))
                .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(20)));
this.ORE_GOLD_DELTAS = TWorldGenerator.ORE
        .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
        .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "nether-gold").a(this.RANGE_10_10)
                        .a())
        .a(goldDeltaCount); // .b(20)*/

        List<PlacementModifier> oreGoldNether = (List<PlacementModifier>) ImmutableList.of(new TallNether_CountPlacement(ConstantInt.of(10), biome, "nether-gold"),
                new TallNether_InSquarePlacement(biome, "nether-gold"), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
        this.ORE_GOLD_NETHER = OreFeatures.ORE_NETHER_GOLD.placed(oreGoldNether);

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> goldCount = (new TallNether_WorldGenDecoratorCount(
        WorldGenDecoratorFrequencyConfiguration.a, biome, "nether-gold"))
                .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(10)));
this.ORE_GOLD_NETHER = TWorldGenerator.ORE
        .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
        .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "nether-gold").a(this.RANGE_10_10)
                        .a())
        .a(goldCount); // .b(10)*/

        // Setting: quartz
        List<PlacementModifier> oreQuartzDeltas = (List<PlacementModifier>) ImmutableList.of(new TallNether_CountPlacement(ConstantInt.of(32), biome, "quartz"),
                new TallNether_InSquarePlacement(biome, "quartz"), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
        this.ORE_QUARTZ_DELTAS = OreFeatures.ORE_QUARTZ.placed(oreQuartzDeltas);

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> quartzDeltaCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "quartz"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(32)));
        this.ORE_QUARTZ_DELTAS = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "quartz").a(this.RANGE_10_10).a())
                .a(quartzDeltaCount); // .b(32)*/

        List<PlacementModifier> oreQuartzNether = (List<PlacementModifier>) ImmutableList.of(new TallNether_CountPlacement(ConstantInt.of(16), biome, "quartz"),
                new TallNether_InSquarePlacement(biome, "quartz"), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
        this.ORE_QUARTZ_NETHER = OreFeatures.ORE_QUARTZ.placed(oreQuartzNether);

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> quartzCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "quartz"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(16)));
        this.ORE_QUARTZ_NETHER = TWorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "quartz").a(this.RANGE_10_10).a())
                .a(quartzCount); // .b(16)*/

        // Setting: ancient-debris1
        this.ORE_ANCIENT_DEBRIS_LARGE = OreFeatures.ORE_ANCIENT_DEBRIS_LARGE.placed(new PlacementModifier[]{new TallNether_CountPlacement(ConstantInt.of(1), biome, "ancient-debris1"), new TallNether_InSquarePlacement(biome, "ancient-debris1"),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24)),
                        BiomeFilter.biome()});

        /*WorldGenFeatureChanceDecoratorRangeConfiguration largeDebrisHeightProvider = new WorldGenFeatureChanceDecoratorRangeConfiguration(
                UniformHeight.a(VerticalAnchor.a(8), VerticalAnchor.a(24)));
        WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> debrisLargeCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "ancient-debris1"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(1)));
        this.ORE_DEBRIS_LARGE = TWorldGenerator.SCATTERED_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 3, 1.0F))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "ancient-debris1")
                                .a(largeDebrisHeightProvider).a())
                .a(debrisLargeCount);*/

        // Setting: ancient-debris2
        this.ORE_ANCIENT_DEBRIS_SMALL = OreFeatures.ORE_ANCIENT_DEBRIS_LARGE.placed(new PlacementModifier[]{new TallNether_CountPlacement(ConstantInt.of(1), biome, "ancient-debris2"), new TallNether_InSquarePlacement(biome, "ancient-debris1"),
                        PlacementUtils.RANGE_8_8, BiomeFilter.biome()});

        /*WorldGenDecoratorConfigured<WorldGenDecoratorFrequencyConfiguration> debrisSmallCount = (new TallNether_WorldGenDecoratorCount(
                WorldGenDecoratorFrequencyConfiguration.a, biome, "ancient-debris2"))
                        .a(new WorldGenDecoratorFrequencyConfiguration(ConstantInt.a(1)));
        this.ORE_DEBRIS_SMALL = TWorldGenerator.SCATTERED_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.d,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 2, 1.0F))
                .a((WorldGenDecoratorConfigured<?>) new TallNether_WorldGenDecoratorRange(
                        WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome, "ancient-debris2").a(this.RANGE_8_8)
                                .a())
                .a(debrisSmallCount);*/
    }
}
