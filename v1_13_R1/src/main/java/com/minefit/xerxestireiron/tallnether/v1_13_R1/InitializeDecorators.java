package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;

import net.minecraft.server.v1_13_R1.BiomeBase;
import net.minecraft.server.v1_13_R1.BiomeHell;
import net.minecraft.server.v1_13_R1.Blocks;
import net.minecraft.server.v1_13_R1.FluidTypes;
import net.minecraft.server.v1_13_R1.StructureGenerator;
import net.minecraft.server.v1_13_R1.WorldGenCarver;
import net.minecraft.server.v1_13_R1.WorldGenCarverWrapper;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorChanceConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureComposite;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_13_R1.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureDecoratorEmptyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureFlowingConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureMushroomConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenNetherConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenStage;
import net.minecraft.server.v1_13_R1.WorldGenerator;
import net.minecraft.server.v1_13_R1.WorldGenStage.Decoration;
import net.minecraft.server.v1_13_R1.WorldGenStage.Features;

@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
public class InitializeDecorators {

    private final ConfigurationSection worldConfig;
    private final BiomeHell biomeHell;
    //private final ConfigValues configValues;

    public InitializeDecorators(ConfigurationSection worldConfig) {
        this.worldConfig = worldConfig;
        this.biomeHell = (BiomeHell) BiomeBase.a(8);
        //this.configValues = new ConfigValues(worldConfig);

        doFixes();
        registerCaves();

        Field aY;

        try {
            aY = BiomeBase.class.getDeclaredField("aY");
            aY.setAccessible(true);

            Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite>> decList = (Map<Decoration, List<WorldGenFeatureComposite>>) aY
                    .get(biomeHell);
            decList.get(WorldGenStage.Decoration.UNDERGROUND_DECORATION).clear();
            decList.get(WorldGenStage.Decoration.VEGETAL_DECORATION).clear();

            // ???
            Field aX = BiomeBase.class.getDeclaredField("aX");
            aX.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerFortress();
        setNewDecorators();
    }

    private void setNewDecorators() {
        // No fucking clue yet
        WorldGenFeatureComposite<WorldGenFeatureFlowingConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> dunno = this.biomeHell
                .a(WorldGenerator.at, new WorldGenFeatureFlowingConfiguration(FluidTypes.e), BiomeBase.w,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, dunno);

        // Brown Mushrooms (brown-shroom)
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenDecoratorChanceConfiguration> brownMushrooms = this.biomeHell
                .a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), this.biomeHell.q,
                        new WorldGenDecoratorChanceConfiguration(1));
        this.biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, brownMushrooms);

        // Red Mushrooms (red-shroom)
        WorldGenFeatureComposite<WorldGenFeatureMushroomConfiguration, WorldGenDecoratorChanceConfiguration> redMushrooms = this.biomeHell
                .a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), this.biomeHell.q,
                        new WorldGenDecoratorChanceConfiguration(1));
        biomeHell.a(WorldGenStage.Decoration.VEGETAL_DECORATION, redMushrooms);

        // Lavafalls (lavafall)
        WorldGenFeatureComposite<WorldGenFeatureHellFlowingLavaConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> lavaFalls = this.biomeHell
                .a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(false), this.biomeHell.u,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(ConfigValues.lavafallAttempts,
                                ConfigValues.lavafallMinHeight, ConfigValues.lavafallMaxMinus,
                                ConfigValues.lavafallMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, lavaFalls);

        // Glowstone Sparse (glowstone1)
        WorldGenFeatureComposite<WorldGenFeatureEmptyConfiguration, WorldGenDecoratorFrequencyConfiguration> glowStone1 = this.biomeHell
                .a(WorldGenerator.W, WorldGenFeatureConfiguration.e, this.biomeHell.P,
                        new WorldGenDecoratorFrequencyConfiguration(10));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, glowStone1);

        // Hidden Lava (hidden-lava)
        WorldGenFeatureComposite<WorldGenFeatureHellFlowingLavaConfiguration, WorldGenFeatureChanceDecoratorCountConfiguration> hiddenLava = this.biomeHell
                .a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(true), this.biomeHell.u,
                        new WorldGenFeatureChanceDecoratorCountConfiguration(ConfigValues.hiddenLavaAttempts,
                                ConfigValues.hiddenLavaMinHeight, ConfigValues.hiddenLavaMaxMinus,
                                ConfigValues.hiddenLavaMaxHeight));
        this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, hiddenLava);
    }

    private void registerCaves() {
        WorldGenCarverWrapper caves = this.biomeHell.a((WorldGenCarver) new TallNether_WorldGenCavesHell(),
                (WorldGenFeatureConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)));
        this.biomeHell.a(WorldGenStage.Features.AIR, caves);
    }

    private void registerFortress() {
        if (ConfigValues.generateFortress) {
            StructureGenerator<WorldGenNetherConfiguration> fortressGen = new TallNether_WorldGenNether(null, this.worldConfig);
            this.biomeHell.a((StructureGenerator) fortressGen, (WorldGenFeatureConfiguration) (new WorldGenNetherConfiguration()));
            WorldGenFeatureComposite<WorldGenNetherConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> fortress = this.biomeHell
                    .a(fortressGen, new WorldGenNetherConfiguration(), TallNether_BiomeHell.n,
                            WorldGenFeatureDecoratorConfiguration.e);
            this.biomeHell.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, fortress);

            try {
                Method bb = net.minecraft.server.v1_13_R1.WorldGenFactory.class.getDeclaredMethod("b",
                        new Class[] { Class.class, String.class });
                bb.setAccessible(true);
                bb.invoke(net.minecraft.server.v1_13_R1.WorldGenFactory.class,
                        new Object[] { TallNether_WorldGenNether.a.class, "Fortress" });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doFixes() {
        try {
            // Fixes trying to place some things like mushrooms off the map when we change world height
            Field ah = net.minecraft.server.v1_13_R1.WorldGenerator.class.getDeclaredField("ah");
            ah.setAccessible(true);
            setFinal(ah, new TallNether_WorldGenMushrooms(worldConfig), this.biomeHell);

            // Fixes 128 height limit hardcoded
            Field P = net.minecraft.server.v1_13_R1.BiomeBase.class.getDeclaredField("P");
            P.setAccessible(true);
            setFinal(P, new TallNether_WorldGenDecoratorNetherGlowstone(this.worldConfig), this.biomeHell);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFinal(Field field, Object obj, Object instance) throws Exception {
        field.setAccessible(true);

        Field mf = Field.class.getDeclaredField("modifiers");
        mf.setAccessible(true);
        mf.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(instance, obj);
    }
}
