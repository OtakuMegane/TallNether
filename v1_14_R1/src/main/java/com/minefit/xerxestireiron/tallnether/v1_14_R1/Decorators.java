package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_14_R1.BiomeBase;
import net.minecraft.server.v1_14_R1.BiomeHell;
import net.minecraft.server.v1_14_R1.Biomes;
import net.minecraft.server.v1_14_R1.Blocks;
import net.minecraft.server.v1_14_R1.FluidTypes;
import net.minecraft.server.v1_14_R1.StructureGenerator;
import net.minecraft.server.v1_14_R1.WorldGenCarverAbstract;
import net.minecraft.server.v1_14_R1.WorldGenCarverConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenCarverWrapper;
import net.minecraft.server.v1_14_R1.WorldGenDecorator;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureComposite;
import net.minecraft.server.v1_14_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_14_R1.WorldGenFeatureConfigured;
import net.minecraft.server.v1_14_R1.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureFlowingConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureMushroomConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenStage;
import net.minecraft.server.v1_14_R1.WorldGenerator;

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

    public boolean set() {
        return doFixes(false) && setFortress(false) && setDecorators();
    }

    public boolean restore() {
        return doFixes(true) && setFortress(true) && restoreDecorators();
    }

    private boolean setDecorators() {
        getDecoratorsList(WorldGenStage.Decoration.UNDERGROUND_DECORATION).clear();
        getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION).clear();
        getFeaturesList(WorldGenStage.Features.AIR).clear();
        setNewDecorators();
        setFortress(false);
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
        WorldGenFeatureConfigured<?> dunno = this.biomeHell.a(WorldGenerator.SPRING_FEATURE,
                new WorldGenFeatureFlowingConfiguration(FluidTypes.LAVA.i()), WorldGenDecorator.p,
                new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);

        // Lavafalls (lavafall)
        WorldGenFeatureConfigured<?> lavaFalls = this.biomeHell
                .a(WorldGenerator.NETHER_SPRING, new WorldGenFeatureHellFlowingLavaConfiguration(false),
                        new TallNether_WorldGenDecoratorNetherHeight(
                                WorldGenFeatureChanceDecoratorCountConfiguration::a, "lavafall"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(8, 4, 8, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, lavaFalls);

        // Fire (fire)
        WorldGenFeatureConfigured<?> fire = this.biomeHell.a(WorldGenerator.HELL_FIRE, WorldGenFeatureConfiguration.e,
                new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration::a),
                new WorldGenDecoratorFrequencyConfiguration(10));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fire);

        // Glowstone Sparse (glowstone1)
        WorldGenFeatureConfigured<?> glowStone1 = this.biomeHell.a(WorldGenerator.GLOWSTONE_BLOB,
                WorldGenFeatureConfiguration.e,
                new TallNether_WorldGenDecoratorNetherGlowstone(WorldGenDecoratorFrequencyConfiguration::a),
                new WorldGenDecoratorFrequencyConfiguration(10));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowStone1);

        // Glowstone Main (glowstone2)
        WorldGenFeatureConfigured<?> glowstone2 = this.biomeHell
                .a(WorldGenerator.GLOWSTONE_BLOB, WorldGenFeatureConfiguration.e,
                        new TallNether_WorldGenDecoratorNetherHeight(
                                WorldGenFeatureChanceDecoratorCountConfiguration::a, "glowstone"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowstone2);

        // Brown Mushrooms (brown-shrooms)
        WorldGenFeatureConfigured<?> brownShrooms2 = this.biomeHell.a(WorldGenerator.BUSH,
                new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM.getBlockData()), WorldGenDecorator.r,
                new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, brownShrooms2);

        // Red Mushrooms (red-shrooms)
        WorldGenFeatureConfigured<?> redShrooms2 = this.biomeHell.a(WorldGenerator.BUSH,
                new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM.getBlockData()), WorldGenDecorator.r,
                new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, redShrooms2);

        // Nether Quartz (quartz)
        WorldGenFeatureConfigured<?> quartz = this.biomeHell.a(WorldGenerator.ORE,
                new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14),
                new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a,
                        "quartz"),
                new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, quartz);

        // Magma Block (magma)
        WorldGenFeatureConfigured<?> magma = this.biomeHell.a(WorldGenerator.ORE,
                new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.MAGMA_BLOCK.getBlockData(), 33),
                new TallNether_WorldGenDecoratorNetherMagma(WorldGenDecoratorFrequencyConfiguration::a),
                new WorldGenDecoratorFrequencyConfiguration(4));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, magma);

        // Hidden Lava (hidden-lava)
        WorldGenFeatureConfigured<?> hiddenLava = this.biomeHell
                .a(WorldGenerator.NETHER_SPRING, new WorldGenFeatureHellFlowingLavaConfiguration(true),
                        new TallNether_WorldGenDecoratorNetherHeight(
                                WorldGenFeatureChanceDecoratorCountConfiguration::a, "hidden-lava"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, hiddenLava);
    }

    private boolean setFortress(boolean restore) {
        StructureGenerator<WorldGenFeatureEmptyConfiguration> fortressGen;

        if (restore) {
            fortressGen = this.vanilla_fortress;
        } else {
            fortressGen = new TallNether_WorldGenNether(WorldGenFeatureEmptyConfiguration::a);
        }

        this.biomeHell.a((StructureGenerator) fortressGen, WorldGenFeatureConfiguration.e);
        WorldGenFeatureConfigured<?> fortress = this.biomeHell.a(fortressGen, WorldGenFeatureConfiguration.e,
                WorldGenDecorator.h, WorldGenFeatureDecoratorConfiguration.e);
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fortress);
        this.biomeHell.a((StructureGenerator) fortressGen,
                (WorldGenFeatureConfiguration) WorldGenFeatureConfiguration.e);

        try {
            Method a = net.minecraft.server.v1_14_R1.WorldGenFactory.class.getDeclaredMethod("a",
                    new Class[] { String.class, StructureGenerator.class });
            a.setAccessible(true);
            a.invoke(net.minecraft.server.v1_14_R1.WorldGenFactory.class, new Object[] { "Fortress", fortressGen });
            WorldGenerator.aP.put("Fortress".toLowerCase(), fortressGen);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean doFixes(boolean restore) {
        return true;
    }
}
