package com.minefit.xerxestireiron.tallnether;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class WorldConfig {

    private final ConfigurationSection worldSettings;
    private final Map<String, Boolean> paperConfig;
    private WorldValues worldValues;
    private Map<String, BiomeValues> biomeValues = new HashMap<>();
    public final boolean isVanilla;

    public WorldConfig(ConfigurationSection worldSettings, Map<String, Boolean> paperConfig) {
        this.paperConfig = paperConfig;
        this.worldSettings = worldSettings;

        if (worldSettings != null && worldSettings.getBoolean("enabled")) {
            this.isVanilla = false;
            this.worldValues = new WorldValues(this.worldSettings, paperConfig);
            this.biomeValues.put("nether-wastes", new BiomeNetherWastes(worldSettings, paperConfig));
            this.biomeValues.put("crimson-forest", new BiomeCrimsonForest(worldSettings, paperConfig));
            this.biomeValues.put("warped-forest", new BiomeWarpedForest(worldSettings, paperConfig));
            this.biomeValues.put("basalt-deltas", new BiomeBasaltDeltas(worldSettings, paperConfig));
            this.biomeValues.put("soul-sand-valley", new BiomeSoulSandValley(worldSettings, paperConfig));
        } else {
            this.worldValues = new WorldValues(this.paperConfig); // Vanilla
            this.isVanilla = true;
            this.biomeValues.put("nether-wastes", new BiomeNetherWastes(paperConfig));
            this.biomeValues.put("crimson-forest", new BiomeCrimsonForest(paperConfig));
            this.biomeValues.put("warped-forest", new BiomeWarpedForest(paperConfig));
            this.biomeValues.put("basalt-deltas", new BiomeBasaltDeltas(paperConfig));
            this.biomeValues.put("soul-sand-valley", new BiomeSoulSandValley(paperConfig));
        }
    };

    public WorldValues getWorldValues() {
        return this.worldValues;
    }

    public BiomeValues getBiomeValues(String biome) {
        return this.biomeValues.get(biome);
    }
}
