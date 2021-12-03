package com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers;

import com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeDecorators;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.WorldInfo;

import net.minecraft.data.worldgen.Carvers;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class CrimsonForestModifier extends BiomeModifier {
    private Biome biome;
    private BiomeDecorators biomeDecorators;
    private BiomeGenerationSettings originalBiomeSettings;
    private BiomeGenerationSettings modifiedBiomeSettings;

    public CrimsonForestModifier(WorldInfo worldInfo, Biome biome) {
        this.biome = biome;
        this.originalBiomeSettings = biome.getGenerationSettings();
        this.biomeDecorators = new BiomeDecorators("soul-sand-valley");
        this.modifiedBiomeSettings = createModifiedSettings();
    }

    public boolean modify() {
        return injectSettings(this.biome, this.modifiedBiomeSettings);
    }

    private BiomeGenerationSettings createModifiedSettings() {
        Builder biomeGenerationSettings_builder = new BiomeGenerationSettings.Builder();

        // BiomeSettingsDefault.v()
        //biomeGenerationSettings_builder.a(this.surfaceComposites.CRIMSON_FOREST);
        //biomeGenerationSettings_builder.a(TStructureFeatures.RUINED_PORTAL_NETHER);
        //biomeGenerationSettings_builder.a(TStructureFeatures.NETHER_BRIDGE);
        //biomeGenerationSettings_builder.a(TStructureFeatures.BASTION_REMNANT);

        //biomeGenerationSettings_builder.addCarver(Carving.AIR, this.biomeDecorators.NETHER_CAVE);
        biomeGenerationSettings_builder.addCarver(Carving.AIR, Carvers.NETHER_CAVE);
        biomeGenerationSettings_builder.addFeature(Decoration.VEGETAL_DECORATION, this.biomeDecorators.SPRING_LAVA);

        // BiomeDefaultFeatures.addDefaultMushrooms
        biomeGenerationSettings_builder.addFeature(Decoration.VEGETAL_DECORATION, this.biomeDecorators.BROWN_MUSHROOM_NORMAL);
        biomeGenerationSettings_builder.addFeature(Decoration.VEGETAL_DECORATION, this.biomeDecorators.RED_MUSHROOM_NORMAL);

        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_OPEN);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_FIRE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE_EXTRA);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_MAGMA);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_CLOSED);
        biomeGenerationSettings_builder.addFeature(Decoration.VEGETAL_DECORATION, this.biomeDecorators.WEEPING_VINES);
        biomeGenerationSettings_builder.addFeature(Decoration.VEGETAL_DECORATION, this.biomeDecorators.CRIMSON_FUNGI);
        biomeGenerationSettings_builder.addFeature(Decoration.VEGETAL_DECORATION, this.biomeDecorators.CRIMSON_FOREST_VEGETATION);

        // BiomeDefaultFeatures.addNetherDefaultOres
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GRAVEL_NETHER);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_BLACKSTONE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GOLD_NETHER);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_QUARTZ_NETHER);

        // BiomeDefaultFeatures.addNetherDefaultOres -> BiomeDefaultFeatures.addAncientDebris
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_ANCIENT_DEBRIS_LARGE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_ANCIENT_DEBRIS_SMALL);

        return biomeGenerationSettings_builder.build();
    }
}
