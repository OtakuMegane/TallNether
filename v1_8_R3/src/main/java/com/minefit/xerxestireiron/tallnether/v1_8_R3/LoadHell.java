package com.minefit.xerxestireiron.tallnether.v1_8_R3;

import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_8_R3.IChunkProvider;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.ChunkProviderServer;

public class LoadHell implements Listener {
    private final World world;
    private final WorldServer nmsWorld;
    private final String worldName;
    private String originalGenName;
    private final Messages messages;
    private ChunkProviderServer chunkProviderServer;
    private IChunkProvider originalGenerator;
    private final ConfigurationSection worldConfig;
    public final ConfigValues configValues;
    private boolean enabled = false;
    private final TravelAgent portalTravelAgent;

    public LoadHell(World world, ConfigurationSection worldConfig, String pluginName) {
        this.world = world;
        this.worldName = world.getName();
        this.worldConfig = worldConfig;
        this.configValues = new ConfigValues(this.worldName, this.worldConfig);
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.messages = new Messages(pluginName);
        this.chunkProviderServer = this.nmsWorld.chunkProviderServer;
        this.originalGenerator = this.chunkProviderServer.chunkProvider;
        overrideGenerator();
        this.portalTravelAgent = new TallNether_CraftTravelAgent(this.nmsWorld);
    }

    public void restoreGenerator() {
        if (this.enabled) {
            this.chunkProviderServer.chunkProvider = this.originalGenerator;
            this.enabled = false;
        }
    }

    public void overrideGenerator() {
        boolean genFeatures = this.nmsWorld.getWorldData().shouldGenerateMapFeatures();
        long worldSeed = this.nmsWorld.getSeed();
        Environment environment = this.world.getEnvironment();

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(this.worldName, environment.toString());
            return;
        }

        if (this.originalGenName.equals("TallNether_ChunkProviderHell")) {
            this.messages.alreadyEnabled(this.worldName);
            return;
        }

        if (!this.originalGenName.equals("NetherChunkGenerator")) {
            this.messages.unknownGenerator(this.worldName, this.originalGenName);
            return;
        }

        this.chunkProviderServer.chunkProvider = new TallNether_ChunkProviderHell(this.nmsWorld, genFeatures, worldSeed,
                this.configValues);
        this.messages.enableSuccess(this.worldName);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (!this.worldConfig.getBoolean("special-travel-agent", true)) {
            return;
        }

        Location destination = event.getTo();

        if (destination == null) {
            return;
        }

        World targetWorld = destination.getWorld();

        if (targetWorld.getName() != this.nmsWorld.worldData.getName()) {
            return;
        }

        if (this.worldConfig.getBoolean("enabled", false)) {
            event.setPortalTravelAgent(this.portalTravelAgent);
        }
    }
}
