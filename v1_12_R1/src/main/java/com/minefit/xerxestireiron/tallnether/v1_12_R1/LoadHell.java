package com.minefit.xerxestireiron.tallnether.v1_12_R1;

import java.lang.reflect.Field;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.minefit.xerxestireiron.tallnether.Messages;
import com.minefit.xerxestireiron.tallnether.ReflectionHelper;

import net.minecraft.server.v1_12_R1.ChunkGenerator;
import net.minecraft.server.v1_12_R1.ChunkProviderServer;
import net.minecraft.server.v1_12_R1.WorldProvider;
import net.minecraft.server.v1_12_R1.WorldServer;

public class LoadHell {
    private final World world;
    private final WorldServer nmsWorld;
    private final String worldName;
    private String originalGenName;
    private WorldProvider worldProvider;
    private final Messages messages;
    private ChunkProviderServer chunkServer;
    private ChunkGenerator originalGenerator;
    public final ConfigValues configValues;
    private boolean enabled = false;
    private final PaperSpigot paperSpigot;
    private final boolean isPaper;

    public LoadHell(World world, ConfigurationSection worldConfig, boolean isPaper, String pluginName) {
        this.world = world;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.worldName = this.world.getName();
        this.isPaper = isPaper;
        this.paperSpigot = new PaperSpigot(this.worldName, this.isPaper);
        this.configValues = new ConfigValues(this.worldName, worldConfig, this.paperSpigot.getSettingsMap());
        this.messages = new Messages(pluginName);
        this.chunkServer = this.nmsWorld.getChunkProviderServer();
        this.originalGenerator = this.chunkServer.chunkGenerator;
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        this.worldProvider = this.nmsWorld.worldProvider;
    }

    public void restoreGenerator() {
        if (this.enabled) {
            if (!setGenerator(this.originalGenerator, true)) {
                this.messages.restoreFailed(this.worldName);
            }

            this.enabled = false;
        }
    }

    public void overrideGenerator(World world) {
        Environment environment = this.world.getEnvironment();
        long worldSeed = this.nmsWorld.getSeed();
        boolean genFeatures = this.nmsWorld.getWorldData().shouldGenerateMapFeatures();

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

        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(this.nmsWorld, genFeatures,
                worldSeed, this.configValues);
        if (setGenerator(tallNetherGenerator, false)) {
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
            Field chunkGenerator = ReflectionHelper.getField(this.chunkServer.getClass(), "chunkGenerator", true);
            chunkGenerator.setAccessible(true);
            ReflectionHelper.fieldSetter(chunkGenerator, this.chunkServer, generator);

            Field worldHeight = ReflectionHelper.getField(this.worldProvider.getClass(), "e", true);
            worldHeight.setAccessible(true);
            worldHeight.setBoolean(this.worldProvider, heightValue);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }

        return true;
    }
}
