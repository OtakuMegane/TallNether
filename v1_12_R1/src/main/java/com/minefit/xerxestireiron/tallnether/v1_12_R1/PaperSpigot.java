package com.minefit.xerxestireiron.tallnether.v1_12_R1;

import org.spigotmc.SpigotWorldConfig;

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

    public PaperSpigot(String worldName, boolean isPaper) {

        if (isPaper) {
            com.destroystokyo.paper.PaperWorldConfig paperConfig = new com.destroystokyo.paper.PaperWorldConfig(
                    worldName, new SpigotWorldConfig(worldName));
            this.generateCanyon = paperConfig.generateCanyon;
            this.generateCaves = paperConfig.generateCaves;
            this.generateDungeon = paperConfig.generateDungeon;
            this.generateFortress = paperConfig.generateFortress;
            this.generateMineshaft = paperConfig.generateMineshaft;
            this.generateMonument = paperConfig.generateMonument;
            this.generateStronghold = paperConfig.generateStronghold;
            this.generateTemple = paperConfig.generateTemple;
            this.generateVillage = paperConfig.generateVillage;
            this.generateFlatBedrock = paperConfig.generateFlatBedrock;
        } else {
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
    }
}