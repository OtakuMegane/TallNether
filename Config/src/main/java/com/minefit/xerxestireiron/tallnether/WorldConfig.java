package com.minefit.xerxestireiron.tallnether;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class WorldConfig {

    private final ConfigurationSection worldSettings;
    private final Map<String, Boolean> paperConfig;
    private WorldValues worldValues;
    private Map<String, BiomeValues> biomeValues = new HashMap<>();

    public WorldConfig(ConfigurationSection worldSettings, Map<String, Boolean> paperConfig) {
        this.paperConfig = paperConfig;
        this.worldSettings = worldSettings;

        if(worldSettings == null) {
            this.worldValues = new WorldValues(this.paperConfig); // Vanilla
        } else {
            this.worldValues = new WorldValues(this.worldSettings, paperConfig);
        }

        // Initialize BiomeValues for know biomes
    };

    public WorldValues getWorldValues() {
        return this.worldValues;
    }

    public BiomeValues getBiomeValues(String biome) {
        return this.biomeValues.get(biome);
    }
}
