package com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers;

import com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeDecorators;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.WorldInfo;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class BasaltDeltasModifier extends BiomeModifier {
    private Biome biome;
    private BiomeDecorators biomeDecorators;
    private BiomeGenerationSettings originalBiomeSettings;
    private BiomeGenerationSettings modifiedBiomeSettings;

    public BasaltDeltasModifier(WorldInfo worldInfo, Biome biome) {
        this.biome = biome;
        this.originalBiomeSettings = biome.getGenerationSettings();
        this.biomeDecorators = new BiomeDecorators("basalt-deltas");
        this.modifiedBiomeSettings = createModifiedSettings();
    }

    public boolean modify() {
        return injectSettings(this.biome, this.modifiedBiomeSettings);
    }

    private BiomeGenerationSettings createModifiedSettings () {
        Builder biomeGenerationSettings_builder = new BiomeGenerationSettings.Builder();

        // data.worldgen.biome.NetherBiomes.basaltDeltas
        biomeGenerationSettings_builder.addFeature(Decoration.SURFACE_STRUCTURES, this.biomeDecorators.DELTA);
        biomeGenerationSettings_builder.addFeature(Decoration.SURFACE_STRUCTURES, this.biomeDecorators.SMALL_BASALT_COLUMNS);
        biomeGenerationSettings_builder.addFeature(Decoration.SURFACE_STRUCTURES, this.biomeDecorators.LARGE_BASALT_COLUMNS);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.BASALT_BLOBS);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.BLACKSTONE_BLOBS);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_DELTA);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_FIRE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_SOUL_FIRE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE_EXTRA);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.BROWN_MUSHROOM_NETHER);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.RED_MUSHROOM_NETHER);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_MAGMA);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_CLOSED_DOUBLE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GOLD_DELTAS);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_QUARTZ_DELTAS);

        // data.worldgen.BiomeDefaultFeatures.addAncientDebris
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_ANCIENT_DEBRIS_LARGE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_ANCIENT_DEBRIS_SMALL);

        return biomeGenerationSettings_builder.build();
    }
}
