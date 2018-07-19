package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import net.minecraft.server.v1_13_R1.World;

public class ConfigValues {

    private final ConfigurationSection worldConfig;
    private final PaperSpigot paperSpigot;
    public static int lavafallAttempts;
    public static int lavafallMinHeight;
    public static int lavafallMaxMinus;
    public static int lavafallMaxHeight;
    public static int glowstone1Attempts;
    public static int glowstone1MinHeight;
    public static int glowstone1MaxHeight;
    public static int glowstone2Attempts;
    public static int glowstone2MinHeight;
    public static int glowstone2MaxMinus;
    public static int glowstone2MaxHeight;
    public static int fireAttempts;
    public static int fireMinHeight;
    public static int fireMaxHeight;
    public static int brownShroomAttempts;
    public static int brownShroomMinHeight;
    public static int brownShroomMaxHeight;
    public static int redShroomAttempts;
    public static int redShroomMinHeight;
    public static int redShroomMaxHeight;
    public static int quartzAttempts;
    public static int quartzMinHeight;
    public static int quartzMaxMinus;
    public static int quartzMaxHeight;
    public static int magmaAttempts;
    public static int magmaMinHeight;
    public static int magmaMaxHeight;
    public static int magmaRangeSize;
    public static int magmaRangeMedian;
    public static int hiddenLavaAttempts;
    public static int hiddenLavaMinHeight;
    public static int hiddenLavaMaxMinus;
    public static int hiddenLavaMaxHeight;
    public static boolean generateFortress;
    public static int fortressMin;
    public static int fortressMax;

    public ConfigValues(World world, ConfigurationSection worldConfig) {
        this.worldConfig = worldConfig;
        this.paperSpigot = new PaperSpigot(world.worldData.getName(), Bukkit.getName().contains("Paper"));
        ConfigValues.lavafallAttempts = setDecoration("lavafall-attempts", 12, false, false);
        ConfigValues.lavafallMinHeight = setDecoration("lavafall-min-height", 4, true, false);
        ConfigValues.lavafallMaxHeight = setDecoration("lavafall-max-height", 248, false, true);
        ConfigValues.lavafallMaxMinus = 256 - ConfigValues.lavafallMaxHeight;
        ConfigValues.glowstone1Attempts = setDecoration("glowstone1-attempts", 10, false, false);
        ConfigValues.glowstone1MinHeight = setDecoration("glowstone1-min-height", 4, true, false);
        ConfigValues.glowstone1MaxHeight = setDecoration("glowstone1-max-height", 248, false, true);
        ConfigValues.glowstone2Attempts = setDecoration("glowstone2-attempts", 20, false, false);
        ConfigValues.glowstone2MinHeight = setDecoration("glowstone2-min-height", 0, true, false);
        ConfigValues.glowstone2MaxHeight = setDecoration("glowstone2-max-height", 256, false, true);
        ConfigValues.glowstone2MaxMinus = 256 - ConfigValues.glowstone2MaxHeight;
        ConfigValues.fireAttempts = setDecoration("fire-attempts", 20, false, false);
        ConfigValues.fireMinHeight = setDecoration("fire-min-height", 4, true, false);
        ConfigValues.fireMaxHeight = setDecoration("fire-max-height", 248, false, true);
        ConfigValues.brownShroomAttempts = setDecoration("brown-shroom-attempts", 2, false, false);
        ConfigValues.brownShroomMinHeight = setDecoration("brown-shroom-min-height", 0, true, false);
        ConfigValues.brownShroomMaxHeight = setDecoration("brown-shroom-max-height", 256, false, true);
        ConfigValues.redShroomAttempts = setDecoration("red-shroom-attempts", 2, false, false);
        ConfigValues.redShroomMinHeight = setDecoration("red-shroom-min-height", 0, true, false);
        ConfigValues.redShroomMaxHeight = setDecoration("red-shroom-max-height", 256, false, true);
        ConfigValues.quartzAttempts = setDecoration("quartz-attempts", 32, false, false);
        ConfigValues.quartzMinHeight = setDecoration("quartz-min-height", 10, true, false);
        ConfigValues.quartzMaxHeight = setDecoration("quartz-max-height", 246, false, true);
        ConfigValues.quartzMaxMinus = 256 - ConfigValues.quartzMaxHeight;
        ConfigValues.magmaAttempts = setDecoration("magma-attempts", 4, false, false);
        ConfigValues.magmaMinHeight = setDecoration("magma-min-height", 43, true, false);
        ConfigValues.magmaMaxHeight = setDecoration("magma-max-height", 53, false, true);
        ConfigValues.magmaRangeSize = ConfigValues.magmaMaxHeight - ConfigValues.magmaMinHeight;
        ConfigValues.magmaRangeMedian = (int) (ConfigValues.magmaRangeSize / 2);
        ConfigValues.hiddenLavaAttempts = setDecoration("hidden-lava-attempts", 32, false, false);
        ConfigValues.hiddenLavaMinHeight = setDecoration("hidden-lava-min-height", 10, true, false);
        ConfigValues.hiddenLavaMaxHeight = setDecoration("hidden-lava-max-height", 246, false, true);
        ConfigValues.hiddenLavaMaxMinus = 256 - ConfigValues.hiddenLavaMaxHeight;
        ConfigValues.generateFortress = this.worldConfig.getBoolean("generate-fortress",
                this.paperSpigot.generateFortress);
        ConfigValues.fortressMin = setDecoration("fortress-min", 64, false, false);
        ConfigValues.fortressMax = setDecoration("fortress-max", 90, false, false);
    };

    private int setDecoration(String setting, int defaultValue, boolean safetyMin, boolean safetyMax) {
        int value = this.worldConfig.getInt(setting, defaultValue);

        if (safetyMin && value < 0) {
            return 0;
        }

        if (safetyMax && value > 256) {
            return 256;
        }

        return value;
    }
}
