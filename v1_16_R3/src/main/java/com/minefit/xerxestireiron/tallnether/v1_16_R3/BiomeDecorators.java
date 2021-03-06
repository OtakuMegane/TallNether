package com.minefit.xerxestireiron.tallnether.v1_16_R3;

import com.google.common.collect.ImmutableSet;
import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators.TallNether_WorldGenCavesHell;
import com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators.TallNether_WorldGenDecoratorDepthAverage;
import com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators.TallNether_WorldGenDecoratorNetherFire;
import com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators.TallNether_WorldGenDecoratorNetherGlowstone;
import com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators.TallNether_WorldGenDecoratorNetherMagma;
import com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators.TallNether_WorldGenDecoratorRange;
import com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators.TallNether_WorldGenLightStone1;

import net.minecraft.server.v1_16_R3.BiomeDecoratorGroups;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.FluidTypes;
import net.minecraft.server.v1_16_R3.WorldGenBlockPlacerSimple;
import net.minecraft.server.v1_16_R3.WorldGenCarverAbstract;
import net.minecraft.server.v1_16_R3.WorldGenCarverConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenCarverWrapper;
import net.minecraft.server.v1_16_R3.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenDecoratorHeightAverageConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_16_R3.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R3.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFeatureEmptyConfiguration2;
import net.minecraft.server.v1_16_R3.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.server.v1_16_R3.WorldGenFeatureStateProviderSimpl;
import net.minecraft.server.v1_16_R3.WorldGenerator;

public class BiomeDecorators {
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final WorldInfo worldInfo;
    private final WorldConfig worldConfig;
    private final BiomeValues biomeConfig;

