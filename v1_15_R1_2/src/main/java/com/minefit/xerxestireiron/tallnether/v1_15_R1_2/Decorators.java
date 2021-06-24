package com.minefit.xerxestireiron.tallnether.v1_15_R1_2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.minefit.xerxestireiron.tallnether.ReflectionHelper;

import net.minecraft.server.v1_15_R1.BiomeBase;
import net.minecraft.server.v1_15_R1.BiomeDecoratorGroups;
import net.minecraft.server.v1_15_R1.BiomeHell;
import net.minecraft.server.v1_15_R1.Biomes;
import net.minecraft.server.v1_15_R1.Blocks;
import net.minecraft.server.v1_15_R1.IRegistry;
import net.minecraft.server.v1_15_R1.StructureGenerator;
import net.minecraft.server.v1_15_R1.WorldGenCarverAbstract;
import net.minecraft.server.v1_15_R1.WorldGenCarverConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenCarverWrapper;
import net.minecraft.server.v1_15_R1.WorldGenDecorator;
import net.minecraft.server.v1_15_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenFactory;
import net.minecraft.server.v1_15_R1.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenFeatureComposite;
import net.minecraft.server.v1_15_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_15_R1.WorldGenFeatureConfigured;
import net.minecraft.server.v1_15_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenStage;
import net.minecraft.server.v1_15_R1.WorldGenSurface;
import net.minecraft.server.v1_15_R1.WorldGenSurfaceComposite;
import net.minecraft.server.v1_15_R1.WorldGenSurfaceConfigurationBase;
import net.minecraft.server.v1_15_R1.WorldGenerator;

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public class Decorators {

    private final BiomeHell biomeHell;
    private List<WorldGenFeatureConfigured> originalDecoratorsUnderground;
    private List<WorldGenFeatureConfigured> originalDecoratorsVegetal;
    private List<WorldGenFeatureComposite> originalFeaturesAir;
    private StructureGenerator<WorldGenFeatureEmptyConfiguration> vanilla_fortress = WorldGenerator.NETHER_BRIDGE;

    public Decorators() {
        this.biomeHell = (BiomeHell) Biomes.NETHER;
        List<WorldGenFeatureConfigured> underground = getDecoratorsList(
                WorldGenStage.Decoration.UNDERGROUND_DECORATION);
        this.originalDecoratorsUnderground = new ArrayList<>(underground);
        List<WorldGenFeatureConfigured> vegetal = getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION);
        this.originalDecoratorsVegetal = new ArrayList<>(vegetal);
        List<WorldGenFeatureComposite> air = getFeaturesList(WorldGenStage.Features.AIR);
        this.originalFeaturesAir = new ArrayList<>(air);
    }

    public boolean alreadySet() {
        return WorldGenerator.ao.get("Fortress".toLowerCase(Locale.ROOT)).getClass().getSimpleName()
                .equals("TallNether_WorldGenNether");
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
        registerFortress(false);
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
            featureMap = (Map<WorldGenStage.Features, List<WorldGenFeatureComposite>>) q.get(biomeHell);
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
            featureMap = (Map<WorldGenStage.Features, List<WorldGenFeatureComposite>>) q.get(biomeHell);
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
            decorationMap = (Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured>>) r.get(biomeHell);
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
            decoratorMap = (Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured>>) r.get(biomeHell);
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
        WorldGenCarverWrapper caves = this.biomeHell.a(
                (WorldGenCarverAbstract) new TallNether_WorldGenCavesHell(WorldGenFeatureConfigurationChance::a),
                (WorldGenCarverConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)));
        this.biomeHell.a(WorldGenStage.Features.AIR, caves);

        // Not sure exactly
        WorldGenFeatureConfigured<?, ?> dunno = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.ac)
                .a(WorldGenDecorator.p.a(new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256)));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);

        // Lavafalls (lavafall)
        WorldGenFeatureConfigured<?, ?> lavaFalls = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.ad)
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a,
                        "lavafall").a(new WorldGenFeatureChanceDecoratorCountConfiguration(8, 4, 8, 128)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, lavaFalls);

        // Fire (fire)
        WorldGenFeatureConfigured<?, ?> fire = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.K)
                .a(new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration::a)
                        .a(new WorldGenDecoratorFrequencyConfiguration(10)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fire);

        // Glowstone Sparse (glowstone1)
        WorldGenFeatureConfigured<?, ?> glowStone1 = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.e)
                .a(new TallNether_WorldGenDecoratorNetherGlowstone(WorldGenDecoratorFrequencyConfiguration::a)
                        .a(new WorldGenDecoratorFrequencyConfiguration(10)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowStone1);

        // Glowstone Main (glowstone2)
        WorldGenFeatureConfigured<?, ?> glowstone2 = WorldGenerator.GLOWSTONE_BLOB.b(WorldGenFeatureConfiguration.e)
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a,
                        "glowstone").a(new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 128)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowstone2);

        // Brown Mushrooms (brown-shroom)
        WorldGenFeatureConfigured<?, ?> brownShrooms = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.N)
                .a(new TallNether_WorldGenDecoratorNetherChance(WorldGenFeatureChanceDecoratorRangeConfiguration::a,
                        "brown-shroom").a(new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, brownShrooms);

        // Red Mushrooms (red-shroom)
        WorldGenFeatureConfigured<?, ?> redShrooms = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.M)
                .a(new TallNether_WorldGenDecoratorNetherChance(WorldGenFeatureChanceDecoratorRangeConfiguration::a,
                        "red-shroom").a(new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, redShrooms);

        // Nether Quartz (quartz)
        WorldGenFeatureConfigured<?, ?> quartz = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a,
                        "quartz").a(new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, quartz);

        // Magma Block (magma)
        WorldGenFeatureConfigured<?, ?> magma = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.MAGMA_BLOCK.getBlockData(), 33))
                .a(new TallNether_WorldGenDecoratorNetherMagma(WorldGenDecoratorFrequencyConfiguration::a)
                        .a(new WorldGenDecoratorFrequencyConfiguration(4)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, magma);

        // Hidden Lava (hidden-lava)
        WorldGenFeatureConfigured<?, ?> hiddenLava = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.ae)
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a,
                        "hidden-lava").a(new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, hiddenLava);
    }

    private boolean registerFortress(boolean restore) {
        StructureGenerator<WorldGenFeatureEmptyConfiguration> fortressGen;

        if (restore) {
            fortressGen = this.vanilla_fortress;
        } else {
            fortressGen = new TallNether_WorldGenNether(WorldGenFeatureEmptyConfiguration::a);
        }

        this.biomeHell.a(fortressGen.b(WorldGenFeatureConfiguration.e));
        WorldGenFeatureConfigured<?, ?> fortress = fortressGen.b(WorldGenFeatureConfiguration.e);
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fortress);

        try {
            StructureGenerator<WorldGenFeatureEmptyConfiguration> sGen = IRegistry.a(IRegistry.STRUCTURE_FEATURE,
                    "Fortress".toLowerCase(Locale.ROOT), fortressGen);
            Field c = ReflectionHelper.getField(WorldGenFactory.class, "c", false);
            ReflectionHelper.fieldSetter(c, null, sGen);
            Field NETHER_BRIDGE = ReflectionHelper.getField(WorldGenerator.class, "NETHER_BRIDGE", false);
            ReflectionHelper.fieldSetter(NETHER_BRIDGE, null, sGen);
            WorldGenerator.ao.replace("Fortress".toLowerCase(Locale.ROOT), fortressGen);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean doFixes(boolean restore) {
        // WorldGenSurfaces are hardcoded, I dunno why. All this shit is to change one number
        TallNether_WorldGenSurfaceNether tnwgs = new TallNether_WorldGenSurfaceNether(
                WorldGenSurfaceConfigurationBase::a);
        WorldGenSurface<WorldGenSurfaceConfigurationBase> asdfg = IRegistry.a(IRegistry.SURFACE_BUILDER, "nether",
                tnwgs);
        WorldGenSurfaceComposite wgsc = new WorldGenSurfaceComposite<>(asdfg, WorldGenSurface.E);

        try {
            Field nField = ReflectionHelper.getField(BiomeHell.class, "n", true);
            ReflectionHelper.fieldSetter(nField, this.biomeHell, wgsc);
        } catch (Throwable t) {
            return false;
        }

        return true;
    }
}
