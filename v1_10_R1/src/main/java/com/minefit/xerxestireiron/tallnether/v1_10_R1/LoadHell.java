package com.minefit.xerxestireiron.tallnether.v1_10_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_10_R1.ChunkGenerator;
import net.minecraft.server.v1_10_R1.ChunkProviderServer;
import net.minecraft.server.v1_10_R1.WorldServer;

public class LoadHell implements Listener {
    private final World world;
    private final WorldServer nmsWorld;
    private final String worldName;
    private String originalGenName;
    private final Messages messages;
    private ChunkProviderServer chunkProviderServer;
    private ChunkGenerator originalGenerator;
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
        this.chunkProviderServer = this.nmsWorld.getChunkProviderServer();
        this.originalGenerator = this.nmsWorld.getChunkProviderServer().chunkGenerator;
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        overrideGenerator();
        this.portalTravelAgent = new TallNether_CraftTravelAgent(this.nmsWorld);
    }

    public void restoreGenerator() {
        if (this.enabled) {
            if (!setGenerator(this.originalGenerator, true)) {
                this.messages.restoreFailed(this.worldName);
            }

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

        if (!isRecognizedGenerator(environment, this.originalGenName)) {
            this.messages.unknownGenerator(this.worldName, originalGenName);
            return;
        }

        TallNether_ChunkProviderHell generator = new TallNether_ChunkProviderHell(this.nmsWorld, genFeatures, worldSeed,
                this.configValues);
        this.enabled = setGenerator(generator, false);

        if (this.enabled) {
            this.messages.enableSuccess(this.worldName);
        } else {
            this.messages.enableFailed(this.worldName);
        }
    }

    private boolean isRecognizedGenerator(Environment environment, String originalGenName) {
        if (environment == Environment.NETHER) {
            return originalGenName.equals("NetherChunkGenerator") || originalGenName.equals("TimedChunkGenerator");
        }

        return false;
    }

    private boolean setGenerator(ChunkGenerator generator, boolean heightValue) {
        try {
            Field chunkGenerator = this.chunkProviderServer.getClass().getDeclaredField("chunkGenerator");
            setFinal(chunkGenerator, this.chunkProviderServer, generator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
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

    public void setFinal(Field field, Object instance, Object obj) throws Exception {
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(instance, obj);
        modifiers.setAccessible(false);
        field.setAccessible(false);
    }
}
