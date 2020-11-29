package com.minefit.xerxestireiron.tallnether.v1_13_R2_2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_13_R2.BiomeBase;
import net.minecraft.server.v1_13_R2.BiomeHell;
import net.minecraft.server.v1_13_R2.BlockPredicate;
import net.minecraft.server.v1_13_R2.Blocks;
import net.minecraft.server.v1_13_R2.FluidTypeFlowing;
import net.minecraft.server.v1_13_R2.FluidTypes;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.StructureGenerator;
import net.minecraft.server.v1_13_R2.WorldGenCarver;
import net.minecraft.server.v1_13_R2.WorldGenCarverWrapper;
import net.minecraft.server.v1_13_R2.WorldGenDecoratorChanceConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureComposite;
import net.minecraft.server.v1_13_R2.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_13_R2.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureDecoratorEmptyConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureFlowingConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureMushroomConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenNether;
import net.minecraft.server.v1_13_R2.WorldGenNetherConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenStage;
import net.minecraft.server.v1_13_R2.WorldGenerator;

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public class Decorators {

    private final BiomeHell biomeHell;
    private List<WorldGenFeatureComposite> originalDecoratorsUnderground;
    private List<WorldGenFeatureComposite> originalDecoratorsVegetal;
    private List<WorldGenFeatureComposite> originalFeaturesAir;

    public Decorators() {
        this.biomeHell = (BiomeHell) BiomeBase.getBiome(8, IRegistry.BIOME.fromId(8));
        List<WorldGenFeatureComposite> underground = getDecoratorsList(
                WorldGenStage.Decoration.UNDERGROUND_DECORATION);
        this.originalDecoratorsUnderground = new ArrayList<>(underground);
        List<WorldGenFeatureComposite> vegetal = getDecoratorsList(WorldGenStage.Decoration.VEGETAL_DECORATION);
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
            Field aV = BiomeBase.class.getDeclaredField("aV");
            aV.setAccessible(true);
            featureMap = (Map<WorldGenStage.Features, List<WorldGenFeatureComposite>>) aV.get(biomeHell);
        } catch (Exception e) {
            e.printStackTrace();
            return featureMap.get(index);
        }

        return featureMap.get(index);
    }

    private boolean setFeaturesList(List<WorldGenFeatureComposite> featuresList, WorldGenStage.Features index) {
        Map<WorldGenStage.Features, List<WorldGenFeatureComposite>> featureMap = new HashMap();

        try {
            Field aV = BiomeBase.class.getDeclaredField("aV");
            aV.setAccessible(true);
            featureMap = (Map<WorldGenStage.Features, List<WorldGenFeatureComposite>>) aV.get(biomeHell);
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
            Field aW = BiomeBase.class.getDeclaredField("aW");
            aW.setAccessible(true);
            decorationMap = (Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite>>) aW.get(biomeHell);
        } catch (Exception e) {
            e.printStackTrace();
            return decorationMap.get(index);
        }

        return decorationMap.get(index);
    }

    private boolean setDecoratorsList(List<WorldGenFeatureComposite> decoratorsList, WorldGenStage.Decoration index) {
        Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite>> decoratorMap = new HashMap();

        try {
            Field aW = BiomeBase.class.getDeclaredField("aW");
            aW.setAccessible(true);
            decoratorMap = (Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite>>) aW.get(biomeHell);
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
        try {
            Field fieldE = FluidTypes.class.getField("e");
            WorldGenFeatureComposite<WorldGenFeatureFlowingConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> dunno;

            try {
                dunno = this.biomeHell.a(WorldGenerator.at,
                        new WorldGenFeatureFlowingConfiguration((FluidTypeFlowing) fieldE.get(null)), this.biomeHell.v,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256));
                this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            Field flLava;
            FluidTypeFlowing flLava2 = null;

            try {
                flLava = FluidTypes.class.getDeclaredField("LAVA");
                flLava.setAccessible(true);
                flLava2 = (FluidTypeFlowing) flLava.get(null);
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            WorldGenFeatureComposite<WorldGenFeatureFlowingConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> dunno = this.biomeHell
                    .a(WorldGenerator.at, new WorldGenFeatureFlowingConfiguration(flLava2), this.biomeHell.v,
                            new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256));
            this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);
        }

        // Brown Mushrooms
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenDecoratorChanceConfiguration> brownShrooms = this.biomeHell
                .a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), this.biomeHell.p,
                        new WorldGenDecoratorChanceConfiguration(4));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, brownShrooms);

        // Red Mushrooms
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenDecoratorChanceConfiguration> redShrooms = this.biomeHell
                .a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), this.biomeHell.p,
                        new WorldGenDecoratorChanceConfiguration(8));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, redShrooms);

        // Not per-world safe
        WorldGenFeatureComposite<WorldGenFeatureHellFlowingLavaConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> lavaFalls = this.biomeHell
                .a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(false),
                        new TallNether_WorldGenDecoratorHeightBiased("lavafall"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(8, 4, 8, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, lavaFalls);

        // Fire (fire)
        WorldGenFeatureComposite<WorldGenFeatureEmptyConfiguration, WorldGenDecoratorFrequencyConfiguration> fire = this.biomeHell
                .a(WorldGenerator.S, WorldGenFeatureConfiguration.e, new TallNether_WorldGenDecoratorNetherFire(),
                        new WorldGenDecoratorFrequencyConfiguration(10));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fire);

        // Glowstone Sparse (glowstone1)
        WorldGenFeatureComposite<WorldGenFeatureEmptyConfiguration, WorldGenDecoratorFrequencyConfiguration> glowStone1 = this.biomeHell
                .a(WorldGenerator.W, WorldGenFeatureConfiguration.e, new TallNether_WorldGenDecoratorNetherGlowstone(),
                        new WorldGenDecoratorFrequencyConfiguration(10));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowStone1);

        // Glowstone Main (glowstone2)
        WorldGenFeatureComposite<WorldGenFeatureEmptyConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> glowstone2 = this.biomeHell
                .a(WorldGenerator.W, WorldGenFeatureConfiguration.e,
                        new TallNether_WorldGenDecoratorHeightBiased("glowstone"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowstone2);

        // Brown Mushrooms (brown-shrooms)
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenFeatureChanceDecoratorRangeConfiguration> brownShrooms2 = this.biomeHell
                .a(new TallNether_WorldGenMushrooms(), new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM),
                        this.biomeHell.x, new WorldGenFeatureChanceDecoratorRangeConfiguration(1.0F, 1, 0, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, brownShrooms2);

        // Red Mushrooms (red-shrooms)
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenFeatureChanceDecoratorRangeConfiguration> redShrooms2 = this.biomeHell
                .a(new TallNether_WorldGenMushrooms(), new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM),
                        this.biomeHell.x, new WorldGenFeatureChanceDecoratorRangeConfiguration(1.0F, 1, 0, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, redShrooms2);

        // Nether Quartz (quartz)
        WorldGenFeatureComposite<WorldGenFeatureOreConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> quartz = this.biomeHell
                .a(WorldGenerator.an,
                        new WorldGenFeatureOreConfiguration(BlockPredicate.a(Blocks.NETHERRACK),
                                Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14),
                        new TallNether_WorldGenDecoratorHeightBiased("quartz"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, quartz);

        // Magma Block (magma)
        WorldGenFeatureComposite<WorldGenFeatureOreConfiguration, WorldGenDecoratorFrequencyConfiguration> magma = this.biomeHell
                .a(WorldGenerator.an,
                        new WorldGenFeatureOreConfiguration(BlockPredicate.a(Blocks.NETHERRACK),
                                Blocks.MAGMA_BLOCK.getBlockData(), 33),
                        new TallNether_WorldGenDecoratorNetherMagma(), new WorldGenDecoratorFrequencyConfiguration(4));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, magma);

        // Hidden Lava (hidden-lava)
        WorldGenFeatureComposite<WorldGenFeatureHellFlowingLavaConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> hiddenLava = this.biomeHell
                .a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(true),
                        new TallNether_WorldGenDecoratorHeightBiased("hidden-lava"),
                        new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, hiddenLava);
    }

    private boolean registerFortress(boolean restore) {
        StructureGenerator<WorldGenNetherConfiguration> fortressGen;

        if (restore) {
            fortressGen = new WorldGenNether();
        } else {
            fortressGen = new TallNether_WorldGenNether();
        }

        this.biomeHell.a((StructureGenerator) fortressGen,
                (WorldGenFeatureConfiguration) (new WorldGenNetherConfiguration()));
        WorldGenFeatureComposite<WorldGenNetherConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> fortress = this.biomeHell
                .a(fortressGen, new WorldGenNetherConfiguration(), this.biomeHell.n,
                        WorldGenFeatureDecoratorConfiguration.e);
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fortress);

        try {
            Method b = net.minecraft.server.v1_13_R2.WorldGenFactory.class.getDeclaredMethod("b",
                    new Class[] { Class.class, String.class });
            b.setAccessible(true);

            if (restore) {
                b.invoke(net.minecraft.server.v1_13_R2.WorldGenFactory.class,
                        new Object[] { WorldGenNether.a.class, "Fortress" });
            } else {
                b.invoke(net.minecraft.server.v1_13_R2.WorldGenFactory.class,
                        new Object[] { TallNether_WorldGenNether.a.class, "Fortress" });
            }
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
