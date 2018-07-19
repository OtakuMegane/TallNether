package com.minefit.xerxestireiron.tallnether.v1_12_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.Listener;

import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_12_R1.ChunkGenerator;
import net.minecraft.server.v1_12_R1.WorldProvider;
import net.minecraft.server.v1_12_R1.WorldServer;

public class LoadHell implements Listener {
    private final World world;
    private final WorldServer nmsWorld;
    private final String worldName;
    private String originalGenName;
    private WorldProvider worldProvider;
    private final Messages messages;
    private ChunkGenerator originalGenerator;
    private final ConfigurationSection worldConfig;
    public final ConfigValues configValues;
    private boolean enabled = false;

    public LoadHell(World world, ConfigurationSection worldConfig, String pluginName) {
        this.world = world;
        this.worldConfig = worldConfig;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.worldName = this.world.getName();
        this.configValues = new ConfigValues(this.worldName, this.worldConfig);
        this.messages = new Messages(pluginName);
        this.originalGenerator = this.nmsWorld.getChunkProviderServer().chunkGenerator;
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        this.worldProvider = this.nmsWorld.worldProvider;
        overrideGenerator();
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
        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(this.nmsWorld, genFeatures,
                worldSeed, this.configValues);

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(this.worldName, environment.toString());
            return;
        }

        if (this.originalGenName.equals("TallNether_ChunkProviderHell")) {
            this.messages.alreadyEnabled(this.worldName);
            return;
        }

        if (!this.originalGenName.equals("NetherChunkGenerator")
                && !this.originalGenName.equals("TimedChunkGenerator")) {
            this.messages.unknownGenerator(this.worldName, this.originalGenName);
            return;
        }

        this.enabled = setGenerator(tallNetherGenerator, false);

        if (this.enabled) {
            this.messages.enableSuccess(this.worldName);
        } else {
            this.messages.enableFailed(this.worldName);
        }
    }

    private boolean setGenerator(ChunkGenerator generator, boolean heightValue) {
        try {
            Field chunkGenerator = net.minecraft.server.v1_12_R1.ChunkProviderServer.class
                    .getDeclaredField("chunkGenerator");
            chunkGenerator.setAccessible(true);
            setFinal(chunkGenerator, generator);

            Field worldHeight = net.minecraft.server.v1_12_R1.WorldProvider.class.getDeclaredField("e");
            worldHeight.setAccessible(true);
            worldHeight.setBoolean(this.worldProvider, heightValue);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void setFinal(Field field, Object obj) throws Exception {
        field.setAccessible(true);

        Field mf = Field.class.getDeclaredField("modifiers");
        mf.setAccessible(true);
        mf.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(this.nmsWorld.getChunkProviderServer(), obj);
    }
}
