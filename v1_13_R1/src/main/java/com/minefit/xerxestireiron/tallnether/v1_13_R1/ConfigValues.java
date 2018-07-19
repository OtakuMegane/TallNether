package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigValues {

    private final ConfigurationSection worldConfig;
    public static int lavafallAttempts;
    public static int lavafallMinHeight;
    public static int lavafallMaxMinus;
    public static int lavafallMaxHeight;
    public static int brownShroomAttempts;
    public static int brownShroomMinHeight;
    public static int brownShroomMaxHeight;
    public static int hiddenLavaAttempts;
    public static int hiddenLavaMinHeight;
    public static int hiddenLavaMaxMinus;
    public static int hiddenLavaMaxHeight;
    public static boolean generateFortress;
    public static int fortressMin;
    public static int fortressMax;

    public ConfigValues(ConfigurationSection worldConfig) {
        this.worldConfig = worldConfig;
        ConfigValues.lavafallAttempts = setDecoration("lavafall-attempts", 12);
        ConfigValues.lavafallMinHeight = setDecoration("lavafall-min-height", 4);
        ConfigValues.lavafallMaxHeight = setDecoration("lavafall-max-height", 248);
        ConfigValues.lavafallMaxMinus = 256 - ConfigValues.lavafallMaxHeight;
        ConfigValues.hiddenLavaAttempts = setDecoration("hidden-lava-attempts", 32);
        ConfigValues.hiddenLavaMinHeight = setDecoration("hidden-lava-min-height", 10);
        ConfigValues.hiddenLavaMaxHeight = setDecoration("hidden-lava-max-height", 246);
        ConfigValues.hiddenLavaMaxMinus = 256 - ConfigValues.hiddenLavaMaxHeight;
        ConfigValues.brownShroomAttempts = setDecoration("brown-shroom-attempts", 2);
        ConfigValues.brownShroomMinHeight = setDecoration("brown-shroom-min-height", 0);
        ConfigValues.brownShroomMaxHeight = setDecoration("brown-shroom-max-height", 256);
        ConfigValues.generateFortress = this.worldConfig.getBoolean("generate-fortress", true);
        ConfigValues.fortressMin = setDecoration("fortress-min", 64);
        ConfigValues.fortressMax = setDecoration("fortress-max", 90);
    };

    private int setDecoration(String setting, int defaultValue) {
        return this.worldConfig.getInt(setting, defaultValue);
    }
}
