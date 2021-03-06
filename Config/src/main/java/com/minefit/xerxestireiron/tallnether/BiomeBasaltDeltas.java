package com.minefit.xerxestireiron.tallnether;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class BiomeBasaltDeltas extends BiomeValues {

    // Vanilla
    public BiomeBasaltDeltas(Map<String, Boolean> paperConfig) {
        super(paperConfig);
        Map<String, Integer> values = new HashMap<>();
        values.put("lavafall-attempts", 16);
        values.put("lavafall-min-height", 4);
        values.put("lavafall-max-height", 128);
        values.put("lavafall-max-offset", 8);
        values.put("glowstone1-attempts", 10);
        values.put("glowstone1-min-height", 4);
        values.put("glowstone1-max-height", 120);
        values.put("glowstone2-attempts", 10);
        values.put("glowstone2-min-height", 0);
        values.put("glowstone2-max-height", 128);
        values.put("glowstone2-max-offset", 0);
        values.put("fire-attempts", 5);
        values.put("fire-min-height", 4);
        values.put("fire-max-height", 120);
        values.put("soul-fire-attempts", 5);
        values.put("soul-fire-min-height", 4);
        values.put("soul-fire-max-height", 120);
        values.put("nether-gold-attempts", 20);
        values.put("nether-gold-min-height", 10);
        values.put("nether-gold-max-height", 128);
        values.put("nether-gold-max-offset", 20);
        values.put("quartz-attempts", 32);
        values.put("quartz-min-height", 10);
        values.put("quartz-max-height", 128);
        values.put("quartz-max-offset", 20);
        values.put("magma-block-attempts", 4);
        values.put("magma-block-min-height", 27);
        values.put("magma-block-max-height", 37);
        values.put("magma-block-range-size", values.get("magma-block-max-height") - values.get("magma-block-min-height"));
        values.put("magma-block-range-median", (int) values.get("magma-block-range-size") / 2);
        values.put("hidden-lava-attempts", 32);
        values.put("hidden-lava-min-height", 10);
        values.put("hidden-lava-max-height", 128);
        values.put("hidden-lava-max-offset", 20);
        values.put("ancient-debris1-attempts", 1);
        values.put("ancient-debris1-min-height", 8);
        values.put("ancient-debris1-max-height", 22);
        values.put("ancient-debris1-range-size", values.get("ancient-debris1-max-height") - values.get("ancient-debris1-min-height"));
        values.put("ancient-debris1-range-median", (int) values.get("ancient-debris1-range-size") / 2);
        values.put("ancient-debris2-attempts", 1);
        values.put("ancient-debris2-min-height", 8);
        values.put("ancient-debris2-max-height", 128);
        values.put("ancient-debris2-max-offset", 16);
        this.values = Collections.unmodifiableMap(values);
    }

    public BiomeBasaltDeltas(ConfigurationSection biomeConfig, Map<String, Boolean> paperConfig) {
        super(biomeConfig, paperConfig);
        Map<String, Integer> values = new HashMap<>();
        values.put("lavafall-attempts", setDecoration("lavafall-attempts", 32, false));
        values.put("lavafall-min-height", setDecoration("lavafall-min-height", 4, true));
        values.put("lavafall-max-height", setDecoration("lavafall-max-height", 252, true));
        values.put("lavafall-max-offset", values.get("lavafall-min-height"));
        values.put("glowstone1-attempts", setDecoration("glowstone1-attempts", 20, false));
        values.put("glowstone1-min-height", setDecoration("glowstone1-min-height", 4, true));
        values.put("glowstone1-max-height", setDecoration("glowstone1-max-height", 252, true));
        values.put("glowstone2-attempts", setDecoration("glowstone2-attempts", 20, false));
        values.put("glowstone2-min-height", setDecoration("glowstone2-min-height", 0, true));
        values.put("glowstone2-max-height", setDecoration("glowstone2-max-height", 256, true));
        values.put("glowstone2-max-offset", values.get("glowstone2-min-height"));
        values.put("fire-attempts", setDecoration("fire-attempts", 10, false));
        values.put("fire-min-height", setDecoration("fire-min-height", 4, true));
        values.put("fire-max-height", setDecoration("fire-max-height", 252, true));
        values.put("soul-fire-attempts", setDecoration("soul-fire-attempts", 10, false));
        values.put("soul-fire-min-height", setDecoration("soul-fire-min-height", 4, true));
        values.put("soul-fire-max-height", setDecoration("soul-fire-max-height", 252, true));
        values.put("nether-gold-attempts", setDecoration("nether-gold-attempts", 40, false));
        values.put("nether-gold-min-height", setDecoration("nether-gold-min-height", 10, true));
        values.put("nether-gold-max-height", setDecoration("nether-gold-max-height", 246, true));
        values.put("nether-gold-max-offset", values.get("nether-gold-min-height"));
        values.put("quartz-attempts", setDecoration("quartz-attempts", 64, false));
        values.put("quartz-min-height", setDecoration("quartz-min-height", 10, true));
        values.put("quartz-max-height", setDecoration("quartz-max-height", 246, true));
        values.put("quartz-max-offset", values.get("quartz-min-height"));
        values.put("magma-block-attempts", setDecoration("magma-block-attempts", 4, false));
        values.put("magma-block-min-height", setDecoration("magma-block-min-height", 43, true));
        values.put("magma-block-max-height", setDecoration("magma-block-max-height", 53, true));
        values.put("magma-block-range-size", values.get("magma-block-max-height") - values.get("magma-block-min-height"));
        values.put("magma-block-range-median", (int) values.get("magma-block-range-size") / 2);
        values.put("hidden-lava-attempts", setDecoration("hidden-lava-attempts", 64, false));
        values.put("hidden-lava-min-height", setDecoration("hidden-lava-min-height", 10, true));
        values.put("hidden-lava-max-height", setDecoration("hidden-lava-max-height", 246, true));
        values.put("hidden-lava-max-offset", values.get("hidden-lava-min-height"));
        values.put("ancient-debris1-attempts", setDecoration("ancient-debris1-attempts", 2, false));
        values.put("ancient-debris1-min-height", setDecoration("ancient-debris1-min-height", 8, true));
        values.put("ancient-debris1-max-height", setDecoration("ancient-debris1-max-height", 44, true));
        values.put("ancient-debris1-range-size", values.get("ancient-debris1-max-height") - values.get("ancient-debris1-min-height"));
        values.put("ancient-debris1-range-median", (int) values.get("ancient-debris1-range-size") / 2);
        values.put("ancient-debris2-attempts", setDecoration("ancient-debris2-attempts", 2, false));
        values.put("ancient-debris2-min-height", setDecoration("ancient-debris2-min-height", 8, true));
        values.put("ancient-debris2-max-height", setDecoration("ancient-debris2-max-height", 248, true));
        values.put("ancient-debris2-max-offset", values.get("ancient-debris2-min-height"));
        this.values = Collections.unmodifiableMap(values);
    }
}
