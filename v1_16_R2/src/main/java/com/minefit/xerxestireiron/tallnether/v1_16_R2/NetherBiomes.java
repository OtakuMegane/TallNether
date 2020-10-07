package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;

import net.minecraft.server.v1_16_R2.BiomeBase;
import net.minecraft.server.v1_16_R2.Biomes;
import net.minecraft.server.v1_16_R2.DedicatedServer;
import net.minecraft.server.v1_16_R2.IRegistry;
import net.minecraft.server.v1_16_R2.IRegistryCustom;
import net.minecraft.server.v1_16_R2.MinecraftKey;
import net.minecraft.server.v1_16_R2.MinecraftServer;

public class NetherBiomes {
    private WorldInfo worldInfo;
    public final Map<String, BiomeBase> biomes = new HashMap<>();

    public NetherBiomes(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
        collectBiomes();
    }

    private void collectBiomes() {
        List<BiomeBase> biomeList = this.worldInfo.chunkServer.getChunkGenerator().getWorldChunkManager().b();
        Iterator<BiomeBase> biomeIterator = biomeList.iterator();

        while (biomeIterator.hasNext()) {
            BiomeBase biomeBase = biomeIterator.next();
            MinecraftKey biomeKey = getBiomeBaseKey(biomeBase);

            if(biomeKey == Biomes.BASALT_DELTAS.a()) {
                this.biomes.put("basalt_deltas", biomeBase);
            } else if(biomeKey == Biomes.CRIMSON_FOREST.a()) {
                this.biomes.put("crimson_forest", biomeBase);
            } else if(biomeKey == Biomes.WARPED_FOREST.a()) {
                this.biomes.put("warped_forest", biomeBase);
            } else if(biomeKey == Biomes.NETHER_WASTES.a()) {
                this.biomes.put("nether_wastes", biomeBase);
            } else if(biomeKey == Biomes.SOUL_SAND_VALLEY.a()) {
                this.biomes.put("soul_sand_valley", biomeBase);
            }
        }
    }

    // Hopefully we can simplify this method or even get rid of it in 1.17+
    public MinecraftKey getBiomeBaseKey(BiomeBase biomeBase) {
        DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();
        IRegistryCustom customRegistry = null;

        try {
            customRegistry = dedicatedServer.aX(); // 1.16.2
        } catch (NoSuchMethodError e) {
            try {
                Method getRegistryMethod;
                getRegistryMethod = MinecraftServer.class.getDeclaredMethod("getCustomRegistry"); // 1.16.3
                customRegistry = (IRegistryCustom) getRegistryMethod.invoke(dedicatedServer);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }

        return customRegistry.b(IRegistry.ay).getKey(biomeBase);
    }
}
