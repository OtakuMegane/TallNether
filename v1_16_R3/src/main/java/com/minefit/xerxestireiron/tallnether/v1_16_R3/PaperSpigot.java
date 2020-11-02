package com.minefit.xerxestireiron.tallnether.v1_16_R3;

import java.util.HashMap;
import java.util.Map;

import org.spigotmc.SpigotWorldConfig;

public class PaperSpigot {
    public final Map<String, Boolean> settingsMap = new HashMap<>();

    public PaperSpigot(String worldName, boolean isPaper) {
        // These settings aren't available currently
        this.settingsMap.put("generateCanyon", true);
        this.settingsMap.put("generateCaves", true);
        this.settingsMap.put("generateDungeon", true);
        this.settingsMap.put("generateFortress", true);
        this.settingsMap.put("generateMineshaft", true);
        this.settingsMap.put("generateMonument", true);
        this.settingsMap.put("generateStronghold", true);
        this.settingsMap.put("generateTemple", true);
        this.settingsMap.put("generateVillage", true);

        if(isPaper && worldName != null) {
            com.destroystokyo.paper.PaperWorldConfig paperConfig = new com.destroystokyo.paper.PaperWorldConfig(
                    worldName, new SpigotWorldConfig(worldName));
            this.settingsMap.put("generateFlatBedrock", paperConfig.generateFlatBedrock);
        }
        else
        {
            this.settingsMap.put("generateFlatBedrock", false);
        }
    }
}