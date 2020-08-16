package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;

import net.minecraft.server.v1_16_R2.ChunkGenerator;
import net.minecraft.server.v1_16_R2.ChunkProviderServer;
import net.minecraft.server.v1_16_R2.DimensionManager;
import net.minecraft.server.v1_16_R2.WorldChunkManager;
import net.minecraft.server.v1_16_R2.WorldServer;

public class WorldInfo {
    public final World world;
    public final WorldServer nmsWorld;
    public final String worldName;
    public final String originalGenName;
    public final ChunkGenerator originalGenerator;
    public final ChunkProviderServer chunkServer;
    public final DimensionManager dimensionManager;
    public final WorldChunkManager originalChunkManager;
    public final NetherBiomes netherBiomes;
    public final BasaltDeltasModifier basaltDeltasModifier;
    public final CrimsonForestModifier crimsonForestModifier;
    public final WarpedForestModifier warpedForestModifier;
    public final NetherWastesModifier netherWastesModifier;
    public final SoulSandValleyModifier soulSandValleyModifier;

    public WorldInfo(World world) {
        this.world = world;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.worldName = this.world.getName();
        PaperSpigot paperSpigot = new PaperSpigot(this.worldName, false);
        this.chunkServer = (ChunkProviderServer) this.nmsWorld.getChunkProvider();
        this.originalGenerator = this.chunkServer.getChunkGenerator();
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        this.dimensionManager = this.nmsWorld.getDimensionManager();
        this.originalChunkManager = this.originalGenerator.getWorldChunkManager();
        this.netherBiomes = new NetherBiomes(this);
        this.basaltDeltasModifier = new BasaltDeltasModifier(this, this.netherBiomes.biomes.get("basalt_deltas"));
        this.crimsonForestModifier = new CrimsonForestModifier(this, this.netherBiomes.biomes.get("crimson_forest"));
        this.warpedForestModifier = new WarpedForestModifier(this, this.netherBiomes.biomes.get("warped_forest"));
        this.netherWastesModifier = new NetherWastesModifier(this, this.netherBiomes.biomes.get("nether_wastes"));
        this.soulSandValleyModifier = new SoulSandValleyModifier(this, this.netherBiomes.biomes.get("soul_sand_valley"));
    }
}
