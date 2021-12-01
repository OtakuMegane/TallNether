package com.minefit.xerxestireiron.tallnether.v1_18_R1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class NetherBiomes {
    private WorldInfo worldInfo;
    public final Map<String, Biome> biomes = new HashMap<>();

    public NetherBiomes(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
        collectBiomes();
    }

    private void collectBiomes() {
        Set<Biome> biomeList = this.worldInfo.nmsWorld.getChunkSource().getGenerator().getBiomeSource().possibleBiomes();
        Iterator<Biome> biomeIterator = biomeList.iterator();

        while (biomeIterator.hasNext()) {
            Biome biome = biomeIterator.next();
            ResourceLocation biomeKey = ((CraftServer) Bukkit.getServer()).getServer().registryAccess()
                    .ownedRegistryOrThrow(Registry.BIOME_REGISTRY).getKey(biome);

            if (biomeKey == Biomes.BASALT_DELTAS.location()) {
                this.biomes.put("basalt_deltas", biome);
            } else if (biomeKey == Biomes.CRIMSON_FOREST.location()) {
                this.biomes.put("crimson_forest", biome);
            } else if (biomeKey == Biomes.WARPED_FOREST.location()) {
                this.biomes.put("warped_forest", biome);
            } else if (biomeKey == Biomes.NETHER_WASTES.location()) {
                this.biomes.put("nether_wastes", biome);
            } else if (biomeKey == Biomes.SOUL_SAND_VALLEY.location()) {
                this.biomes.put("soul_sand_valley", biome);
            }
        }
    }
}
// CONVERTED