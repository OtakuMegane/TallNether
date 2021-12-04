package com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers;

import com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeDecorators;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.WorldInfo;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class SoulSandValleyModifier extends BiomeModifier {
    private Biome biome;
    private BiomeDecorators biomeDecorators;
    private BiomeGenerationSettings originalBiomeSettings;
    private BiomeGenerationSettings modifiedBiomeSettings;

    public SoulSandValleyModifier(WorldInfo worldInfo, Biome biome) {
        this.biome = biome;
        this.originalBiomeSettings = biome.getGenerationSettings();
        this.biomeDecorators = new BiomeDecorators("soul-sand-valley");
        this.modifiedBiomeSettings = createModifiedSettings();
    }

    public boolean modify() {
        return injectSettings(this.biome, this.modifiedBiomeSettings);
    }

    private BiomeGenerationSettings createModifiedSettings () {
        Builder biomeGenerationSettings_builder = new BiomeGenerationSettings.Builder();

        // data.worldgen.biome.NetherBiomes.soulSandValley
        biomeGenerationSettings_builder.addFeature(Decoration.VEGETAL_DECORATION, this.biomeDecorators.SPRING_LAVA);
        biomeGenerationSettings_builder.addFeature(Decoration.LOCAL_MODIFICATIONS, this.biomeDecorators.BASALT_PILLAR);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_OPEN);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_FIRE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_SOUL_FIRE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE_EXTRA);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_CRIMSON_ROOTS);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_MAGMA);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_CLOSED);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_SOUL_SAND);

        // data.worldgen.BiomeDefaultFeatures.addNetherDefaultOres
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GRAVEL_NETHER);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_BLACKSTONE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GOLD_NETHER);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_QUARTZ_NETHER);

        // data.worldgen.BiomeDefaultFeatures.addNetherDefaultOres -> data.worldgen.BiomeDefaultFeatures.addAncientDebris
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_ANCIENT_DEBRIS_LARGE);
        biomeGenerationSettings_builder.addFeature(Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_ANCIENT_DEBRIS_SMALL);

        return biomeGenerationSettings_builder.build();
    }
}
