package com.minefit.xerxestireiron.tallnether.v1_17_R1.BiomeModifiers;

import com.minefit.xerxestireiron.tallnether.v1_17_R1.BiomeDecorators;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.SurfaceComposites;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldInfo;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TStructureFeatures;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TWorldGenCarvers;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TWorldGenStage;

import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;

public class BasaltDeltasModifier extends BiomeModifier {
    private BiomeBase biomeBase;
    private BiomeDecorators biomeDecorators;
    private BiomeSettingsGeneration originalBiomeSettings;
    private BiomeSettingsGeneration modifiedBiomeSettings;
    private SurfaceComposites surfaceComposites;

    public BasaltDeltasModifier(WorldInfo worldInfo, BiomeBase biomeBase) {
        this.biomeBase = biomeBase;
        this.originalBiomeSettings = biomeBase.e();
        this.biomeDecorators = new BiomeDecorators("basalt-deltas");
        this.surfaceComposites = new SurfaceComposites();
        this.modifiedBiomeSettings = createModifiedSettings();
    }

    public boolean modify() {
        return injectSettings(this.biomeBase, this.modifiedBiomeSettings);
    }

    private BiomeSettingsGeneration createModifiedSettings () {
        BiomeSettingsGeneration.a biomeSettingsGeneration_a = new BiomeSettingsGeneration.a();

        // BiomeSettingsDefault.u()
        biomeSettingsGeneration_a.a(this.surfaceComposites.BASALT_DELTAS);
        biomeSettingsGeneration_a.a(TStructureFeatures.RUINED_PORTAL_NETHER);
        biomeSettingsGeneration_a.a(TWorldGenStage.Features.AIR, TWorldGenCarvers.NETHER_CAVE);
        biomeSettingsGeneration_a.a(TStructureFeatures.NETHER_BRIDGE);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.SURFACE_STRUCTURES, this.biomeDecorators.DELTA);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.SPRING_LAVA_DOUBLE);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.SURFACE_STRUCTURES, this.biomeDecorators.SMALL_BASALT_COLUMNS);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.SURFACE_STRUCTURES, this.biomeDecorators.LARGE_BASALT_COLUMNS);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.BASALT_BLOBS);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.BLACKSTONE_BLOBS);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_DELTA);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_FIRE);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_SOUL_FIRE);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE_EXTRA);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.BROWN_MUSHROOM_NETHER);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.RED_MUSHROOM_NETHER);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_MAGMA);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_CLOSED_DOUBLE);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GOLD_DELTAS);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_QUARTZ_DELTAS);

        // BiomeSettings.as(biomesettingsgeneration_a)
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_DEBRIS_LARGE);
        biomeSettingsGeneration_a.a(TWorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_DEBRIS_SMALL);

        return biomeSettingsGeneration_a.a();
    }
}
