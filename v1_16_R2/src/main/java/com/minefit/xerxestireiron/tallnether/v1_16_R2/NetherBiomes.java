package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.server.v1_16_R2.BiomeBase;
import net.minecraft.server.v1_16_R2.IRegistry;
import net.minecraft.server.v1_16_R2.MinecraftKey;
import net.minecraft.server.v1_16_R2.WorldGenFeatureCompositeConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenFeatureConfigured;

public class NetherBiomes {
    private WorldInfo worldInfo;
    public final Map<String, BiomeBase> biomes = new HashMap<>();

    public NetherBiomes(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
        collectBiomes();
    }

    private void collectBiomes() {
        List<BiomeBase> biomeList = this.worldInfo.originalChunkManager.b();
        Iterator<BiomeBase> biomeIterator = biomeList.iterator();

        while (biomeIterator.hasNext()) {
            BiomeBase biomeBase = biomeIterator.next();
            int forest_count = 0;
            int random_count = 0;
            boolean basalt_found = false;

            for (List<Supplier<WorldGenFeatureConfigured<?, ?>>> wut : biomeBase.e().c()) { // Immutable
                for (Supplier<WorldGenFeatureConfigured<?, ?>> wut2 : wut) {
                    WorldGenFeatureCompositeConfiguration wut3 = (WorldGenFeatureCompositeConfiguration) wut2.get().c();
                    MinecraftKey minecraft_key = IRegistry.FEATURE.getKey(wut3.b.get().b());

                    if (minecraft_key.toString().equals("minecraft:basalt_columns")) {
                        basalt_found = true;
                    }

                    if (minecraft_key.toString().equals("minecraft:nether_forest_vegetation")) {
                        ++forest_count;
                    }

                    if (minecraft_key.toString().equals("minecraft:random_patch")) {
                        ++random_count;
                    }
                }
            }

            if (basalt_found) {
                this.biomes.put("basalt_deltas", biomeBase);
            } else if (forest_count > 0) {
                if (forest_count == 1) {
                    this.biomes.put("crimson_forest", biomeBase);
                }

                if (forest_count == 2) {
                    this.biomes.put("warped_forest", biomeBase);
                }
            } else if (random_count == 2) {
                this.biomes.put("nether_wastes", biomeBase);
            } else if (random_count == 3) {
                this.biomes.put("soul_sand_valley", biomeBase);
            }
        }
    }
}
