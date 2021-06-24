package com.minefit.xerxestireiron.tallnether.v1_13_R2_2;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.minefit.xerxestireiron.tallnether.Messages;
import com.minefit.xerxestireiron.tallnether.ReflectionHelper;

import net.minecraft.server.v1_13_R2.BiomeLayout;
import net.minecraft.server.v1_13_R2.Biomes;
import net.minecraft.server.v1_13_R2.Blocks;
import net.minecraft.server.v1_13_R2.ChunkGenerator;
import net.minecraft.server.v1_13_R2.ChunkTaskScheduler;
import net.minecraft.server.v1_13_R2.GeneratorSettingsNether;

public class LoadHell {
    private final Messages messages;
    private final Decorators decorators;
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final HashMap<String, WorldInfo> worldInfos;
    private final boolean isPaper;

    public LoadHell(World world, ConfigurationSection worldConfig, boolean isPaper, String pluginName) {
        this.isPaper = isPaper;
        // Add vanilla values
        this.configAccessor.addConfig(null,
                new ConfigValues(null, worldConfig, new PaperSpigot(null, this.isPaper).getSettingsMap()));
        this.messages = new Messages(pluginName);
        this.decorators = new Decorators();
        this.worldInfos = new HashMap<>();
    }

    public boolean overrideDecorators() {
        return this.decorators.set();
    }

    public boolean restoreDecorators() {
        return this.decorators.restore();
    }

    public void addWorld(World world, ConfigurationSection worldConfig) {
        String worldName = world.getName();
        this.configAccessor.addConfig(worldName,
                new ConfigValues(worldName, worldConfig, new PaperSpigot(worldName, this.isPaper).getSettingsMap()));
        this.worldInfos.putIfAbsent(worldName, new WorldInfo(world, worldConfig));
    }

    public void removeWorld(World world) {
        this.worldInfos.remove(world.getName());
    }

    public boolean restoreGenerator(World world) {
        WorldInfo worldInfo = this.worldInfos.get(world.getName());
        return this.setGenerator(worldInfo, worldInfo.originalGenerator, true);
    }

    public void overrideGenerator(World world) {
        String worldName = world.getName();
        WorldInfo worldInfo = this.worldInfos.get(worldName);
        GeneratorSettingsNether generatorsettingsnether = new GeneratorSettingsNether();
        generatorsettingsnether.a(Blocks.NETHERRACK.getBlockData());
        generatorsettingsnether.b(Blocks.LAVA.getBlockData());
        Environment environment = world.getEnvironment();

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(worldName, environment.toString());
            return;
        }

        if (worldInfo.originalGenName.equals("TallNether_ChunkProviderHell")) {
            this.messages.alreadyEnabled(worldName);
            return;
        }

        if (!isRecognizedGenerator(environment, worldInfo.originalGenName)) {
            this.messages.unknownGenerator(worldName, worldInfo.originalGenName);
            return;
        }

        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(worldInfo.nmsWorld,
                BiomeLayout.b.a(BiomeLayout.b.b().a(Biomes.NETHER)), generatorsettingsnether, worldInfo.configValues);

        if (setGenerator(worldInfo, tallNetherGenerator, false)) {
            this.messages.enableSuccess(worldName);
        } else {
            this.messages.enableFailed(worldName);
        }
    }

    private boolean isRecognizedGenerator(Environment environment, String originalGenName) {
        if (environment == Environment.NETHER) {
            return originalGenName.equals("NetherChunkGenerator") || originalGenName.equals("TimedChunkGenerator");
        }

        return false;
    }

    private boolean setGenerator(WorldInfo worldInfo, ChunkGenerator<?> generator, boolean heightValue) {
        try {
            Field chunkGenerator = ReflectionHelper.getField(worldInfo.chunkServer.getClass(), "chunkGenerator", true);
            chunkGenerator.setAccessible(true);
            ReflectionHelper.fieldSetter(chunkGenerator, worldInfo.chunkServer, generator);

            Field worldHeight = ReflectionHelper.getField(worldInfo.worldProvider.getClass(), "d", true);
            worldHeight.setAccessible(true);
            worldHeight.setBoolean(worldInfo.worldProvider, heightValue);

            Field scheduler = ReflectionHelper.getField(worldInfo.chunkServer.getClass(), "chunkScheduler", true);
            scheduler.setAccessible(true);
            ChunkTaskScheduler taskScheduler = (ChunkTaskScheduler) scheduler.get(worldInfo.chunkServer);

            Field schedulerGenerator = ReflectionHelper.getField(taskScheduler.getClass(), "d", true);
            scheduler.setAccessible(true);
            ReflectionHelper.fieldSetter(schedulerGenerator, taskScheduler, generator);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }

        return true;
    }
}
