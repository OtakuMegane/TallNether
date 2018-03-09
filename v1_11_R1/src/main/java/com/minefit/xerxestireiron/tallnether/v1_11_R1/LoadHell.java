package com.minefit.xerxestireiron.tallnether.v1_11_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_11_R1.ChunkGenerator;
import net.minecraft.server.v1_11_R1.WorldServer;

public class LoadHell implements Listener {
    private final World world;
    private final WorldServer nmsWorld;
    private final Messages messages;
    private ChunkGenerator originalGenerator;
    private final TravelAgent portalTravelAgent;
    private final ConfigurationSection worldConfig;

    public LoadHell(World world, ConfigurationSection worldConfig, String pluginName) {
        this.world = world;
        this.worldConfig = worldConfig;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.messages = new Messages(pluginName);
        overrideGenerator();
        this.portalTravelAgent = new TallNether_CraftTravelAgent(this.nmsWorld);
    }

    public void restoreGenerator() {
        try {
            Field cp = net.minecraft.server.v1_11_R1.ChunkProviderServer.class.getDeclaredField("chunkGenerator");
            cp.setAccessible(true);
            setFinal(cp, this.originalGenerator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void overrideGenerator() {
        String worldName = this.world.getName();
        this.originalGenerator = this.nmsWorld.getChunkProviderServer().chunkGenerator;
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

        try {
            Field cp = net.minecraft.server.v1_11_R1.ChunkProviderServer.class.getDeclaredField("chunkGenerator");
            cp.setAccessible(true);

            if (!originalGenName.equals("NetherChunkGenerator") && !originalGenName.equals("TimedChunkGenerator")) {
                this.messages.unknownGenerator(worldName, originalGenName);
                return;
            }

            TallNether_ChunkProviderHell generator = new TallNether_ChunkProviderHell(this.nmsWorld, genFeatures,
                    worldSeed, this.worldConfig);
            setFinal(cp, generator);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.messages.enabledSuccessfully(worldName);
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

    public void setFinal(Field field, Object obj) throws Exception {
        field.setAccessible(true);

        Field mf = Field.class.getDeclaredField("modifiers");
        mf.setAccessible(true);
        mf.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(this.nmsWorld.getChunkProviderServer(), obj);
    }
}
