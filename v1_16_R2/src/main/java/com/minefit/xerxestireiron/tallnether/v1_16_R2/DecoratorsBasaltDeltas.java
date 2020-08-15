package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableSet;

import net.minecraft.server.v1_16_R2.BiomeBasaltDeltas;
import net.minecraft.server.v1_16_R2.BiomeBase;
import net.minecraft.server.v1_16_R2.BiomeBase.a;
import net.minecraft.server.v1_16_R2.BiomeDecoratorGroups;
import net.minecraft.server.v1_16_R2.BiomeRegistry;
import net.minecraft.server.v1_16_R2.BiomeSettingsGeneration;
import net.minecraft.server.v1_16_R2.Biomes;
import net.minecraft.server.v1_16_R2.BiomesSettingsDefault;
import net.minecraft.server.v1_16_R2.Blocks;
import net.minecraft.server.v1_16_R2.FluidTypes;
import net.minecraft.server.v1_16_R2.IRegistry;
import net.minecraft.server.v1_16_R2.IRegistryCustom;
import net.minecraft.server.v1_16_R2.RegistryGeneration;
import net.minecraft.server.v1_16_R2.ResourceKey;
import net.minecraft.server.v1_16_R2.WorldGenBlockPlacerSimple;
import net.minecraft.server.v1_16_R2.WorldGenCarverAbstract;
import net.minecraft.server.v1_16_R2.WorldGenCarverConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenCarverWrapper;
import net.minecraft.server.v1_16_R2.WorldGenDecorator;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorHeightAverageConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureComposite;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R2.WorldGenFeatureEmptyConfiguration2;
import net.minecraft.server.v1_16_R2.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureStateProviderSimpl;
import net.minecraft.server.v1_16_R2.WorldGenStage;
import net.minecraft.server.v1_16_R2.WorldGenSurface;
import net.minecraft.server.v1_16_R2.WorldGenSurfaceComposite;
import net.minecraft.server.v1_16_R2.WorldGenSurfaceConfigurationBase;
import net.minecraft.server.v1_16_R2.WorldGenerator;

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public class DecoratorsBasaltDeltas {

    private List<WorldGenFeatureConfigured> originalDecoratorsUnderground;
    private List<WorldGenFeatureConfigured> originalDecoratorsVegetal;
    private List<WorldGenFeatureComposite> originalFeaturesAir;
    private final BiomeBase biome;

    public DecoratorsBasaltDeltas() {
        //this.biome = Biomes.BASALT_DELTAS;
        IRegistry<BiomeBase> iregistry_biome = RegistryGeneration.WORLDGEN_BIOME;
        this.biome = iregistry_biome.d(Biomes.BASALT_DELTAS);
        this.biome.e();
        // This may be how we set decorators
        BiomeSettingsGeneration.a biomeSettingsGeneration_a = new BiomeSettingsGeneration.a();
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecoratorGroups.ORE_BLACKSTONE);
        BiomeBase.a bb = new BiomeBase.a();
        bb.a
        List<WorldGenFeatureConfigured> underground = getDecoratorsList(
                WorldGenStage.Decoration.UNDERGROUND_DECORATION);
        this.originalDecoratorsUnderground = new ArrayList<>(underground);
        List<WorldGenFeatureConfigured> vegetal = getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION);
        this.originalDecoratorsVegetal = new ArrayList<>(vegetal);
        List<WorldGenFeatureComposite> air = getFeaturesList(WorldGenStage.Features.AIR);
        this.originalFeaturesAir = new ArrayList<>(air);
    }

    public boolean set() {
        return doFixes(false) && setDecorators();
    }

    public boolean restore() {
        return doFixes(true) && restoreDecorators();
    }

    private boolean setDecorators() {
        getDecoratorsList(WorldGenStage.Decoration.UNDERGROUND_DECORATION).clear();
        getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION).clear();
        getFeaturesList(WorldGenStage.Features.AIR).clear();
        setNewDecorators();
        return true;
    }

    private boolean restoreDecorators() {
        getDecoratorsList(WorldGenStage.Decoration.UNDERGROUND_DECORATION).clear();

        if (!setDecoratorsList(this.originalDecoratorsUnderground, WorldGenStage.Decoration.UNDERGROUND_DECORATION)) {
            return false;
        }

        getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION).clear();

        if (!setDecoratorsList(this.originalDecoratorsVegetal, WorldGenStage.Decoration.UNDERGROUND_DECORATION)) {
            return false;
        }

        getFeaturesList(WorldGenStage.Features.AIR).clear();
        if (!setFeaturesList(this.originalFeaturesAir, WorldGenStage.Features.AIR)) {
            return false;
        }

        return true;
    }

    private List<WorldGenFeatureComposite> getFeaturesList(WorldGenStage.Features index) {
        Map<WorldGenStage.Features, List<WorldGenFeatureComposite>> featureMap = new HashMap();
        try {
            Field q = BiomeBase.class.getDeclaredField("q");
            q.setAccessible(true);
            featureMap = (Map<WorldGenStage.Features, List<WorldGenFeatureComposite>>) q.get(this.biome);
        } catch (Exception e) {
            e.printStackTrace();
            return featureMap.get(index);
        }

        return featureMap.get(index);
    }

    private boolean setFeaturesList(List<WorldGenFeatureComposite> featuresList, WorldGenStage.Features index) {
        Map<WorldGenStage.Features, List<WorldGenFeatureComposite>> featureMap = new HashMap();

        try {
            Field q = BiomeBase.class.getDeclaredField("q");
            q.setAccessible(true);
            featureMap = (Map<WorldGenStage.Features, List<WorldGenFeatureComposite>>) q.get(this.biome);
            featureMap.get(index).addAll(featuresList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private List<WorldGenFeatureConfigured> getDecoratorsList(WorldGenStage.Decoration index) {
        Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured>> decorationMap = new HashMap();

        try {
            Field r = BiomeBase.class.getDeclaredField("r");
            r.setAccessible(true);
            decorationMap = (Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured>>) r.get(this.biome);
        } catch (Exception e) {
            e.printStackTrace();
            return decorationMap.get(index);
        }

        return decorationMap.get(index);
    }

    private boolean setDecoratorsList(List<WorldGenFeatureConfigured> decoratorsList, WorldGenStage.Decoration index) {
        Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured>> decoratorMap = new HashMap();

        try {
            Field r = BiomeBase.class.getDeclaredField("r");
            r.setAccessible(true);
            decoratorMap = (Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured>>) r.get(this.biome);
            decoratorMap.get(index).addAll(decoratorsList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void setNewDecorators() {
        // Taken from BiomeWarpedForest.java
        // Some type casts used in the decompiled code are not necessary when done here
        // Trial and error is involved
        // NMS methods in the early/dev releases of Spigot will sometimes change without a version update, so wait until stable release to be safe

        /*// Nether Fortress (BiomeDecoratorGroups.o)
        StructureFeature<WorldGenFeatureEmptyConfiguration, ? extends StructureGenerator<WorldGenFeatureEmptyConfiguration>> nether_fortress = StructureGenerator.FORTRESS
                .a(WorldGenFeatureEmptyConfiguration.b);
        this.biome.a(nether_fortress);*/

        // Cave Generation
        WorldGenCarverWrapper caves = this.biome.a(
                (WorldGenCarverAbstract) new TallNether_WorldGenCavesHell(WorldGenFeatureConfigurationChance.b),
                (WorldGenCarverConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)));
        biomeSettingsGeneration_a.a(WorldGenStage.Features.AIR, caves);

        // We curently do not clear SURFACE_STRUCTURES so no need to register decorators
        /*// ???
        WorldGenFeatureConfigured<?, ?> delta = WorldGenerator.DELTA_FEATURE.b(BiomeDecoratorGroups.aV)
                .a(WorldGenDecorator.b.a(new WorldGenDecoratorFrequencyConfiguration(40)));
        this.biome.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, delta);*/

        // Lava deltas
        // 1.16.2: .a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecoratorGroups.SPRING_LAVA_DOUBLE)
        /*WorldGenFeatureConfigured<?, ?> dunno = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.aL)
                .a(WorldGenDecorator.p.a(new WorldGenFeatureChanceDecoratorCountConfiguration(40, 8, 16, 256)));*/
        this.biome.a(WorldGenStage.Decoration.VEGETAL_DECORATION, BiomeDecorators.SPRING_LAVA_DOUBLE);

        // We curently do not clear SURFACE_STRUCTURES so no need to register decorators
        /*// Basalt Column ???
        WorldGenFeatureConfigured<?, ?> basalt_column1 = WorldGenerator.BASALT_COLUMNS.b(BiomeDecoratorGroups.aR)
                .a(WorldGenDecorator.b.a(new WorldGenDecoratorFrequencyConfiguration(4)));
        this.biome.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, basalt_column1);

        // TODO: Decode
        // Basalt Column
        WorldGenFeatureConfigured<?, ?> basalt_column2 = WorldGenerator.BASALT_COLUMNS.b(BiomeDecoratorGroups.aS)
                .a(WorldGenDecorator.b.a(new WorldGenDecoratorFrequencyConfiguration(2)));
        this.biome.a(WorldGenStage.Decoration.SURFACE_STRUCTURES, basalt_column2);*/

        // Replace netherrack with basalt
        // 1.16.2: .a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecoratorGroups.BASALT_BLOBS)
        /*WorldGenFeatureConfigured<?, ?> replace_blob1 = WorldGenerator.NETHERRACK_REPLACE_BLOBS
                .b(BiomeDecoratorGroups.aT)
                .a(WorldGenDecorator.n.a(new WorldGenFeatureChanceDecoratorCountConfiguration(75, 0, 0, 128)));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.BASALT_BLOBS);

        // Replace netherrack with blackstone
        // 1.16.2: .a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecoratorGroups.BLACKSTONE_BLOBS)
        /*WorldGenFeatureConfigured<?, ?> replace_blob2 = WorldGenerator.NETHERRACK_REPLACE_BLOBS
                .b(BiomeDecoratorGroups.aU)
                .a(WorldGenDecorator.n.a(new WorldGenFeatureChanceDecoratorCountConfiguration(25, 0, 0, 128)));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.BLACKSTONE_BLOBS);

        // Lava falls
        /*WorldGenFeatureHellFlowingLavaConfiguration SPRING_DELTA = (new WorldGenFeatureHellFlowingLavaConfiguration(FluidTypes.LAVA.h(), true, 4, 1, ImmutableSet.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL, Blocks.MAGMA_BLOCK, Blocks.BLACKSTONE)));
        WorldGenFeatureConfigured<?, ?> lavaFalls2 = WorldGenerator.SPRING_FEATURE.b(SPRING_DELTA)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "lavafall").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(4, 8, 128)).b(16));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.SPRING_DELTA);

        // Fire (fire)
        /*WorldGenFeatureRandomPatchConfiguration PATCH_FIRE = (new WorldGenFeatureRandomPatchConfiguration.a(new WorldGenFeatureStateProviderSimpl(Blocks.FIRE.getBlockData()), WorldGenBlockPlacerSimple.c)).a(64).a(ImmutableSet.of(Blocks.NETHERRACK)).b().d();
        WorldGenFeatureConfigured<?, ?> fire = WorldGenerator.RANDOM_PATCH.b(PATCH_FIRE)
                .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration.a, "fire")
                        .b(new WorldGenDecoratorFrequencyConfiguration(10)));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.PATCH_FIRE);

        // Soul Fire (soul-fire)
        /*WorldGenFeatureRandomPatchConfiguration PATCH_SOUL_FIRE = (new WorldGenFeatureRandomPatchConfiguration.a(new WorldGenFeatureStateProviderSimpl(Blocks.SOUL_FIRE.getBlockData()), WorldGenBlockPlacerSimple.c)).a(64).a(ImmutableSet.of(Blocks.NETHERRACK)).b().d();
        WorldGenFeatureConfigured<?, ?> soulFire = WorldGenerator.RANDOM_PATCH.b(PATCH_SOUL_FIRE)
                .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration.a, "soul-fire")
                        .b(new WorldGenDecoratorFrequencyConfiguration(10)));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.PATCH_SOUL_FIRE);

        // Glowstone Sparse (glowstone1)
        /*WorldGenFeatureConfigured<?, ?> glowStone1 = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.k).a(
                new TallNether_WorldGenDecoratorNetherGlowstone(WorldGenDecoratorFrequencyConfiguration.a, "glowstone1")
                        .b(new WorldGenDecoratorFrequencyConfiguration(10)));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.GLOWSTONE_EXTRA);

        // Glowstone Main (glowstone2)
        /*WorldGenFeatureConfigured<?, ?> glowstone2 = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.k)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "glowstone2").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(0, 0, 128)).b(10));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.GLOWSTONE);


        // Brown Mushrooms (brown-shroom)
        // 1.16.2: BiomeDecoratorGroups.PATCH_BROWN_MUSHROOM.d(256).a(4)
        /*WorldGenFeatureRandomPatchConfiguration PATCH_BROWN_MUSHROOM = (new WorldGenFeatureRandomPatchConfiguration.a(new WorldGenFeatureStateProviderSimpl(Blocks.BROWN_MUSHROOM.getBlockData()), WorldGenBlockPlacerSimple.c)).a(64).b().d();
        WorldGenFeatureConfigured<?, ?> brownShrooms2 = WorldGenerator.RANDOM_PATCH.b(PATCH_BROWN_MUSHROOM)
                .a(new TallNether_WorldGenDecoratorNetherChance(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "brown-shroom").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.BROWN_MUSHROOM_NETHER);


        // Red Mushrooms (red-shroom)
        // 1.16.2: BiomeDecoratorGroups.PATCH_RED_MUSHROOM.d(256).a(4)
        /*WorldGenFeatureRandomPatchConfiguration PATCH_RED_MUSHROOM = (new WorldGenFeatureRandomPatchConfiguration.a(new WorldGenFeatureStateProviderSimpl(Blocks.RED_MUSHROOM.getBlockData()), WorldGenBlockPlacerSimple.c)).a(64).b().d();
        WorldGenFeatureConfigured<?, ?> redShrooms2 = WorldGenerator.RANDOM_PATCH.b(PATCH_RED_MUSHROOM)
                .a(new TallNether_WorldGenDecoratorNetherChance(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "red-shroom").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.RED_MUSHROOM_NETHER);

        // Magma Block (magma)
        /*WorldGenFeatureConfigured<?, ?> magma = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.MAGMA_BLOCK.getBlockData(), 33))
                .a(new TallNether_WorldGenDecoratorNetherMagma(WorldGenFeatureEmptyConfiguration2.a)
                        .b(WorldGenFeatureEmptyConfiguration2.c)).b(4);*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.ORE_MAGMA);

        // Hidden Lava (hidden-lava)
        /*WorldGenFeatureConfigured<?, ?> hiddenLava = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.aO)
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "hidden-lava").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(32));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.SPRING_CLOSED_DOUBLE);

        // Set from BiomeDecoratorGroups.a method
        // Nether Gold Ore (nether-gold)
        /*WorldGenFeatureConfigured<?, ?> nether_gold = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "nether-gold").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(20));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.ORE_GOLD_DELTAS);

        // Set from BiomeDecoratorGroups.a method
        // Nether Quartz (quartz)
        /*WorldGenFeatureConfigured<?, ?> quartz = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "quartz").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(10, 20, 128)).b(32));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.ORE_QUARTZ_DELTAS);

        // Set from BiomeDecoratorGroups.at method
        // Ancient Debris 1
        /*WorldGenFeatureConfigured<?, ?> ancient_debris1 = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 3))
                .a(new TallNether_WorldGenDecoratorDepthAverage(WorldGenDecoratorHeightAverageConfiguration.a,
                        "ancient-debris").b(new WorldGenDecoratorHeightAverageConfiguration(16, 8)));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.ORE_DEBRIS_LARGE);

        // Set from BiomeDecoratorGroups.at method
        // Ancient Debris 2
        /*WorldGenFeatureConfigured<?, ?> ancient_debris2 = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 2))
                .a(new TallNether_WorldGenDecoratorRange(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "ancient-debris").b(new WorldGenFeatureChanceDecoratorRangeConfiguration(8, 16, 128)).b(1));*/
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecorators.ORE_DEBRIS_SMALL);
    }

    private boolean doFixes(boolean restore) {
        // WorldGenSurfaces are hardcoded, I dunno why. All this shit is to change one number
        TallNether_WorldGenSurfaceBasaltDeltas tnwgs = new TallNether_WorldGenSurfaceBasaltDeltas(
                WorldGenSurfaceConfigurationBase.a);
        WorldGenSurface<WorldGenSurfaceConfigurationBase> asdfg = IRegistry.a(IRegistry.SURFACE_BUILDER,
                "basalt_deltas", tnwgs);
        WorldGenSurfaceComposite wgsc = new WorldGenSurfaceComposite<>(asdfg, WorldGenSurface.R);

        try {
            Field mField = ReflectionHelper.getField(BiomeBasaltDeltas.class, "m", true);
            ReflectionHelper.setFinal(mField, this.biome, wgsc);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
