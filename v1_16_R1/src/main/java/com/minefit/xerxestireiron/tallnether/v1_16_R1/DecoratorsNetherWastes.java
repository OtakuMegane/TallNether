package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_16_R1.BiomeBase;
import net.minecraft.server.v1_16_R1.BiomeDecoratorGroups;
import net.minecraft.server.v1_16_R1.BiomeHell;
import net.minecraft.server.v1_16_R1.Biomes;
import net.minecraft.server.v1_16_R1.Blocks;
import net.minecraft.server.v1_16_R1.IRegistry;
import net.minecraft.server.v1_16_R1.StructureFeature;
import net.minecraft.server.v1_16_R1.StructureGenerator;
import net.minecraft.server.v1_16_R1.WorldGenCarverAbstract;
import net.minecraft.server.v1_16_R1.WorldGenCarverConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenCarverWrapper;
import net.minecraft.server.v1_16_R1.WorldGenDecorator;
import net.minecraft.server.v1_16_R1.WorldGenDecoratorDungeonConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenDecoratorHeightAverageConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenFeatureBastionPieces;
import net.minecraft.server.v1_16_R1.WorldGenFeatureBastionRemnantConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenFeatureComposite;
import net.minecraft.server.v1_16_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_16_R1.WorldGenFeatureConfigured;
import net.minecraft.server.v1_16_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenFeatureRuinedPortal;
import net.minecraft.server.v1_16_R1.WorldGenFeatureRuinedPortalConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenStage;
import net.minecraft.server.v1_16_R1.WorldGenSurface;
import net.minecraft.server.v1_16_R1.WorldGenSurfaceComposite;
import net.minecraft.server.v1_16_R1.WorldGenSurfaceConfigurationBase;
import net.minecraft.server.v1_16_R1.WorldGenerator;

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public class DecoratorsNetherWastes {

    private final BiomeHell biome;
    private List<WorldGenFeatureConfigured> originalDecoratorsUnderground;
    private List<WorldGenFeatureConfigured> originalDecoratorsVegetal;
    private List<WorldGenFeatureComposite> originalFeaturesAir;

    public DecoratorsNetherWastes() {
        this.biome = (BiomeHell) Biomes.NETHER_WASTES;
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
        // Taken from BiomeHell.java
        // Some type casts used in the decompiled code are not necessary when done here
        // Trial and error is involved
        // NMS methods in the early/dev releases of Spigot will sometimes change without a version update, so wait until stable release to be safe

        // Cave Generation
        WorldGenCarverWrapper caves = this.biome.a(
                (WorldGenCarverAbstract) new TallNether_WorldGenCavesHell(WorldGenFeatureConfigurationChance.b),
                (WorldGenCarverConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)));
        this.biome.a(WorldGenStage.Features.AIR, caves);

        // Not sure exactly
        WorldGenFeatureConfigured<?, ?> dunno = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.aL)
                .a(WorldGenDecorator.p.a(new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256)));
        this.biome.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);

        // The following two are from BiomeDecoratorGroups.ab(this)
        // They gen pretty rarely
        // Red shrooms
        WorldGenFeatureConfigured<?, ?> redShrooms = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.at)
                .a(WorldGenDecorator.j.a(new WorldGenDecoratorDungeonConfiguration(4)));
        this.biome.a(WorldGenStage.Decoration.VEGETAL_DECORATION, redShrooms);

        // Brown shrooms
        WorldGenFeatureConfigured<?, ?> brownShrooms = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.as)
                .a(WorldGenDecorator.j.a(new WorldGenDecoratorDungeonConfiguration(8)));
        this.biome.a(WorldGenStage.Decoration.VEGETAL_DECORATION, brownShrooms);

        // Lavafalls (lavafall)
        WorldGenFeatureConfigured<?, ?> lavaFalls = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.aM)
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration.a,
                        "lavafall").a(new WorldGenFeatureChanceDecoratorCountConfiguration(8, 4, 8, 128)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, lavaFalls);

        // Fire (fire)
        WorldGenFeatureConfigured<?, ?> fire = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.ap)
                .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration.a, "fire")
                        .a(new WorldGenDecoratorFrequencyConfiguration(10)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fire);

        // Soul Fire (soul-fire)
        WorldGenFeatureConfigured<?, ?> soulFire = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.aq)
                .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration.a, "soul-fire")
                        .a(new WorldGenDecoratorFrequencyConfiguration(10)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, soulFire);

        // Glowstone Sparse (glowstone1)
        WorldGenFeatureConfigured<?, ?> glowStone1 = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.k).a(
                new TallNether_WorldGenDecoratorNetherGlowstone(WorldGenDecoratorFrequencyConfiguration.a, "glowstone1")
                        .a(new WorldGenDecoratorFrequencyConfiguration(10)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowStone1);

        // Glowstone Main (glowstone2)
        WorldGenFeatureConfigured<?, ?> glowstone2 = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.k)
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration.a,
                        "glowstone2").a(new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 128)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowstone2);

        // Red Mushrooms (red-shroom)
        WorldGenFeatureConfigured<?, ?> redShrooms2 = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.at)
                .a(new TallNether_WorldGenDecoratorNetherChance(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "red-shroom").a(new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, redShrooms2);

        // Brown Mushrooms (brown-shroom)
        WorldGenFeatureConfigured<?, ?> brownShrooms2 = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.as)
                .a(new TallNether_WorldGenDecoratorNetherChance(WorldGenFeatureChanceDecoratorRangeConfiguration.a,
                        "brown-shroom").a(new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, brownShrooms2);

        // Set from BiomeDecoratorGroups.a method
        // Nether Gold Ore (nether-gold)
        WorldGenFeatureConfigured<?, ?> nether_gold = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_GOLD_ORE.getBlockData(), 10))
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration.a,
                        "nether-gold").a(new WorldGenFeatureChanceDecoratorCountConfiguration(10, 10, 20, 128)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, nether_gold);

        // Set from BiomeDecoratorGroups.a method
        // Nether Quartz (quartz)
        WorldGenFeatureConfigured<?, ?> quartz = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration.a,
                        "quartz").a(new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, quartz);

        // Magma Block (magma)
        WorldGenFeatureConfigured<?, ?> magma = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.MAGMA_BLOCK.getBlockData(), 33))
                .a(new TallNether_WorldGenDecoratorNetherMagma(WorldGenDecoratorFrequencyConfiguration.a)
                        .a(new WorldGenDecoratorFrequencyConfiguration(4)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, magma);

        // Hidden Lava (hidden-lava)
        WorldGenFeatureConfigured<?, ?> hiddenLava = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.aO)
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration.a,
                        "hidden-lava").a(new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, hiddenLava);

        // Set from BiomeDecoratorGroups.at method
        // Ancient Debris 1
        WorldGenFeatureConfigured<?, ?> ancient_debris1 = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 3))
                .a(new TallNether_WorldGenDecoratorHeightAverage(WorldGenDecoratorHeightAverageConfiguration.a,
                        "ancient-debris").a(new WorldGenDecoratorHeightAverageConfiguration(1, 16, 8)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, ancient_debris1);

        // Set from BiomeDecoratorGroups.at method
        // Ancient Debris 2
        WorldGenFeatureConfigured<?, ?> ancient_debris2 = WorldGenerator.NO_SURFACE_ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHER_ORE_REPLACEABLES,
                        Blocks.ANCIENT_DEBRIS.getBlockData(), 2))
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration.a,
                        "ancient-debris").a(new WorldGenFeatureChanceDecoratorCountConfiguration(2, 5, 0, 37)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, ancient_debris2);

        // Set from BiomeDecoratorGroups.a method
        // Gravel Patches
        WorldGenFeatureConfigured<?, ?> gravel_patch = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.GRAVEL.getBlockData(), 33))
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration.a,
                        "gravel-patch").a(new WorldGenFeatureChanceDecoratorCountConfiguration(2, 5, 0, 37)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, gravel_patch);

        // Set from BiomeDecoratorGroups.a method
        // Blackstone Patches
        WorldGenFeatureConfigured<?, ?> blackstone_patch = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.BLACKSTONE.getBlockData(), 33))
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration.a,
                        "blackstone-patch").a(new WorldGenFeatureChanceDecoratorCountConfiguration(2, 5, 10, 37)));
        this.biome.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, blackstone_patch);
    }

    private boolean doFixes(boolean restore) {
        // WorldGenSurfaces are hardcoded, I dunno why. All this shit is to change one number
        TallNether_WorldGenSurfaceNether tnwgs = new TallNether_WorldGenSurfaceNether(
                WorldGenSurfaceConfigurationBase.a);
        WorldGenSurface<WorldGenSurfaceConfigurationBase> asdfg = IRegistry.a(IRegistry.SURFACE_BUILDER, "nether",
                tnwgs);
        WorldGenSurfaceComposite wgsc = new WorldGenSurfaceComposite<>(asdfg, WorldGenSurface.M);

        try {
            Field mField = ReflectionHelper.getField(BiomeHell.class, "m", true);
            ReflectionHelper.setFinal(mField, this.biome, wgsc);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
