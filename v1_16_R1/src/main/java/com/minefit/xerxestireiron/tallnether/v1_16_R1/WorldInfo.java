package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;

import net.minecraft.server.v1_16_R1.ChunkGenerator;
import net.minecraft.server.v1_16_R1.ChunkProviderServer;
import net.minecraft.server.v1_16_R1.DimensionManager;
import net.minecraft.server.v1_16_R1.WorldChunkManager;
import net.minecraft.server.v1_16_R1.WorldServer;

public class WorldInfo {
    public final World world;
    public final WorldServer nmsWorld;
    public final String worldName;
    public final String originalGenName;
    public final ChunkGenerator originalGenerator;
    public final ChunkProviderServer chunkServer;
    public final DimensionManager dimensionManager;
    public final WorldChunkManager originalChunkManager;


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
    }
}
