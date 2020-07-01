package com.minefit.xerxestireiron.tallnether;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigAccessor {
    private static Map<String, ConfigValues> worldConfigs = new HashMap<>();
    private static Map<String, WorldConfig> newWorldConfigs = new HashMap<>();
    private static WorldConfig vanillaConfig;
    private static ConfigValues vanilla;
    private static ConfigurationSection mainConfig;

    public ConfigAccessor() {
    }

    public void setMainConfig(ConfigurationSection mainConfig) {
        ConfigAccessor.mainConfig = mainConfig;
    }

    public WorldConfig setVanillaConfig(Map<String, Boolean> paperConfig) {
        ConfigAccessor.vanillaConfig = new WorldConfig(null, paperConfig);
        return ConfigAccessor.vanillaConfig;
    }

    public WorldConfig newWorldConfig(String worldName, Map<String, Boolean> paperConfig, boolean store) {
        ConfigurationSection worldSettings = ConfigAccessor.mainConfig.getConfigurationSection("worlds." + worldName);
        WorldConfig worldConfig = new WorldConfig(worldSettings, paperConfig);

        if(store) {
            ConfigAccessor.newWorldConfigs.put(worldName, worldConfig);
        }

        return worldConfig;
    }

    public WorldConfig getWorldConfig(String worldName) {
        if (ConfigAccessor.worldConfigs.containsKey(worldName)) {
            return ConfigAccessor.newWorldConfigs.get(worldName);
        }

        return ConfigAccessor.vanillaConfig;

    }



    public void addConfig(String worldName, ConfigValues config) {
        if (worldName == null) {
            ConfigAccessor.vanilla = config;
        } else {
            ConfigAccessor.worldConfigs.put(worldName, config);
        }
    }

    public ConfigValues getConfig(String worldName) {
        if (ConfigAccessor.worldConfigs.containsKey(worldName)) {
            return ConfigAccessor.worldConfigs.get(worldName);
        }

        return ConfigAccessor.vanilla;

    }

    public ConfigValues getConfig(String worldName, String biome) {
        if (ConfigAccessor.worldConfigs.containsKey(worldName)) {
            return ConfigAccessor.worldConfigs.get(worldName);
        }

        return ConfigAccessor.vanilla;

    }
}
