package com.minefit.XerxesTireIron.TallNether;

public class PaperSpigot {
    private final TallNether plugin;

    public PaperSpigot(TallNether instance) {
        this.plugin = instance;
    }

    public com.destroystokyo.paper.PaperWorldConfig getPaperWorldConfig(String worldName) {
        return new com.destroystokyo.paper.PaperWorldConfig(worldName, new org.spigotmc.SpigotWorldConfig(worldName));
    }
}
