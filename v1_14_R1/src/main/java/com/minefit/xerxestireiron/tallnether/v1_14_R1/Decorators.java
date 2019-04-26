package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minefit.xerxestireiron.tallnether.ConfigValues;

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
import net.minecraft.server.v1_14_R1.WorldGenNether;
import net.minecraft.server.v1_14_R1.WorldGenStage;
import net.minecraft.server.v1_14_R1.WorldGenerator;

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public class Decorators {

    private final BiomeHell biomeHell;
    private List<WorldGenFeatureConfigured> originalDecoratorsUnderground;
    private List<WorldGenFeatureConfigured> originalDecoratorsVegetal;
    private List<WorldGenFeatureComposite> originalFeaturesAir;
    private final ConfigValues configValues;

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

        if (!doFixes(false) /*|| !registerFortress(false)*/) {
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

        if (!doFixes(true) /*|| !registerFortress(true)*/) {
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
        // Cave Generation
        WorldGenCarverWrapper caves = this.biomeHell.a((WorldGenCarverAbstract) new TallNether_WorldGenCavesHell(WorldGenFeatureConfigurationChance::a),
                (WorldGenCarverConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)));
        this.biomeHell.a(WorldGenStage.Features.AIR, caves);

        // Not sure exactly
            WorldGenFeatureConfigured<?> dunno = this.biomeHell
                    .a(WorldGenerator.SPRING_FEATURE, new WorldGenFeatureFlowingConfiguration(FluidTypes.LAVA.i()), WorldGenDecorator.p,
                            new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256));
            this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);

        // Lavafalls (lavafall)
        WorldGenFeatureConfigured<?> lavaFalls = this.biomeHell
                .a(WorldGenerator.NETHER_SPRING, new WorldGenFeatureHellFlowingLavaConfiguration(false), new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a, "lavafall"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.lavafallAttempts,
                                this.configValues.lavafallMinHeight, this.configValues.lavafallMaxMinus,
                                this.configValues.lavafallMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, lavaFalls);

        // Fire (fire)
        WorldGenFeatureConfigured<?> fire = this.biomeHell
                .a(WorldGenerator.HELL_FIRE, WorldGenFeatureConfiguration.e,
                        new TallNether_WorldGenDecoratorNetherFire(WorldGenDecoratorFrequencyConfiguration::a),
                        new WorldGenDecoratorFrequencyConfiguration(10));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fire);

        // Glowstone Sparse (glowstone1)
        WorldGenFeatureConfigured<?> glowStone1 = this.biomeHell
                .a(WorldGenerator.GLOWSTONE_BLOB, WorldGenFeatureConfiguration.e,
                        new TallNether_WorldGenDecoratorNetherGlowstone(WorldGenDecoratorFrequencyConfiguration::a),
                        new WorldGenDecoratorFrequencyConfiguration(10));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowStone1);

        // Glowstone Main (glowstone2)
        WorldGenFeatureConfigured<?> glowstone2 = this.biomeHell
                .a(WorldGenerator.GLOWSTONE_BLOB, WorldGenFeatureConfiguration.e, new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a, "glowstone"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.glowstone2Attempts,
                                this.configValues.glowstone2MinHeight, this.configValues.glowstone2MaxMinus,
                                this.configValues.glowstone2MaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowstone2);

        // Brown Mushrooms (brown-shrooms)
        WorldGenFeatureConfigured<?> brownShrooms2 = this.biomeHell
                .a(WorldGenerator.BUSH, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM.getBlockData()), WorldGenDecorator.r,
                        new WorldGenFeatureChanceDecoratorRangeConfiguration(1.0F,
                                this.configValues.brownShroomMinHeight, 0, this.configValues.brownShroomMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, brownShrooms2);

        // Red Mushrooms (red-shrooms)
        WorldGenFeatureConfigured<?> redShrooms2 = this.biomeHell
                .a(WorldGenerator.BUSH, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM.getBlockData()), WorldGenDecorator.r,
                        new WorldGenFeatureChanceDecoratorRangeConfiguration(1.0F, this.configValues.redShroomMinHeight,
                                0, this.configValues.redShroomMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, redShrooms2);

        // Nether Quartz (quartz)
        WorldGenFeatureConfigured<?> quartz = this.biomeHell
                .a(WorldGenerator.ORE,
                        new WorldGenFeatureOreConfiguration(
                                WorldGenFeatureOreConfiguration.Target.NETHERRACK, Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14),
                        new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a, "quartz"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.quartzAttempts,
                                this.configValues.quartzMinHeight, this.configValues.quartzMaxMinus,
                                this.configValues.quartzMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, quartz);

        // Magma Block (magma)
        WorldGenFeatureConfigured<?> magma = this.biomeHell
                .a(WorldGenerator.ORE,
                        new WorldGenFeatureOreConfiguration(WorldGenFeatureOreConfiguration.Target.NETHERRACK,
                                Blocks.MAGMA_BLOCK.getBlockData(), 33),
                        new TallNether_WorldGenDecoratorNetherMagma(WorldGenDecoratorFrequencyConfiguration::a),
                        new WorldGenDecoratorFrequencyConfiguration(4));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, magma);

        // Hidden Lava (hidden-lava)
        WorldGenFeatureConfigured<?> hiddenLava = this.biomeHell
                .a(WorldGenerator.NETHER_SPRING, new WorldGenFeatureHellFlowingLavaConfiguration(true), new TallNether_WorldGenDecoratorNetherHeight(WorldGenFeatureChanceDecoratorCountConfiguration::a, "hidden-lava"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.hiddenLavaAttempts,
                                this.configValues.hiddenLavaMinHeight, this.configValues.hiddenLavaMaxMinus,
                                this.configValues.hiddenLavaMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, hiddenLava);
    }

    private boolean registerFortress(boolean restore) {
        if (this.configValues.generateFortress) {
            StructureGenerator<WorldGenFeatureEmptyConfiguration> fortressGen;

            if (restore) {
                fortressGen = WorldGenerator.NETHER_BRIDGE;
            } else {
                fortressGen = new TallNether_WorldGenNether(WorldGenFeatureEmptyConfiguration::a, this.configValues);
            }

            this.biomeHell.a((StructureGenerator) fortressGen, WorldGenFeatureConfiguration.e);

            WorldGenFeatureConfigured<?> fortress = this.biomeHell
                    .a(fortressGen, WorldGenFeatureConfiguration.e, WorldGenDecorator.h,
                            WorldGenFeatureDecoratorConfiguration.e);

            this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fortress);

            try {
                Method b = net.minecraft.server.v1_14_R1.WorldGenFactory.class.getDeclaredMethod("b",
                        new Class[] { Class.class, String.class });
                b.setAccessible(true);

                if (restore) {
                    b.invoke(net.minecraft.server.v1_14_R1.WorldGenFactory.class,
                            new Object[] { WorldGenNether.a.class, "Fortress" });
                } else {
                    b.invoke(net.minecraft.server.v1_14_R1.WorldGenFactory.class,
                            new Object[] { TallNether_WorldGenNether.a.class, "Fortress" });
                }
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

    private void setFinal(Field field, Object instance, Object obj) throws Exception {
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(instance, obj);
    }
}
