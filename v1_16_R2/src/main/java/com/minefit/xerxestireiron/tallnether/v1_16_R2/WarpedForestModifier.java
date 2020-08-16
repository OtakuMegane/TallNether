package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import net.minecraft.server.v1_16_R2.BiomeBase;
import net.minecraft.server.v1_16_R2.BiomeSettingsGeneration;
import net.minecraft.server.v1_16_R2.StructureFeatures;
import net.minecraft.server.v1_16_R2.WorldGenStage;
import net.minecraft.server.v1_16_R2.WorldGenSurfaceComposites;

public class WarpedForestModifier extends BiomeModifier {
    private BiomeBase biomeBase;
    private BiomeDecorators biomeDecorators;
    private BiomeSettingsGeneration originalBiomeSettings;
    private BiomeSettingsGeneration modifiedBiomeSettings;

    public WarpedForestModifier(WorldInfo worldInfo, BiomeBase biomeBase) {
        this.biomeBase = biomeBase;
        this.originalBiomeSettings = biomeBase.e();
        this.biomeDecorators = new BiomeDecorators(worldInfo, "warped-forest");
        this.modifiedBiomeSettings = createModifiedSettings();
    }

    public boolean modify() {
        return injectSettings(this.biomeBase, this.modifiedBiomeSettings);
    }

    @SuppressWarnings("unchecked")
    private BiomeSettingsGeneration createModifiedSettings () {
        BiomeSettingsGeneration.a biomeSettingsGeneration_a = new BiomeSettingsGeneration.a();

        biomeSettingsGeneration_a.a(WorldGenSurfaceComposites.v);
        biomeSettingsGeneration_a.a(StructureFeatures.o);
        biomeSettingsGeneration_a.a(StructureFeatures.s);
        biomeSettingsGeneration_a.a(StructureFeatures.E);
        biomeSettingsGeneration_a.a(WorldGenStage.Features.AIR, this.biomeDecorators.CAVES_HELL); // WorldGenCarvers.f
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.SPRING_LAVA); // SPRING_LAVA

        // BiomeSettings.Z(biomesettingsgeneration_a)
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.BROWN_MUSHROOM_NORMAL); // BROWN_MUSHROOM_NORMAL
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.RED_MUSHROOM_NORMAL); // RED_MUSHROOM_NORMAL

        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_OPEN); // SPRING_OPEN
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_FIRE); // PATCH_FIRE
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_SOUL_FIRE);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE_EXTRA); // GLOWSTONE_EXTRA
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE); // GLOWSTONE
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_MAGMA); // ORE_MAGMA
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_CLOSED); // SPRING_CLOSED
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.WARPED_FUNGI); // WARPED_FUNGI
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.WARPED_FOREST_VEGETATION); // WARPED_FOREST_VEGETATION
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.NETHER_SPROUTS); // NETHER_SPROUTS
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.TWISTING_VINES); // TWISTING_VINES

        // BiomeSettings.ao(biomesettingsgeneration_a)
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GRAVEL_NETHER); // ORE_GRAVEL_NETHER
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_BLACKSTONE); // ORE_BLACKSTONE
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GOLD_NETHER); // ORE_GOLD_NETHER
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_QUARTZ_NETHER); // ORE_QUARTZ_NETHER

        // BiomeSettings.ap(biomesettingsgeneration_a)
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_DEBRIS_LARGE); // ORE_DEBRIS_LARGE
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_DEBRIS_SMALL); // ORE_DEBRIS_SMALL

        return biomeSettingsGeneration_a.a();
    }
}
