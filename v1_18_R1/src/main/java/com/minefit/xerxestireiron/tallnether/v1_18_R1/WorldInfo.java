package com.minefit.xerxestireiron.tallnether.v1_18_R1;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;

import com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers.BasaltDeltasModifier;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers.CrimsonForestModifier;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers.NetherWastesModifier;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers.SoulSandValleyModifier;
import com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers.WarpedForestModifier;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;


public class WorldInfo {
    public final World world;
    public final ServerLevel nmsWorld;
    public final String worldName;
    public final String originalGenName;
    public final ChunkGenerator originalGenerator;
    public final ServerChunkCache serverChunkCache;
    public final DimensionType dimensionType;
    public final BiomeSource originalBiomeSource;
    public boolean modified = false;
    public NetherBiomes netherBiomes;
    public BasaltDeltasModifier basaltDeltasModifier;
    public CrimsonForestModifier crimsonForestModifier;
    public WarpedForestModifier warpedForestModifier;
    public NetherWastesModifier netherWastesModifier;
    public SoulSandValleyModifier soulSandValleyModifier;

    public WorldInfo(World world) {
        this.world = world;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.worldName = this.world.getName();
        PaperSpigot paperSpigot = new PaperSpigot(this.worldName, false);
        this.serverChunkCache = this.nmsWorld.getChunkSource();
        this.originalGenerator = this.serverChunkCache.getGenerator();
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        this.dimensionType = this.nmsWorld.dimensionType();
        this.originalBiomeSource = this.originalGenerator.getBiomeSource();
    }

    public void collectBiomeData() {
        this.netherBiomes = new NetherBiomes(this);
        this.basaltDeltasModifier = new BasaltDeltasModifier(this, this.netherBiomes.biomes.get("basalt_deltas"));
        this.crimsonForestModifier = new CrimsonForestModifier(this, this.netherBiomes.biomes.get("crimson_forest"));
        this.warpedForestModifier = new WarpedForestModifier(this, this.netherBiomes.biomes.get("warped_forest"));
        this.netherWastesModifier = new NetherWastesModifier(this, this.netherBiomes.biomes.get("nether_wastes"));
        this.soulSandValleyModifier = new SoulSandValleyModifier(this,
                this.netherBiomes.biomes.get("soul_sand_valley"));
    }
}
