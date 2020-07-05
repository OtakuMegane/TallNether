package com.minefit.xerxestireiron.tallnether;

import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public abstract class BiomeValues {

    protected Map<String, Boolean> paperConfig;
    protected ConfigurationSection biomeConfig;
    public Map<String, Integer> values;

    public BiomeValues(Map<String, Boolean> paperConfig) {
        this.paperConfig = paperConfig;
    }

    public BiomeValues(ConfigurationSection biomeConfig, Map<String, Boolean> paperConfig) {
        this.paperConfig = paperConfig;
        this.biomeConfig = biomeConfig;
    }

    protected int setDecoration(String setting, int defaultValue, boolean safetyMax) {
        int value;

        if (this.biomeConfig == null) {
            value = defaultValue;
        } else {
            value = this.biomeConfig.getInt(setting, defaultValue);
        }

        if (value < 0) {
            value = 0;
        }

        if (safetyMax && value > 256) {
            value = 256;
        }

        return value;
    }
}
