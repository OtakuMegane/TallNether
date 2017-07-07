package com.minefit.XerxesTireIron.TallNether.v1_12_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import com.minefit.XerxesTireIron.TallNether.Messages;
import com.minefit.XerxesTireIron.TallNether.TallNether;

import net.minecraft.server.v1_12_R1.ChunkGenerator;
import net.minecraft.server.v1_12_R1.WorldServer;

public class LoadHell implements Listener {
    private final TallNether plugin;
    private final World world;
    private final WorldServer nmsWorld;
    private final Messages messages;
    private ChunkGenerator originalGenerator;
    private final TravelAgent portalTravelAgent;
    private final ConfigurationSection worldConfig;

    public LoadHell(World world, TallNether instance) {
        this.plugin = instance;
        this.world = world;
        this.worldConfig = this.plugin.getConfig().getConfigurationSection("worlds." + this.world.getName());
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.messages = new Messages(this.plugin);
        overrideGenerator();

        if (fastutilStandardLocation()) {
            this.portalTravelAgent = new TallNether_CraftTravelAgent(this.nmsWorld);
        } else {
            this.portalTravelAgent = new TallNether_CraftTravelAgent_fastutil_nonstandard(this.nmsWorld);
        }
    }

    public void restoreGenerator() {
        try {
            Field cp = net.minecraft.server.v1_12_R1.ChunkProviderServer.class.getDeclaredField("chunkGenerator");
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
            Field cp = net.minecraft.server.v1_12_R1.ChunkProviderServer.class.getDeclaredField("chunkGenerator");
            cp.setAccessible(true);

            if (!originalGenName.equals("NetherChunkGenerator") && !originalGenName.equals("TimedChunkGenerator")) {
                this.messages.unknownGenerator(worldName, originalGenName);
                return;
            }

            TallNether_ChunkProviderHell generator = new TallNether_ChunkProviderHell(this.nmsWorld, genFeatures,
                    worldSeed, this.worldConfig, this.plugin);
            setFinal(cp, generator);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.messages.enabledSuccessfully(worldName);
    }

    @EventHandler(priority = EventPriority.HIGH)
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

    // I hate supporting PaperSpigot
    private boolean fastutilStandardLocation() {
        try {
            Class.forName("org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.longs.Long2ObjectMap");
            Class.forName("org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap");
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }
}
