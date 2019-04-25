package com.minefit.xerxestireiron.tallnether;

import java.util.HashMap;
import java.util.Map;

public class ConfigAccessor {
    private static Map<String, ConfigValues> worldConfigs = new HashMap<>();
    private static ConfigValues vanilla;

    public ConfigAccessor() {
    }

    public void addConfig(String worldName, ConfigValues config) {
        if(worldName == null) {
            ConfigAccessor.vanilla = config;
        }
        else {
            ConfigAccessor.worldConfigs.put(worldName, config);
        }
    }

    public ConfigValues getConfig(String worldName) {
        if (ConfigAccessor.worldConfigs.containsKey(worldName)) {
            return ConfigAccessor.worldConfigs.get(worldName);
        }

        return ConfigAccessor.vanilla;

    }
}