    private final WorldGenFeatureRandomPatchConfiguration PATCH_FIRE_CONFIG = new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(Blocks.FIRE.getBlockData()), WorldGenBlockPlacerSimple.c).a(64)
                    .a(ImmutableSet.of(Blocks.NETHERRACK)).b().d();
    private final WorldGenFeatureRandomPatchConfiguration PATCH_SOUL_FIRE_CONFIG = new WorldGenFeatureRandomPatchConfiguration.a(
            new WorldGenFeatureStateProviderSimpl(Blocks.SOUL_FIRE.getBlockData()), WorldGenBlockPlacerSimple.c).a(64)
                    .a(ImmutableSet.of(Blocks.SOUL_SOIL)).b().d();
    private final WorldGenFeatureHellFlowingLavaConfiguration SPRING_DELTA_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            FluidTypes.LAVA.h(), true, 4, 1,
            ImmutableSet.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL, Blocks.MAGMA_BLOCK, Blocks.BLACKSTONE));
    private final WorldGenFeatureHellFlowingLavaConfiguration SPRING_OPEN_CONFIG = new WorldGenFeatureHellFlowingLavaConfiguration(
            FluidTypes.LAVA.h(), false, 4, 1, ImmutableSet.of(Blocks.NETHERRACK));

    @SuppressWarnings("rawtypes")
    public final WorldGenCarverWrapper CAVES_HELL;
    public final WorldGenFeatureConfigured<?, ?> PATCH_FIRE;
    public final WorldGenFeatureConfigured<?, ?> PATCH_SOUL_FIRE;
    public final WorldGenFeatureConfigured<?, ?> GLOWSTONE;
    public final WorldGenFeatureConfigured<?, ?> TALLNETHER_GLOWSTONE; // TallNether variant of GLOWSTONE
    public final WorldGenFeatureConfigured<?, ?> GLOWSTONE_EXTRA;
    public final WorldGenFeatureConfigured<?, ?> ORE_MAGMA; // Setting magma-block
    public final WorldGenFeatureConfigured<?, ?> ORE_QUARTZ_NETHER;
    public final WorldGenFeatureConfigured<?, ?> ORE_QUARTZ_DELTAS;
    public final WorldGenFeatureConfigured<?, ?> ORE_DEBRIS_LARGE; // Setting ancient-debris1
    public final WorldGenFeatureConfigured<?, ?> ORE_DEBRIS_SMALL; // Setting ancient-debris2
    public final WorldGenFeatureConfigured<?, ?> ORE_GOLD_NETHER;
    public final WorldGenFeatureConfigured<?, ?> ORE_GOLD_DELTAS;
    public final WorldGenFeatureConfigured<?, ?> SPRING_OPEN; // Setting lavafall
    public final WorldGenFeatureConfigured<?, ?> SPRING_DELTA; // Setting lavafall (Basalt Deltas variant)
    public final WorldGenFeatureConfigured<?, ?> SPRING_CLOSED; // Setting hidden-lava
    public final WorldGenFeatureConfigured<?, ?> SPRING_CLOSED_DOUBLE; // Setting hidden-lava (Basalt Deltas variant)
    public final WorldGenFeatureConfigured<?, ?> BROWN_MUSHROOM_NETHER = BiomeDecoratorGroups.BROWN_MUSHROOM_NETHER;
    public final WorldGenFeatureConfigured<?, ?> RED_MUSHROOM_NETHER = BiomeDecoratorGroups.RED_MUSHROOM_NETHER;
    public final WorldGenFeatureConfigured<?, ?> BROWN_MUSHROOM_NORMAL = BiomeDecoratorGroups.BROWN_MUSHROOM_NORMAL;
    public final WorldGenFeatureConfigured<?, ?> RED_MUSHROOM_NORMAL = BiomeDecoratorGroups.RED_MUSHROOM_NORMAL;
    public final WorldGenFeatureConfigured<?, ?> BASALT_BLOBS = BiomeDecoratorGroups.BASALT_BLOBS;
    public final WorldGenFeatureConfigured<?, ?> BLACKSTONE_BLOBS = BiomeDecoratorGroups.BLACKSTONE_BLOBS;
    public final WorldGenFeatureConfigured<?, ?> SPRING_LAVA = BiomeDecoratorGroups.SPRING_LAVA;
    public final WorldGenFeatureConfigured<?, ?> SPRING_LAVA_DOUBLE = BiomeDecoratorGroups.SPRING_LAVA_DOUBLE;
    public final WorldGenFeatureConfigured<?, ?> WEEPING_VINES = BiomeDecoratorGroups.WEEPING_VINES;
    public final WorldGenFeatureConfigured<?, ?> CRIMSON_FUNGI = BiomeDecoratorGroups.CRIMSON_FUNGI;
    public final WorldGenFeatureConfigured<?, ?> CRIMSON_FOREST_VEGETATION = BiomeDecoratorGroups.CRIMSON_FOREST_VEGETATION;
    public final WorldGenFeatureConfigured<?, ?> WARPED_FUNGI = BiomeDecoratorGroups.WARPED_FUNGI;
    public final WorldGenFeatureConfigured<?, ?> WARPED_FOREST_VEGETATION = BiomeDecoratorGroups.WARPED_FOREST_VEGETATION;
    public final WorldGenFeatureConfigured<?, ?> NETHER_SPROUTS = BiomeDecoratorGroups.NETHER_SPROUTS;
    public final WorldGenFeatureConfigured<?, ?> TWISTING_VINES = BiomeDecoratorGroups.TWISTING_VINES;
    public final WorldGenFeatureConfigured<?, ?> SMALL_BASALT_COLUMNS = BiomeDecoratorGroups.SMALL_BASALT_COLUMNS;
    public final WorldGenFeatureConfigured<?, ?> LARGE_BASALT_COLUMNS = BiomeDecoratorGroups.LARGE_BASALT_COLUMNS;
    public final WorldGenFeatureConfigured<?, ?> ORE_SOUL_SAND = BiomeDecoratorGroups.ORE_SOUL_SAND;
    public final WorldGenFeatureConfigured<?, ?> ORE_GRAVEL_NETHER = BiomeDecoratorGroups.ORE_GRAVEL_NETHER;
    public final WorldGenFeatureConfigured<?, ?> ORE_BLACKSTONE = BiomeDecoratorGroups.ORE_BLACKSTONE;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BiomeDecorators(WorldInfo worldInfo, String biome) {
        this.worldInfo = worldInfo;
        this.worldConfig = this.configAccessor.getWorldConfig(this.worldInfo.worldName);
        this.biomeConfig = this.worldConfig.getBiomeValues(biome);

        this.CAVES_HELL = ((WorldGenCarverAbstract) (new TallNether_WorldGenCavesHell(
                WorldGenFeatureConfigurationChance.b)))
                        .a((WorldGenCarverConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)));
        this.PATCH_FIRE = WorldGenerator.RANDOM_PATCH.b(this.PATCH_FIRE_CONFIG)
                .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration.a, biome, "fire")
                        .b(new WorldGenDecoratorFrequencyConfiguration(10)));
        this.PATCH_SOUL_FIRE = WorldGenerator.RANDOM_PATCH.b(this.PATCH_SOUL_FIRE_CONFIG)
                .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration.a, biome,
                        "soul-fire").b(new WorldGenDecoratorFrequencyConfiguration(10)));
        this.GLOWSTONE = (WorldGenFeatureConfigured) ((WorldGenFeatureConfigured) ((WorldGenFeatureConfigured) (new TallNether_WorldGenLightStone1(
                WorldGenFeatureEmptyConfiguration.a)).b(WorldGenFeatureConfiguration.k).d(128)).a()).b(10);
        this.TALLNETHER_GLOWSTONE = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.k)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "glowstone2").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).a()); // .b(10)
        this.GLOWSTONE_EXTRA = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.k)
                .a(new TallNether_WorldGenDecoratorNetherGlowstone(WorldGenDecoratorFrequencyConfiguration.a, biome,
                        "glowstone1").b(new WorldGenDecoratorFrequencyConfiguration(10)));
        this.ORE_MAGMA = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.MAGMA_BLOCK.getBlockData(), 33))
                .a(new TallNether_WorldGenDecoratorNetherMagma(WorldGenFeatureEmptyConfiguration2.a, biome)
                        .b(WorldGenFeatureEmptyConfiguration2.c).a())
                .b(4);
        this.ORE_QUARTZ_NETHER = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "quartz").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).a()); // .b(16)
        this.ORE_QUARTZ_DELTAS = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "quartz").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).a()); // .b(32)
        this.ORE_DEBRIS_LARGE = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 3))
                .a(new TallNether_WorldGenDecoratorDepthAverage(WorldGenDecoratorHeightAverageConfiguration.a, biome,
                        "ancient-debris1").b(new WorldGenDecoratorHeightAverageConfiguration(16, 8)).a());
        this.ORE_DEBRIS_SMALL = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 2))
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "ancient-debris2").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(8, 16, 128)).a());
        this.ORE_GOLD_NETHER = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "nether-gold").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).a()); // .b(10)
        this.ORE_GOLD_DELTAS = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "nether-gold").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).a()); // b(20)
        this.SPRING_OPEN = WorldGenerator.SPRING_FEATURE.b(this.SPRING_OPEN_CONFIG)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "lavafall").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(4, 8, 128)).a()); // .b(8)
        this.SPRING_DELTA = WorldGenerator.SPRING_FEATURE.b(this.SPRING_DELTA_CONFIG)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "lavafall").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(4, 8, 128)).a()); // .b(16)
        this.SPRING_CLOSED = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.a.j)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "hidden-lava").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).a()); // .b(16)
        this.SPRING_CLOSED_DOUBLE = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.a.j)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a, biome,
                        "hidden-lava").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).a()); // .b(32)
    }
}
