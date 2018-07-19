package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import net.minecraft.server.v1_13_R1.BiomeBase;
import net.minecraft.server.v1_13_R1.BlockPredicate;
import net.minecraft.server.v1_13_R1.Blocks;
import net.minecraft.server.v1_13_R1.EntityTypes;
import net.minecraft.server.v1_13_R1.EnumCreatureType;
import net.minecraft.server.v1_13_R1.FluidTypes;
import net.minecraft.server.v1_13_R1.StructureGenerator;
import net.minecraft.server.v1_13_R1.WorldGenCarver;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorChanceConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureChanceDecoratorRangeConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfigurationChance;
import net.minecraft.server.v1_13_R1.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureFlowingConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureMushroomConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenNetherConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenStage;
import net.minecraft.server.v1_13_R1.WorldGenSurfaceComposite;
import net.minecraft.server.v1_13_R1.WorldGenerator;

public final class TallNether_BiomeHell extends BiomeBase {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected TallNether_BiomeHell() {
        super((new BiomeBase.a()).a(new WorldGenSurfaceComposite(TallNether_BiomeHell.aF, TallNether_BiomeHell.at)).a(BiomeBase.Precipitation.NONE).a(BiomeBase.Geography.NETHER).a(0.1F).b(0.2F).c(2.0F).d(0.0F).a(4159204).b(329011).a((String) null));
        this.a((StructureGenerator) WorldGenerator.p, (WorldGenFeatureConfiguration) (new WorldGenNetherConfiguration())); // DONE?
        this.a(WorldGenStage.Features.AIR, a((WorldGenCarver) TallNether_BiomeHell.c, (WorldGenFeatureConfiguration) (new WorldGenFeatureConfigurationChance(0.2F)))); //DONE
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.at, new WorldGenFeatureFlowingConfiguration(FluidTypes.e), TallNether_BiomeHell.w, new WorldGenFeatureChanceDecoratorCountConfiguration(20, 8, 16, 256)));// DONE
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), TallNether_BiomeHell.q, new WorldGenDecoratorChanceConfiguration(4)));//DONE
        this.a(WorldGenStage.Decoration.VEGETAL_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), TallNether_BiomeHell.q, new WorldGenDecoratorChanceConfiguration(8)));//DONE
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.p, new WorldGenNetherConfiguration(), TallNether_BiomeHell.n, WorldGenFeatureDecoratorConfiguration.e));//DONE?
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(false), TallNether_BiomeHell.u, new WorldGenFeatureChanceDecoratorCountConfiguration(8, 4, 8, 128))); // DONE
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.S, WorldGenFeatureConfiguration.e, TallNether_BiomeHell.H, new WorldGenDecoratorFrequencyConfiguration(10)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.W, WorldGenFeatureConfiguration.e, TallNether_BiomeHell.P, new WorldGenDecoratorFrequencyConfiguration(10)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.W, WorldGenFeatureConfiguration.e, TallNether_BiomeHell.u, new WorldGenFeatureChanceDecoratorCountConfiguration(10, 0, 0, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.BROWN_MUSHROOM), TallNether_BiomeHell.y, new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.ah, new WorldGenFeatureMushroomConfiguration(Blocks.RED_MUSHROOM), TallNether_BiomeHell.y, new WorldGenFeatureChanceDecoratorRangeConfiguration(0.5F, 0, 0, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(BlockPredicate.a(Blocks.NETHERRACK), Blocks.NETHER_QUARTZ_ORE.getBlockData(), 14), TallNether_BiomeHell.u, new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.an, new WorldGenFeatureOreConfiguration(BlockPredicate.a(Blocks.NETHERRACK), Blocks.MAGMA_BLOCK.getBlockData(), 49), TallNether_BiomeHell.I, new WorldGenDecoratorFrequencyConfiguration(4)));
        this.a(WorldGenStage.Decoration.UNDERGROUND_DECORATION, a(WorldGenerator.ak, new WorldGenFeatureHellFlowingLavaConfiguration(true), TallNether_BiomeHell.u, new WorldGenFeatureChanceDecoratorCountConfiguration(16, 10, 20, 128)));//DONE
    }
}
