package com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition;

import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRuinedPortalConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureVillageConfiguration;

// Mojang: net.minecraft.data.worldgen.StructureFeatures
// Spigot: net.minecraft.world.level.levelgen.feature.StructureFeature
public class TStructureFeatures {
    public static final StructureFeature<WorldGenFeatureEmptyConfiguration, ? extends StructureGenerator<WorldGenFeatureEmptyConfiguration>> NETHER_BRIDGE = StructureFeatures.o;
    public static final StructureFeature<WorldGenFeatureChanceDecoratorRangeConfiguration, ? extends StructureGenerator<WorldGenFeatureChanceDecoratorRangeConfiguration>> NETHER_FOSSIL = StructureFeatures.p;
    public static final StructureFeature<WorldGenFeatureVillageConfiguration, ? extends StructureGenerator<WorldGenFeatureVillageConfiguration>> BASTION_REMNANT = StructureFeatures.s;
    public static final StructureFeature<WorldGenFeatureRuinedPortalConfiguration, ? extends StructureGenerator<WorldGenFeatureRuinedPortalConfiguration>> RUINED_PORTAL_NETHER = StructureFeatures.E;
}
