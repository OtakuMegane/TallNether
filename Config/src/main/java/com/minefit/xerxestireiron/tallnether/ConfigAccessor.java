package com.minefit.xerxestireiron.tallnether;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigAccessor {
    private static Map<String, ConfigValues> worldConfigs = new HashMap<>(); // Pre 1.16
    private static ConfigValues vanilla; // Pre 1.16
    private static Map<String, WorldConfig> newWorldConfigs = new HashMap<>();
    private static ConfigurationSection mainConfig;

    public ConfigAccessor() {
    }

    public ConfigAccessor(ConfigurationSection mainConfig) {
        ConfigAccessor.mainConfig = mainConfig;
    }

    public WorldConfig newWorldConfig(String worldName, Map<String, Boolean> paperConfig, boolean vanilla) {
        ConfigurationSection worldSettings = ConfigAccessor.mainConfig.getConfigurationSection("worlds." + worldName);
        WorldConfig worldConfig = new WorldConfig(worldSettings, paperConfig);
        ConfigAccessor.newWorldConfigs.put(worldName, worldConfig);
        return worldConfig;
    }

    public WorldConfig getWorldConfig(String worldName) {
        WorldConfig worldConfig = ConfigAccessor.newWorldConfigs.get(worldName);
        return worldConfig;
    }

    public String biomeClassToConfig(String biomeClass) {
        if (biomeClass.equals("BiomeHell")) {
            return "nether-wastes";
        } else if (biomeClass.equals("BiomeCrimsonForest")) {
            return "crimson-forest";
        } else if (biomeClass.equals("BiomeWarpedForest")) {
            return "warped-forest";
        } else if (biomeClass.equals("BiomeBasaltDeltas")) {
            return "basalt-deltas";
        } else if (biomeClass.equals("BiomeSoulSandValley")) {
            return "soul-sand-valley";
        } else {
            return "";
        }
    }

    // Pre 1.16
    public void addConfig(String worldName, ConfigValues config) {
        if (worldName == null) {
            ConfigAccessor.vanilla = config;
        } else {
            ConfigAccessor.worldConfigs.put(worldName, config);
        }
    }

    // Pre 1.16
    public ConfigValues getConfig(String worldName) {
        if (ConfigAccessor.worldConfigs.containsKey(worldName)) {
            return ConfigAccessor.worldConfigs.get(worldName);
        }

        return ConfigAccessor.vanilla;

    }
}
