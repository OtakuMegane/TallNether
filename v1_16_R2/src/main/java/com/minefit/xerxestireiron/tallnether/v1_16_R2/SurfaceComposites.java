package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import com.minefit.xerxestireiron.tallnether.v1_16_R2.WorldGenSurfaces.TallNether_WorldGenSurfaceBasaltDeltas;
import com.minefit.xerxestireiron.tallnether.v1_16_R2.WorldGenSurfaces.TallNether_WorldGenSurfaceNether;
import com.minefit.xerxestireiron.tallnether.v1_16_R2.WorldGenSurfaces.TallNether_WorldGenSurfaceNetherForest;
import com.minefit.xerxestireiron.tallnether.v1_16_R2.WorldGenSurfaces.TallNether_WorldGenSurfaceSoulSandValley;

import net.minecraft.server.v1_16_R2.WorldGenSurface;
import net.minecraft.server.v1_16_R2.WorldGenSurfaceComposite;
import net.minecraft.server.v1_16_R2.WorldGenSurfaceConfigurationBase;

public class SurfaceComposites {
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> BASALT_DELTAS;
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> CRIMSON_FOREST;
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> NETHER_WASTES;
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> SOUL_SAND_VALLEY;
    public final WorldGenSurfaceComposite<WorldGenSurfaceConfigurationBase> WARPED_FOREST;

    public SurfaceComposites () {
        this.BASALT_DELTAS = new TallNether_WorldGenSurfaceBasaltDeltas(WorldGenSurfaceConfigurationBase.a).a(WorldGenSurface.u);
        this.CRIMSON_FOREST = new TallNether_WorldGenSurfaceNetherForest(WorldGenSurfaceConfigurationBase.a).a(WorldGenSurface.s);
        this.NETHER_WASTES = new TallNether_WorldGenSurfaceNether(WorldGenSurfaceConfigurationBase.a).a(WorldGenSurface.p);
        this.SOUL_SAND_VALLEY = new TallNether_WorldGenSurfaceSoulSandValley(WorldGenSurfaceConfigurationBase.a).a(WorldGenSurface.q);
        this.WARPED_FOREST = new TallNether_WorldGenSurfaceNetherForest(WorldGenSurfaceConfigurationBase.a).a(WorldGenSurface.t);
    }
}
