package com.minefit.xerxestireiron.tallnether.v1_13_R2_2;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

public class PaperSpigot {
    public final boolean generateCanyon;
    public final boolean generateCaves;
    public final boolean generateDungeon;
    public final boolean generateFortress;
    public final boolean generateMineshaft;
    public final boolean generateMonument;
    public final boolean generateStronghold;
    public final boolean generateTemple;
    public final boolean generateVillage;
    public final boolean generateFlatBedrock;

    // Used for vanilla
    public PaperSpigot() {
        this(null, Bukkit.getName().contains("Paper"));
    }

    public PaperSpigot(String worldName, boolean isPaper) {
        if(worldName != null) {
            ; // Nothing until Paper restores settings
        }

        this.generateCanyon = true;
        this.generateCaves = true;
        this.generateDungeon = true;
        this.generateFortress = true;
        this.generateMineshaft = true;
        this.generateMonument = true;
        this.generateStronghold = true;
        this.generateTemple = true;
        this.generateVillage = true;
        this.generateFlatBedrock = false;
    }

    public PaperSpigot(String worldName) {
        this(worldName, Bukkit.getName().contains("Paper"));
    }

    public Map<String, Boolean> getSettingsMap() {
        Map<String, Boolean> settingsMap = new HashMap<>();
        settingsMap.put("generateCanyon", this.generateCanyon);
        settingsMap.put("generateCaves", this.generateCaves);
        settingsMap.put("generateDungeon", this.generateDungeon);
        settingsMap.put("generateFortress", this.generateFortress);
        settingsMap.put("generateMineshaft", this.generateMineshaft);
        settingsMap.put("generateMonument", this.generateMonument);
        settingsMap.put("generateStronghold", this.generateStronghold);
        settingsMap.put("generateTemple", this.generateTemple);
        settingsMap.put("generateVillage", this.generateVillage);
        settingsMap.put("generateFlatBedrock", this.generateFlatBedrock);
        return settingsMap;
    }
}