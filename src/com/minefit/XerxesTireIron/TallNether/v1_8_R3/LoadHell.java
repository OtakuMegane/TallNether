package com.minefit.XerxesTireIron.TallNether.v1_8_R3;

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

import com.minefit.XerxesTireIron.TallNether.Messages;
import com.minefit.XerxesTireIron.TallNether.TallNether;

import net.minecraft.server.v1_8_R3.IChunkProvider;
import net.minecraft.server.v1_8_R3.WorldServer;

public class LoadHell implements Listener {
    private final TallNether plugin;
    private final World world;
    private final WorldServer nmsWorld;
    private Messages messages;
    private IChunkProvider originalGenerator;
    private final TravelAgent portalTravelAgent;
    private final ConfigurationSection worldConfig;

    public LoadHell(World world, TallNether instance) {
        this.plugin = instance;
        this.world = world;
        this.worldConfig = this.plugin.getConfig().getConfigurationSection("worlds." + this.world.getName());
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.messages = new Messages(this.plugin);
        overrideGenerator();
        this.portalTravelAgent = new TallNether_CraftTravelAgent(this.nmsWorld);
    }

    public void restoreGenerator() {
        this.nmsWorld.chunkProviderServer.chunkProvider = this.originalGenerator;
    }

    public void overrideGenerator() {
        String worldName = this.world.getName();
        this.originalGenerator = this.nmsWorld.chunkProviderServer.chunkProvider;
        String originalGenName = this.originalGenerator.getClass().getSimpleName();
        boolean genFeatures = this.nmsWorld.getWorldData().shouldGenerateMapFeatures();
        long worldSeed = this.nmsWorld.getSeed();
        Environment environment = this.world.getEnvironment();

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(worldName, environment.toString());
            return;
        }

        if (originalGenName.equals("TallNether_ChunkProviderHell")) {
            this.messages.alreadyEnabled(worldName);
            return;
        }

        if (!originalGenName.equals("NetherChunkGenerator") && !originalGenName.equals("TimedChunkGenerator")) {
            this.messages.unknownGenerator(worldName, originalGenName);
            return;
        }

        this.nmsWorld.chunkProviderServer.chunkProvider = new TallNether_ChunkProviderHell(this.nmsWorld, genFeatures,
                worldSeed, this.worldConfig, this.plugin);
        this.messages.enabledSuccessfully(worldName);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerPortal(PlayerPortalEvent event) {
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
