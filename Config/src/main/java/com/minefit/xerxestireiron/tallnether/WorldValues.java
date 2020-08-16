package com.minefit.xerxestireiron.tallnether;

import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class WorldValues {

    private final Map<String, Boolean> paperConfig;
    public final boolean vanillaValues;
    public final boolean generateFarLands;
    public final int farLandsLowX;
    public final int farLandsLowZ;
    public final int farLandsHighX;
    public final int farLandsHighZ;
    public final int lavaSeaLevel;
    public final boolean flatBedrockCeiling;
    public final boolean flatBedrockFloor;

    // Vanilla
    public WorldValues(Map<String, Boolean> paperConfig) {
        this.paperConfig = paperConfig;
        this.vanillaValues = true;
        this.generateFarLands = false;
        this.farLandsLowX = -12550824;
        this.farLandsLowZ = -12550824;
        this.farLandsHighX = 12550824;
        this.farLandsHighZ = 12550824;
        this.lavaSeaLevel = 32;

        if (this.paperConfig == null) {
            this.flatBedrockCeiling = false;
            this.flatBedrockFloor = false;
        } else {
            this.flatBedrockCeiling = this.paperConfig.get("generateFlatBedrock");
            this.flatBedrockFloor = this.paperConfig.get("generateFlatBedrock");
        }
    }

    public WorldValues(ConfigurationSection worldSettings, Map<String, Boolean> paperConfig) {
        this.paperConfig = paperConfig;
        this.vanillaValues = false;
        this.generateFarLands = worldSettings.getBoolean("farlands", false);
        this.farLandsLowX = worldSettings.getInt("lowX", -12550824);
        this.farLandsLowZ = worldSettings.getInt("lowZ", -12550824);
        this.farLandsHighX = worldSettings.getInt("highX", 12550824);
        this.farLandsHighZ = worldSettings.getInt("highZ", 12550824);
        this.lavaSeaLevel = setDecoration(worldSettings, "lava-sea-level", 48, true);
        this.flatBedrockCeiling = worldSettings.getBoolean("flat-bedrock-ceiling",
                this.paperConfig.get("generateFlatBedrock"));
        this.flatBedrockFloor = worldSettings.getBoolean("flat-bedrock-floor",
                this.paperConfig.get("generateFlatBedrock"));
    }

    private int setDecoration(ConfigurationSection worldConfig, String setting, int defaultValue, boolean safetyMax) {
        int value = worldConfig.getInt(setting, defaultValue);

        if (value < 0) {
            value = 0;
        }

        if (safetyMax && value > 256) {
            value = 256;
        }

        return value;
    }
}
