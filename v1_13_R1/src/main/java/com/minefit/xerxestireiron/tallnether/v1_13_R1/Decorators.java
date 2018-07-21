package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_13_R1.BiomeBase;
import net.minecraft.server.v1_13_R1.BiomeHell;
import net.minecraft.server.v1_13_R1.BlockPredicate;
import net.minecraft.server.v1_13_R1.Blocks;
import net.minecraft.server.v1_13_R1.FluidTypes;
import net.minecraft.server.v1_13_R1.StructureGenerator;
import net.minecraft.server.v1_13_R1.WorldGenCarver;
import net.minecraft.server.v1_13_R1.WorldGenCarverWrapper;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorChanceConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureComposite;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_13_R1.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureDecoratorEmptyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureFlowingConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureMushroomConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenMushrooms;
import net.minecraft.server.v1_13_R1.WorldGenNether;
import net.minecraft.server.v1_13_R1.WorldGenNetherConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenStage;
import net.minecraft.server.v1_13_R1.WorldGenerator;

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public class Decorators {

    private final BiomeHell biomeHell;
    private List<WorldGenFeatureComposite> originalDecoratorsUnderground;
    private List<WorldGenFeatureComposite> originalDecoratorsVegetal;
    private List<WorldGenFeatureComposite> originalFeaturesAir;
    private final ConfigValues configValues;

    public Decorators(ConfigValues configValues) {
        this.biomeHell = (BiomeHell) BiomeBase.a(8);
        this.configValues = configValues;
    }

    public boolean initialize() {
        doFixes(false);
        List<WorldGenFeatureComposite> underground = getDecoratorsList(WorldGenStage.Decoration.UNDERGROUND_DECORATION);
        this.originalDecoratorsUnderground = new ArrayList<>(underground);
        underground.clear();
        List<WorldGenFeatureComposite> vegetal = getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION);
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
        List<WorldGenFeatureComposite> underground = getDecoratorsList(WorldGenStage.Decoration.UNDERGROUND_DECORATION);
        underground.clear();

        if (!setDecoratorsList(this.originalDecoratorsUnderground, WorldGenStage.Decoration.UNDERGROUND_DECORATION)) {
            return false;
        }

        List<WorldGenFeatureComposite> vegetal = getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION);
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
            Field aX = BiomeBase.class.getDeclaredField("aX");
            aX.setAccessible(true);
            featureMap = (Map<WorldGenStage.Features, List<WorldGenFeatureComposite>>) aX.get(biomeHell);
        } catch (Exception e) {
            e.printStackTrace();
            return featureMap.get(index);
        }

        return featureMap.get(index);
    }

    private boolean setFeaturesList(List<WorldGenFeatureComposite> featuresList, WorldGenStage.Features index) {
        Map<WorldGenStage.Features, List<WorldGenFeatureComposite>> featureMap = new HashMap();

        try {
            Field aX = BiomeBase.class.getDeclaredField("aX");
            aX.setAccessible(true);
            featureMap = (Map<WorldGenStage.Features, List<WorldGenFeatureComposite>>) aX.get(biomeHell);
            featureMap.get(index).addAll(featuresList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private List<WorldGenFeatureComposite> getDecoratorsList(WorldGenStage.Decoration index) {
        Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite>> decorationMap = new HashMap();

        try {
            Field aY = BiomeBase.class.getDeclaredField("aY");
            aY.setAccessible(true);
            decorationMap = (Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite>>) aY.get(biomeHell);
        } catch (Exception e) {
            e.printStackTrace();
            return decorationMap.get(index);
        }

        return decorationMap.get(index);
    }

    private boolean setDecoratorsList(List<WorldGenFeatureComposite> decoratorsList, WorldGenStage.Decoration index) {
        Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite>> decoratorMap = new HashMap();

        try {
            Field aY = BiomeBase.class.getDeclaredField("aY");
            aY.setAccessible(true);
            decoratorMap = (Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite>>) aY.get(biomeHell);
            decoratorMap.get(index).addAll(decoratorsList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void setNewDecorators() {
        // Cave Generation
        WorldGenCarverWrapper caves = this.biomeHell.a((WorldGenCarver) new TallNether_WorldGenCavesHell(),
                (WorldGenFeatureConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)));
        this.biomeHell.a(WorldGenStage.Features.AIR, caves);

        // No fucking clue yet
        WorldGenFeatureComposite<WorldGenFeatureFlowingConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> dunno = this.biomeHell
                .a(WorldGenerator.at, new WorldGenFeatureFlowingConfiguration(FluidTypes.e), this.biomeHell.w,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);

        // Brown Mushrooms
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenDecoratorChanceConfiguration> brownShrooms = this.biomeHell
                .a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), this.biomeHell.q,
                        new WorldGenDecoratorChanceConfiguration(4));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, brownShrooms);

        // Red Mushrooms
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenDecoratorChanceConfiguration> redShrooms = this.biomeHell
                .a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), this.biomeHell.q,
                        new WorldGenDecoratorChanceConfiguration(8));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, redShrooms);

        // Lavafalls (lavafall)
        WorldGenFeatureComposite<WorldGenFeatureHellFlowingLavaConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> lavaFalls = this.biomeHell
                .a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(false), this.biomeHell.u,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.lavafallAttempts,
                                this.configValues.lavafallMinHeight, this.configValues.lavafallMaxMinus,
                                this.configValues.lavafallMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, lavaFalls);

        // Fire (fire)
        WorldGenFeatureComposite<WorldGenFeatureEmptyConfiguration, WorldGenDecoratorFrequencyConfiguration> fire = this.biomeHell
                .a(WorldGenerator.S, WorldGenFeatureConfiguration.e,
                        new TallNether_WorldGenDecoratorNetherFire(this.configValues),
                        new WorldGenDecoratorFrequencyConfiguration(this.configValues.fireAttempts));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fire);

        // Glowstone Sparse (glowstone1)
        WorldGenFeatureComposite<WorldGenFeatureEmptyConfiguration, WorldGenDecoratorFrequencyConfiguration> glowStone1 = this.biomeHell
                .a(WorldGenerator.W, WorldGenFeatureConfiguration.e,
                        new TallNether_WorldGenDecoratorNetherGlowstone(this.configValues),
                        new WorldGenDecoratorFrequencyConfiguration(this.configValues.glowstone1Attempts));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowStone1);

        // Glowstone Main (glowstone2)
        WorldGenFeatureComposite<WorldGenFeatureEmptyConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> glowstone2 = this.biomeHell
                .a(WorldGenerator.W, WorldGenFeatureConfiguration.e, this.biomeHell.u,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.glowstone2Attempts,
                                this.configValues.glowstone2MinHeight, this.configValues.glowstone2MaxMinus,
                                this.configValues.glowstone2MaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowstone2);

        // Brown Mushrooms (brown-shrooms)
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenFeatureChanceDecoratorRangeConfiguration> brownShrooms2 = this.biomeHell
                .a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), this.biomeHell.y,
                        new WorldGenFeatureChanceDecoratorRangeConfiguration(1.0F,
                                this.configValues.brownShroomMinHeight, 0, this.configValues.brownShroomMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, brownShrooms2);

        // Red Mushrooms (red-shrooms)
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenFeatureChanceDecoratorRangeConfiguration> redShrooms2 = this.biomeHell
                .a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), this.biomeHell.y,
                        new WorldGenFeatureChanceDecoratorRangeConfiguration(1.0F, this.configValues.redShroomMinHeight,
                                0, this.configValues.redShroomMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, redShrooms2);

        // Nether Quartz (quartz)
        WorldGenFeatureComposite<WorldGenFeatureOreConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> quartz = this.biomeHell
                .a(WorldGenerator.an,
                        new WorldGenFeatureOreConfiguration(
                                BlockPredicate.a(Blocks.NETHERRACK), Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14),
                        this.biomeHell.u,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.quartzAttempts,
                                this.configValues.quartzMinHeight, this.configValues.quartzMaxMinus,
                                this.configValues.quartzMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, quartz);

        // Magma Block (magma)
        WorldGenFeatureComposite<WorldGenFeatureOreConfiguration, WorldGenDecoratorFrequencyConfiguration> magma = this.biomeHell
                .a(WorldGenerator.an,
                        new WorldGenFeatureOreConfiguration(BlockPredicate.a(Blocks.NETHERRACK),
                                Blocks.MAGMA_BLOCK.getBlockData(), 33),
                        new TallNether_WorldGenDecoratorNetherMagma(this.configValues),
                        new WorldGenDecoratorFrequencyConfiguration(this.configValues.magmaAttempts));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, magma);

        // Hidden Lava (hidden-lava)
        WorldGenFeatureComposite<WorldGenFeatureHellFlowingLavaConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> hiddenLava = this.biomeHell
                .a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(true), this.biomeHell.u,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(this.configValues.hiddenLavaAttempts,
                                this.configValues.hiddenLavaMinHeight, this.configValues.hiddenLavaMaxMinus,
                                this.configValues.hiddenLavaMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, hiddenLava);
    }

    private boolean registerFortress(boolean restore) {
        if (this.configValues.generateFortress) {
            StructureGenerator<WorldGenNetherConfiguration> fortressGen;

            if (restore) {
                fortressGen = new WorldGenNether();
            } else {
                fortressGen = new TallNether_WorldGenNether(this.configValues);
            }

            this.biomeHell.a((StructureGenerator) fortressGen,
                    (WorldGenFeatureConfiguration) (new WorldGenNetherConfiguration()));
            WorldGenFeatureComposite<WorldGenNetherConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> fortress = this.biomeHell
                    .a(fortressGen, new WorldGenNetherConfiguration(), this.biomeHell.n,
                            WorldGenFeatureDecoratorConfiguration.e);
            this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fortress);

            try {
                Method b = net.minecraft.server.v1_13_R1.WorldGenFactory.class.getDeclaredMethod("b",
                        new Class[] { Class.class, String.class });
                b.setAccessible(true);

                if (restore) {
                    b.invoke(net.minecraft.server.v1_13_R1.WorldGenFactory.class,
                            new Object[] { WorldGenNether.a.class, "Fortress" });
                } else {
                    b.invoke(net.minecraft.server.v1_13_R1.WorldGenFactory.class,
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
        try {
            // Fixes placing mushrooms outside of range when changing height + work around hardcoded values
            Field ah = net.minecraft.server.v1_13_R1.WorldGenerator.class.getDeclaredField("ah");
            ah.setAccessible(true);

            if (restore) {
                setFinal(ah, new WorldGenMushrooms(), this.biomeHell);
            } else {
                setFinal(ah, new TallNether_WorldGenMushrooms(this.configValues), this.biomeHell);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

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
