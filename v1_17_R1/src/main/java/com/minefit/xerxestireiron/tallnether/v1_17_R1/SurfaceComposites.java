package com.minefit.xerxestireiron.tallnether.v1_17_R1;

import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TWorldGenSurface;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldGenSurfaces.TallNether_WorldGenSurfaceBasaltDeltas;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldGenSurfaces.TallNether_WorldGenSurfaceNether;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldGenSurfaces.TallNether_WorldGenSurfaceNetherForest;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldGenSurfaces.TallNether_WorldGenSurfaceSoulSandValley;

import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceComposite;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceConfigurationBase;

public class SurfaceComposites {
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> BASALT_DELTAS;
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> CRIMSON_FOREST;
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> NETHER;
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> SOUL_SAND_VALLEY;
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> WARPED_FOREST;

    public SurfaceComposites () {
        this.BASALT_DELTAS = new TallNether_WorldGenSurfaceBasaltDeltas(WorldGenSurfaceConfigurationBase.a).a(TWorldGenSurface.CONFIG_BASALT_DELTAS);
        this.CRIMSON_FOREST = new TallNether_WorldGenSurfaceNetherForest(WorldGenSurfaceConfigurationBase.a).a(TWorldGenSurface.CONFIG_CRIMSON_FOREST);
        this.NETHER = new TallNether_WorldGenSurfaceNether(WorldGenSurfaceConfigurationBase.a).a(TWorldGenSurface.CONFIG_HELL);
        this.SOUL_SAND_VALLEY = new TallNether_WorldGenSurfaceSoulSandValley(WorldGenSurfaceConfigurationBase.a).a(TWorldGenSurface.CONFIG_SOUL_SAND_VALLEY);
        this.WARPED_FOREST = new TallNether_WorldGenSurfaceNetherForest(WorldGenSurfaceConfigurationBase.a).a(TWorldGenSurface.CONFIG_WARPED_FOREST);
    }
}
