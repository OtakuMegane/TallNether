package com.minefit.xerxestireiron.tallnether.v1_15_R1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.minefit.xerxestireiron.tallnether.ConfigValues;

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
import net.minecraft.server.v1_15_R1.WorldGenerator;

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public class Decorators {

    private final BiomeHell biomeHell;
    private List<WorldGenFeatureConfigured> originalDecoratorsUnderground;
    private List<WorldGenFeatureConfigured> originalDecoratorsVegetal;
    private List<WorldGenFeatureComposite> originalFeaturesAir;
    private final ConfigValues configValues;
    private StructureGenerator<WorldGenFeatureEmptyConfiguration> vanilla_fortress = WorldGenerator.NETHER_BRIDGE;

    public Decorators(ConfigValues configValues) {
        this.biomeHell = (BiomeHell) Biomes.NETHER;
        this.configValues = configValues;
    }

    public boolean initialize() {
        doFixes(false);
        List<WorldGenFeatureConfigured> underground = getDecoratorsList(WorldGenStage.Decoration.UNDERGROUND_DECORATION);
        this.originalDecoratorsUnderground = new ArrayList<>(underground);
        underground.clear();
        List<WorldGenFeatureConfigured> vegetal = getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION);
        this.originalDecoratorsVegetal = new ArrayList<>(vegetal);
        vegetal.clear();
        List<WorldGenFeatureComposite> air = getFeaturesList(WorldGenStage.Features.AIR);
        this.originalFeaturesAir = new ArrayList<>(air);
        air.clear();

        if (!doFixes(false) || !registerFortress(false)) {
            return false;
        }

        setNewDecorators();
        return true;
    }

    public boolean restore() {
        List<WorldGenFeatureConfigured> underground = getDecoratorsList(WorldGenStage.Decoration.UNDERGROUND_DECORATION);
        underground.clear();

        if (!setDecoratorsList(this.originalDecoratorsUnderground, WorldGenStage.Decoration.UNDERGROUND_DECORATION)) {
            return false;
        }

        List<WorldGenFeatureConfigured> vegetal = getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION);
        vegetal.clear();

        if (!setDecoratorsList(this.originalDecoratorsVegetal, WorldGenStage.Decoration.UNDERGROUND_DECORATION)) {
            return false;
        }

        List<WorldGenFeatureComposite> air = getFeaturesList(WorldGenStage.Features.AIR);
        air.clear();
        if (!setFeaturesList(this.originalFeaturesAir, WorldGenStage.Features.AIR)) {
            return false;
        }

        if (!doFixes(true) || !registerFortress(true)) {
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
        WorldGenCarverWrapper caves = this.biomeHell.a((WorldGenCarverAbstract) new TallNether_WorldGenCavesHell(WorldGenFeatureConfigurationChance::a),
                (WorldGenCarverConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)));
        this.biomeHell.a(WorldGenStage.Features.AIR, caves);

        // Not sure exactly
        WorldGenFeatureConfigured<?, ?> dunno = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.Y)
                .a(WorldGenDecorator.p.a(new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256)));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);

        // Lavafalls (lavafall)
        WorldGenFeatureConfigured<?, ?> lavaFalls = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.Z)
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a,
                        "lavafall").a(
                                new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.lavafallAttempts,
                                        this.configValues.lavafallMinHeight, this.configValues.lavafallMaxMinus,
                                        this.configValues.lavafallMaxHeight)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, lavaFalls);

        // Fire (fire)
        WorldGenFeatureConfigured<?, ?> fire = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.G)
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
                        "glowstone")
                                .a(new WorldGenFeatureChanceDecoratorCountConfiguration(
                                        this.configValues.glowstone2Attempts, this.configValues.glowstone2MinHeight,
                                        this.configValues.glowstone2MaxMinus, this.configValues.glowstone2MaxHeight)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowstone2);

        // Brown Mushrooms (brown-shrooms)
        WorldGenFeatureConfigured<?, ?> brownShrooms = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.J)
                .a(WorldGenDecorator.r.a(new WorldGenFeatureChanceDecoratorRangeConfiguration(1.0F,
                        this.configValues.brownShroomMinHeight, 0, this.configValues.brownShroomMaxHeight)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, brownShrooms);

        // Red Mushrooms (red-shrooms)
        WorldGenFeatureConfigured<?, ?> redShrooms = WorldGenerator.RANDOM_PATCH.b(BiomeDecoratorGroups.I)
                .a(WorldGenDecorator.r.a(new WorldGenFeatureChanceDecoratorRangeConfiguration(1.0F,
                        this.configValues.redShroomMinHeight, 0, this.configValues.redShroomMaxHeight)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, redShrooms);

        // Nether Quartz (quartz)
        WorldGenFeatureConfigured<?, ?> quartz = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14))
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a,
                        "quartz").a(
                                new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.quartzAttempts,
                                        this.configValues.quartzMinHeight, this.configValues.quartzMaxMinus,
                                        this.configValues.quartzMaxHeight)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, quartz);

        // Magma Block (magma)
        WorldGenFeatureConfigured<?, ?> magma = WorldGenerator.ORE
                .b(new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                        Blocks.MAGMA_BLOCK.getBlockData(), 33))
                .a(new TallNether_WorldGenDecoratorNetherMagma(WorldGenDecoratorFrequencyConfiguration::a)
                        .a(new WorldGenDecoratorFrequencyConfiguration(4)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, magma);

        // Hidden Lava (hidden-lava)
        WorldGenFeatureConfigured<?, ?> hiddenLava = WorldGenerator.SPRING_FEATURE.b(BiomeDecoratorGroups.Z)
                .a(new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a,
                        "hidden-lava")
                                .a(new WorldGenFeatureChanceDecoratorCountConfiguration(
                                        this.configValues.hiddenLavaAttempts, this.configValues.hiddenLavaMinHeight,
                                        this.configValues.hiddenLavaMaxMinus, this.configValues.hiddenLavaMaxHeight)));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, hiddenLava);
    }

    private boolean registerFortress(boolean restore) {
        if (this.configValues.generateFortress) {
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
                ReflectionHelper.setFinal(c, null, sGen);
                Field NETHER_BRIDGE = ReflectionHelper.getField(WorldGenerator.class, "NETHER_BRIDGE", false);
                ReflectionHelper.setFinal(NETHER_BRIDGE, null, sGen);
                WorldGenerator.ao.replace("Fortress".toLowerCase(Locale.ROOT), fortressGen);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private boolean doFixes(boolean restore) {
        return true;
    }
}
