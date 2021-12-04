package com.minefit.xerxestireiron.tallnether.v1_18_R1;

import java.util.List;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class BiomeDecorators {

    public final PlacedFeature DELTA = NetherPlacements.DELTA;
    public final PlacedFeature SMALL_BASALT_COLUMNS = NetherPlacements.SMALL_BASALT_COLUMNS;
    public final PlacedFeature LARGE_BASALT_COLUMNS = NetherPlacements.LARGE_BASALT_COLUMNS;
    public final PlacedFeature BASALT_BLOBS = NetherPlacements.BASALT_BLOBS;
    public final PlacedFeature BLACKSTONE_BLOBS = NetherPlacements.BLACKSTONE_BLOBS;
    public final PlacedFeature GLOWSTONE_EXTRA; // Setting glowstone1
    public final PlacedFeature GLOWSTONE; // Setting glowstone2
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
        this.GLOWSTONE_EXTRA = NetherFeatures.GLOWSTONE_EXTRA.placed(new PlacementModifier[] {
                new TallNether_CountPlacement(BiasedToBottomInt.of(0, 9), biome, "glowstone1"),
                new TallNether_InSquarePlacement(biome, "glowstone1"), PlacementUtils.RANGE_4_4, BiomeFilter.biome() });

        // Setting: glowstone2
        this.GLOWSTONE = NetherFeatures.GLOWSTONE_EXTRA.placed(
                new PlacementModifier[] { new TallNether_CountPlacement(ConstantInt.of(10), biome, "glowstone2"),
                        new TallNether_InSquarePlacement(biome, "glowstone2"), PlacementUtils.FULL_RANGE,
                        BiomeFilter.biome() });

        // Setting lavafall
        this.SPRING_DELTA = NetherFeatures.SPRING_LAVA_NETHER.placed(new PlacementModifier[] {
                new TallNether_CountPlacement(ConstantInt.of(16), biome, "lavafall"),
                new TallNether_InSquarePlacement(biome, "lavafall"), PlacementUtils.RANGE_4_4, BiomeFilter.biome() });

        this.SPRING_OPEN = NetherFeatures.SPRING_NETHER_OPEN.placed(new PlacementModifier[] {
                new TallNether_CountPlacement(ConstantInt.of(8), biome, "lavafall"),
                new TallNether_InSquarePlacement(biome, "lavafall"), PlacementUtils.RANGE_4_4, BiomeFilter.biome() });

        // Setting: hidden-lava
        this.SPRING_CLOSED = NetherFeatures.SPRING_NETHER_CLOSED.placed(
                new PlacementModifier[] { new TallNether_CountPlacement(ConstantInt.of(16), biome, "hidden-lava"),
                        new TallNether_InSquarePlacement(biome, "hidden-lava"), PlacementUtils.RANGE_10_10,
                        BiomeFilter.biome() });

        this.SPRING_CLOSED_DOUBLE = NetherFeatures.SPRING_NETHER_CLOSED.placed(
                new PlacementModifier[] { new TallNether_CountPlacement(ConstantInt.of(32), biome, "hidden-lava"),
                        new TallNether_InSquarePlacement(biome, "hidden-lava"), PlacementUtils.RANGE_10_10,
                        BiomeFilter.biome() });

        // Setting: fire
        List<PlacementModifier> firePlacement = (List<PlacementModifier>) ImmutableList.of(
                new TallNether_CountPlacement(UniformInt.of(0, 5), biome, "fire"),
                new TallNether_InSquarePlacement(biome, "fire"), PlacementUtils.RANGE_4_4, BiomeFilter.biome());
        this.PATCH_FIRE = NetherFeatures.PATCH_FIRE.placed(firePlacement);

        // Setting: soul-fire
        List<PlacementModifier> soulFirePlacement = (List<PlacementModifier>) ImmutableList.of(
                new TallNether_CountPlacement(UniformInt.of(0, 5), biome, "soul-fire"),
                new TallNether_InSquarePlacement(biome, "soul-fire"), PlacementUtils.RANGE_4_4, BiomeFilter.biome());
        this.PATCH_SOUL_FIRE = NetherFeatures.PATCH_SOUL_FIRE.placed(soulFirePlacement);

        // Setting: magma-block
        List<PlacementModifier> oreMagma = (List<PlacementModifier>) ImmutableList.of(
                new TallNether_CountPlacement(ConstantInt.of(4), biome, "magma-block"),
                new TallNether_InSquarePlacement(biome, "magma-block"),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(27), VerticalAnchor.absolute(36)),
                BiomeFilter.biome());
        this.ORE_MAGMA = OreFeatures.ORE_MAGMA.placed(oreMagma);

        // Setting: nether-gold
        List<PlacementModifier> oreGoldDeltas = (List<PlacementModifier>) ImmutableList.of(
                new TallNether_CountPlacement(ConstantInt.of(20), biome, "nether-gold"),
                new TallNether_InSquarePlacement(biome, "nether-gold"), PlacementUtils.RANGE_10_10,
                BiomeFilter.biome());
        this.ORE_GOLD_DELTAS = OreFeatures.ORE_NETHER_GOLD.placed(oreGoldDeltas);

        List<PlacementModifier> oreGoldNether = (List<PlacementModifier>) ImmutableList.of(
                new TallNether_CountPlacement(ConstantInt.of(10), biome, "nether-gold"),
                new TallNether_InSquarePlacement(biome, "nether-gold"), PlacementUtils.RANGE_10_10,
                BiomeFilter.biome());
        this.ORE_GOLD_NETHER = OreFeatures.ORE_NETHER_GOLD.placed(oreGoldNether);

        // Setting: quartz
        List<PlacementModifier> oreQuartzDeltas = (List<PlacementModifier>) ImmutableList.of(
                new TallNether_CountPlacement(ConstantInt.of(32), biome, "quartz"),
                new TallNether_InSquarePlacement(biome, "quartz"), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
        this.ORE_QUARTZ_DELTAS = OreFeatures.ORE_QUARTZ.placed(oreQuartzDeltas);

        List<PlacementModifier> oreQuartzNether = (List<PlacementModifier>) ImmutableList.of(
                new TallNether_CountPlacement(ConstantInt.of(16), biome, "quartz"),
                new TallNether_InSquarePlacement(biome, "quartz"), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
        this.ORE_QUARTZ_NETHER = OreFeatures.ORE_QUARTZ.placed(oreQuartzNether);

        // Setting: ancient-debris1
        this.ORE_ANCIENT_DEBRIS_LARGE = OreFeatures.ORE_ANCIENT_DEBRIS_LARGE.placed(
                new PlacementModifier[] { new TallNether_CountPlacement(ConstantInt.of(1), biome, "ancient-debris1"),
                        new TallNether_InSquarePlacement(biome, "ancient-debris1"),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24)),
                        BiomeFilter.biome() });

        // Setting: ancient-debris2
        this.ORE_ANCIENT_DEBRIS_SMALL = OreFeatures.ORE_ANCIENT_DEBRIS_LARGE.placed(
                new PlacementModifier[] { new TallNether_CountPlacement(ConstantInt.of(1), biome, "ancient-debris2"),
                        new TallNether_InSquarePlacement(biome, "ancient-debris1"), PlacementUtils.RANGE_8_8,
                        BiomeFilter.biome() });
    }
}
