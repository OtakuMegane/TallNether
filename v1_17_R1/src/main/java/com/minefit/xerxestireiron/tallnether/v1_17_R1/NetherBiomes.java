package com.minefit.xerxestireiron.tallnether.v1_17_R1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;

import com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldInfo;

import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;


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
            MinecraftKey biomeKey = ((CraftServer) Bukkit.getServer()).getServer().getCustomRegistry().b(IRegistry.aO).getKey(biomeBase);
            //MinecraftKey biomeKey = getBiomeBaseKey(biomeBase);

            if (biomeKey == Biomes.aA.a()) {
                this.biomes.put("basalt_deltas", biomeBase);
            } else if (biomeKey == Biomes.ay.a()) {
                this.biomes.put("crimson_forest", biomeBase);
            } else if (biomeKey == Biomes.az.a()) {
                this.biomes.put("warped_forest", biomeBase);
            } else if (biomeKey == Biomes.i.a()) {
                this.biomes.put("nether_wastes", biomeBase);
            } else if (biomeKey == Biomes.ax.a()) {
                this.biomes.put("soul_sand_valley", biomeBase);
            }
        }
    }

    // Hopefully we can simplify this method or even get rid of it in 1.17+
    public MinecraftKey getBiomeBaseKey(BiomeBase biomeBase) {
        DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();
        IRegistryCustom customRegistry = null;

        try {
            Method getRegistryMethod;
            getRegistryMethod = MinecraftServer.class.getDeclaredMethod("getCustomRegistry");
            customRegistry = (IRegistryCustom) getRegistryMethod.invoke(dedicatedServer);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e1) {
            e1.printStackTrace();
        }

        return customRegistry.b(IRegistry.aO).getKey(biomeBase);
    }
}
