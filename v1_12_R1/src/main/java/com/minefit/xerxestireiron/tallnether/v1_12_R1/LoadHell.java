package com.minefit.xerxestireiron.tallnether.v1_12_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.minefit.xerxestireiron.tallnether.Messages;

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

    public LoadHell(World world, ConfigurationSection worldConfig, String pluginName) {
        this.world = world;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.worldName = this.world.getName();
        this.paperSpigot = new PaperSpigot(this.worldName);
        this.configValues = new ConfigValues(this.worldName, worldConfig, this.paperSpigot.getSettingsMap());
        this.messages = new Messages(pluginName);
        this.chunkServer = this.nmsWorld.getChunkProviderServer();
        this.originalGenerator = this.chunkServer.chunkGenerator;
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        this.worldProvider = this.nmsWorld.worldProvider;
        overrideGenerator();

        if (this.enabled) {
            this.messages.enableSuccess(this.worldName);
        } else {
            this.messages.enableFailed(this.worldName);
        }
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
        this.enabled = setGenerator(tallNetherGenerator, false);
    }

    private boolean isRecognizedGenerator(Environment environment, String originalGenName) {
        if (environment == Environment.NETHER) {
            return originalGenName.equals("NetherChunkGenerator") || originalGenName.equals("TimedChunkGenerator");
        }

        return false;
    }

    private boolean setGenerator(ChunkGenerator generator, boolean heightValue) {
        try {
            Field chunkGenerator = getField(this.chunkServer.getClass(), "chunkGenerator", true);
            chunkGenerator.setAccessible(true);
            setFinal(chunkGenerator, this.chunkServer, generator);

            Field worldHeight = getField(this.worldProvider.getClass(), "e", true);
            worldHeight.setAccessible(true);
            worldHeight.setBoolean(this.worldProvider, heightValue);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static Field getField(Class<?> baseClass, String fieldName, boolean declared) throws NoSuchFieldException {
        Field field;

        try {
            if (declared) {
                field = baseClass.getDeclaredField(fieldName);
            } else {
                field = baseClass.getField(fieldName);
            }
        } catch (NoSuchFieldException e) {
            Class<?> superClass = baseClass.getSuperclass();

            if (superClass != null) {
                field = getField(superClass, fieldName, declared);
            } else {
                throw e;
            }
        }

        return field;
    }

    private void setFinal(Field field, Object instance, Object obj) throws Exception {
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(instance, obj);
    }
}
