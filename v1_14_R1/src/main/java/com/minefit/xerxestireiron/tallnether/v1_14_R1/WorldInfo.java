package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;

import com.minefit.xerxestireiron.tallnether.ConfigValues;

import net.minecraft.server.v1_14_R1.ChunkGenerator;
import net.minecraft.server.v1_14_R1.ChunkProviderServer;
import net.minecraft.server.v1_14_R1.WorldProvider;
import net.minecraft.server.v1_14_R1.WorldServer;

public class WorldInfo {
    public final World world;
    public final WorldServer nmsWorld;
    public final String worldName;
    public final String originalGenName;
    public final WorldProvider worldProvider;
    public final ChunkGenerator<?> originalGenerator;
    public final ChunkProviderServer chunkServer;
    public final ConfigValues configValues;

    public WorldInfo(World world, ConfigurationSection config) {
        this.world = world;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.worldName = this.world.getName();
        PaperSpigot paperSpigot = new PaperSpigot(this.worldName, false);
        this.configValues = new ConfigValues(this.worldName, config, paperSpigot.getSettingsMap());
        this.chunkServer = (ChunkProviderServer) this.nmsWorld.getChunkProvider();
        this.originalGenerator = this.chunkServer.getChunkGenerator();
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        this.worldProvider = this.nmsWorld.worldProvider;
    }

}
