package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import com.google.common.collect.ImmutableSet;
import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;

import net.minecraft.server.v1_16_R2.BiomeDecoratorGroups;
import net.minecraft.server.v1_16_R2.Blocks;
import net.minecraft.server.v1_16_R2.FluidTypes;
import net.minecraft.server.v1_16_R2.IntSpread;
import net.minecraft.server.v1_16_R2.WorldGenBlockPlacerSimple;
import net.minecraft.server.v1_16_R2.WorldGenCarverAbstract;
import net.minecraft.server.v1_16_R2.WorldGenCarverConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenCarverWrapper;
import net.minecraft.server.v1_16_R2.WorldGenDecorator;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorHeightAverageConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorRange;
import net.minecraft.server.v1_16_R2.WorldGenFeatureBasaltColumnsConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R2.WorldGenFeatureEmptyConfiguration2;
import net.minecraft.server.v1_16_R2.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureStateProviderSimpl;
import net.minecraft.server.v1_16_R2.WorldGenerator;

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
    public final WorldGenFeatureConfigured<?, ?> PATCH_FIRE; // Setting fire
    public final WorldGenFeatureConfigured<?, ?> PATCH_SOUL_FIRE; // Setting soul-fire
    public final WorldGenFeatureConfigured<?, ?> GLOWSTONE; // GLOWSTONE
    public final WorldGenFeatureConfigured<?, ?> GLOWSTONE_EXTRA; // Setting glowstone1
    public final WorldGenFeatureConfigured<?, ?> ORE_MAGMA; // Setting magma-block
    public final WorldGenFeatureConfigured<?, ?> ORE_QUARTZ_NETHER; // ORE_QUARTZ_NETHER, ORE_QUARTZ_DELTAS
    public final WorldGenFeatureConfigured<?, ?> ORE_DEBRIS_LARGE; // Setting ancient-debris1
    public final WorldGenFeatureConfigured<?, ?> ORE_DEBRIS_SMALL; // ORE_DEBRIS_SMALL
    public final WorldGenFeatureConfigured<?, ?> ORE_GOLD_NETHER; // ORE_GOLD_NETHER, ORE_GOLD_DELTAS
    public final WorldGenFeatureConfigured<?, ?> SPRING_OPEN; // SPRING_OPEN
    public final WorldGenFeatureConfigured<?, ?> SPRING_DELTA; // SPRING_DELTA
    public final WorldGenFeatureConfigured<?, ?> SPRING_CLOSED; // SPRING_CLOSED
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
    public final WorldGenFeatureConfigured<?, ?> SMALL_BASALT_COLUMNS; // SMALL_BASALT_COLUMNS
    public final WorldGenFeatureConfigured<?, ?> LARGE_BASALT_COLUMNS; // LARGE_BASALT_COLUMNS
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
        this.GLOWSTONE = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.k)
                .a(new WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a)
                        .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(
                                this.biomeConfig.values.get("glowstone2-min-height"),
                                this.biomeConfig.values.get("glowstone2-max-offset"),
                                this.biomeConfig.values.get("glowstone2-max-height")))
                        .b(this.biomeConfig.values.get("glowstone2-attempts")));
        this.GLOWSTONE_EXTRA = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.k)
                .a(new TallNether_WorldGenDecoratorNetherGlowstone(WorldGenDecoratorFrequencyConfiguration.a,
                        this.biomeConfig.values.get("glowstone1-min-height"),
                        this.biomeConfig.values.get("glowstone1-min-height"))
                                .b(new WorldGenDecoratorFrequencyConfiguration(
                                        this.biomeConfig.values.get("glowstone1-attempts"))));
        this.ORE_MAGMA = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.MAGMA_BLOCK.getBlockData(), 33))
                .a(new TallNether_WorldGenDecoratorNetherMagma(WorldGenFeatureEmptyConfiguration2.a, biome)
                        .b(WorldGenFeatureEmptyConfiguration2.c).a())
                .b(4);
        this.ORE_QUARTZ_NETHER = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a(new WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a)
                        .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(
                                this.biomeConfig.values.get("quartz-min-height"),
                                this.biomeConfig.values.get("quartz-max-offset"),
                                this.biomeConfig.values.get("quartz-max-height")))
                        .b(this.biomeConfig.values.get("quartz-attempts")));
        // Normally this would be using WorldGenDecoratorDepthAverage but adapting settings would be a pain
        this.ORE_DEBRIS_LARGE = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 3))
                .a(new WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a)
                        .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(
                                this.biomeConfig.values.get("ancient-debris1-min-height"), 0,
                                this.biomeConfig.values.get("ancient-debris1-max-height")))
                        .b(this.biomeConfig.values.get("ancient-debris1-attempts")));
        /*this.ORE_DEBRIS_LARGE = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 3))
                .a(new TallNether_WorldGenDecoratorDepthAverage(WorldGenDecoratorHeightAverageConfiguration.a,
                        "ancient-debris").b(new WorldGenDecoratorHeightAverageConfiguration(16, 8)));*/
        this.ORE_DEBRIS_SMALL = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 2))
                .a(new WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a)
                        .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(
                                this.biomeConfig.values.get("ancient-debris2-min-height"),
                                this.biomeConfig.values.get("ancient-debris2-max-offset"),
                                this.biomeConfig.values.get("ancient-debris2-max-height")))
                        .b(this.biomeConfig.values.get("ancient-debris2-attempts")));
        this.ORE_GOLD_NETHER = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a(new WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a)
                        .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(
                                this.biomeConfig.values.get("nether-gold-min-height"),
                                this.biomeConfig.values.get("nether-gold-max-offset"),
                                this.biomeConfig.values.get("nether-gold-max-height")))
                        .b(this.biomeConfig.values.get("nether-gold-attempts")));
        this.SPRING_OPEN = WorldGenerator.SPRING_FEATURE.b(this.SPRING_OPEN_CONFIG)
                .a(new WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a)
                        .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(
                                this.biomeConfig.values.get("lavafall-min-height"),
                                this.biomeConfig.values.get("lavafall-max-offset"),
                                this.biomeConfig.values.get("lavafall-max-height")))
                        .b(this.biomeConfig.values.get("lavafall-attempts")));
        // Basalt Deltas version
        this.SPRING_DELTA = WorldGenerator.SPRING_FEATURE.b(this.SPRING_DELTA_CONFIG)
                .a(new WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a)
                        .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(
                                this.biomeConfig.values.get("lavafall-min-height"),
                                this.biomeConfig.values.get("lavafall-max-offset"),
                                this.biomeConfig.values.get("lavafall-max-height")))
                        .b(this.biomeConfig.values.get("lavafall-attempts")));
        this.SPRING_CLOSED = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.a.j)
                .a(new WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a)
                        .b(new WorldGenFeatureChanceDecoratorRangeConfiguration(
                                this.biomeConfig.values.get("hidden-lava-min-height"),
                                this.biomeConfig.values.get("hidden-lava-max-offset"),
                                this.biomeConfig.values.get("hidden-lava-max-height")))
                        .b(this.biomeConfig.values.get("hidden-lava-attempts")));
        this.SMALL_BASALT_COLUMNS = WorldGenerator.BASALT_COLUMNS
                .b(new WorldGenFeatureBasaltColumnsConfiguration(IntSpread.a(1), IntSpread.a(1, 3)))
                .a(WorldGenDecorator.C.b(new WorldGenDecoratorFrequencyConfiguration(2))); // Double frequency?
        this.LARGE_BASALT_COLUMNS = WorldGenerator.BASALT_COLUMNS
                .b(new WorldGenFeatureBasaltColumnsConfiguration(IntSpread.a(2, 1), IntSpread.a(5, 5)))
                .a(WorldGenDecorator.C.b(new WorldGenDecoratorFrequencyConfiguration(2))); // Double frequency?
    }
}
