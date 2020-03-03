package com.minefit.xerxestireiron.tallnether.v1_15_R1_2;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_15_R1.BiomeLayout;
import net.minecraft.server.v1_15_R1.Biomes;
import net.minecraft.server.v1_15_R1.Blocks;
import net.minecraft.server.v1_15_R1.ChunkGenerator;
import net.minecraft.server.v1_15_R1.GeneratorSettingsNether;

public class LoadHell {
    private final Messages messages;
    private final Decorators decorators;
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final HashMap<String, WorldInfo> worldInfos;

    public LoadHell(ConfigurationSection worldConfig, String pluginName) {
        // Add vanilla values
        this.configAccessor.addConfig(null, new ConfigValues(null, worldConfig, new PaperSpigot().getSettingsMap()));
        this.messages = new Messages(pluginName);
        this.decorators = new Decorators();
        this.worldInfos = new HashMap<>();
    }

    public boolean overrideDecorators(boolean check) {
        boolean alreadySet = false;

        // Check if decorators were already modified
        // If we set decorators a second time it causes null errors
        if(check) {
            alreadySet = this.decorators.alreadySet();
        }

        if(!alreadySet) {
            return this.decorators.set();
        }

        return false;
    }

    public boolean restoreDecorators() {
        return this.decorators.restore();
    }

    public void addWorld(World world, ConfigurationSection worldConfig) {
        String worldName = world.getName();
        this.configAccessor.addConfig(worldName,
                new ConfigValues(worldName, worldConfig, new PaperSpigot(worldName).getSettingsMap()));
        this.worldInfos.putIfAbsent(worldName, new WorldInfo(world, worldConfig));
    }

    public void removeWorld(World world) {
        this.worldInfos.remove(world.getName());
    }

    public void overrideGenerator(World world) {
        String worldName = world.getName();
        WorldInfo worldInfo = this.worldInfos.get(worldName);
        GeneratorSettingsNether generatorsettingsnether = new TallNether_GeneratorSettingsNether();
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
                BiomeLayout.b.a(BiomeLayout.b.a(worldInfo.nmsWorld.getWorldData()).a(Biomes.NETHER)),
                generatorsettingsnether, this.configAccessor.getConfig(worldName));

        if (setGenerator(worldInfo, tallNetherGenerator, false)) {
            this.messages.enableSuccess(worldName);
        } else {
            this.messages.enableFailed(worldName);
        }
    }

    public boolean restoreGenerator(World world) {
        WorldInfo worldInfo = this.worldInfos.get(world.getName());
        return this.setGenerator(worldInfo, worldInfo.originalGenerator, true);
    }

    private boolean isRecognizedGenerator(Environment environment, String originalGenName) {
        if (environment == Environment.NETHER) {
            return originalGenName.equals("ChunkProviderHell") || originalGenName.equals("TimedChunkGenerator");
        }

        return false;
    }

    private boolean setGenerator(WorldInfo worldInfo, ChunkGenerator<?> generator, boolean heightValue) {
        try {
            Field chunkGenerator = ReflectionHelper.getField(worldInfo.chunkServer.getClass(), "chunkGenerator", true);
            chunkGenerator.setAccessible(true);
            ReflectionHelper.setFinal(chunkGenerator, worldInfo.chunkServer, generator);

            Field worldHeight = ReflectionHelper.getField(worldInfo.worldProvider.getClass(), "d", true);
            worldHeight.setAccessible(true);
            worldHeight.setBoolean(worldInfo.worldProvider, heightValue);

            Field chunkMapGenerator = ReflectionHelper.getField(worldInfo.chunkServer.playerChunkMap.getClass(),
                    "chunkGenerator", true);
            chunkMapGenerator.setAccessible(true);
            ReflectionHelper.setFinal(chunkMapGenerator, worldInfo.chunkServer.playerChunkMap, generator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
