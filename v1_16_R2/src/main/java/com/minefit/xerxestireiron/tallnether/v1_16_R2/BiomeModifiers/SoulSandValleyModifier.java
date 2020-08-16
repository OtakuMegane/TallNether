package com.minefit.xerxestireiron.tallnether.v1_16_R2.BiomeModifiers;

import com.minefit.xerxestireiron.tallnether.v1_16_R2.BiomeDecorators;
import com.minefit.xerxestireiron.tallnether.v1_16_R2.SurfaceComposites;
import com.minefit.xerxestireiron.tallnether.v1_16_R2.WorldInfo;

import net.minecraft.server.v1_16_R2.BiomeBase;
import net.minecraft.server.v1_16_R2.BiomeDecoratorGroups;
import net.minecraft.server.v1_16_R2.BiomeSettingsGeneration;
import net.minecraft.server.v1_16_R2.StructureFeatures;
import net.minecraft.server.v1_16_R2.WorldGenStage;

public class SoulSandValleyModifier extends BiomeModifier {
    private BiomeBase biomeBase;
    private BiomeDecorators biomeDecorators;
    private BiomeSettingsGeneration originalBiomeSettings;
    private BiomeSettingsGeneration modifiedBiomeSettings;
    private SurfaceComposites surfaceComposites;

    public SoulSandValleyModifier(WorldInfo worldInfo, BiomeBase biomeBase) {
        this.biomeBase = biomeBase;
        this.originalBiomeSettings = biomeBase.e();
        this.biomeDecorators = new BiomeDecorators(worldInfo, "soul-sand-valley");
        this.surfaceComposites = new SurfaceComposites();
        this.modifiedBiomeSettings = createModifiedSettings();
    }

    public boolean modify() {
        return injectSettings(this.biomeBase, this.modifiedBiomeSettings);
    }

    @SuppressWarnings("unchecked")
    private BiomeSettingsGeneration createModifiedSettings () {
        BiomeSettingsGeneration.a biomeSettingsGeneration_a = new BiomeSettingsGeneration.a();

        biomeSettingsGeneration_a.a(this.surfaceComposites.SOUL_SAND_VALLEY);
        biomeSettingsGeneration_a.a(StructureFeatures.o);
        biomeSettingsGeneration_a.a(StructureFeatures.p);
        biomeSettingsGeneration_a.a(StructureFeatures.E);
        biomeSettingsGeneration_a.a(StructureFeatures.s);
        biomeSettingsGeneration_a.a(WorldGenStage.Features.AIR, this.biomeDecorators.CAVES_HELL); // WorldGenCarvers.f
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.VEGETAL_DECORATION, this.biomeDecorators.SPRING_LAVA);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.LOCAL_MODIFICATIONS, BiomeDecoratorGroups.BASALT_PILLAR);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_OPEN);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE_EXTRA);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.GLOWSTONE);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.TALLNETHER_GLOWSTONE);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, BiomeDecoratorGroups.PATCH_CRIMSON_ROOTS);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_FIRE);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.PATCH_SOUL_FIRE);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_MAGMA);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.SPRING_CLOSED);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_SOUL_SAND);

        // BiomeSettings.ao(biomesettingsgeneration_a)
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GRAVEL_NETHER);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_BLACKSTONE);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_GOLD_NETHER);
        biomeSettingsGeneration_a.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, this.biomeDecorators.ORE_QUARTZ_NETHER);

        return biomeSettingsGeneration_a.a();
    }
}
