package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_16_R1.Biomes;
import net.minecraft.server.v1_16_R1.Blocks;
import net.minecraft.server.v1_16_R1.ChunkGenerator;
import net.minecraft.server.v1_16_R1.GeneratorSettingBase;
import net.minecraft.server.v1_16_R1.WorldChunkManager;

public class LoadHell {
    private final Messages messages;
    private final Decorators decorators;
    private final RegisterFortress registerFortress = new RegisterFortress();
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final HashMap<String, WorldInfo> worldInfos;

    public LoadHell(ConfigurationSection worldConfig, String pluginName) {
        this.messages = new Messages(pluginName);
        this.decorators = new Decorators();
        this.worldInfos = new HashMap<>();
        this.configAccessor.addConfig(null, new ConfigValues(null, worldConfig, new PaperSpigot().getSettingsMap()));
        // Because of how we modify the vanilla generator, we need vanilla settings even for worlds not enabled
        this.configAccessor.setVanillaConfig(new PaperSpigot().getSettingsMap());
    }

    public boolean overrideDecorators() {
        return new DecoratorsNetherWastes().set();
        //return this.decorators.set();
    }

    public boolean restoreDecorators() {
        return this.decorators.restore();
    }

    public boolean registerFortress() {
        return this.registerFortress.set(false);
    }

    public boolean restoreFortress() {
        return this.registerFortress.set(true);
    }

    public void addWorld(World world, ConfigurationSection worldConfig) {
        String worldName = world.getName();
        this.configAccessor.newWorldConfig(worldName, new PaperSpigot().getSettingsMap(), true);
        this.configAccessor.addConfig(worldName,
                new ConfigValues(worldName, worldConfig, new PaperSpigot(worldName).getSettingsMap()));
        this.worldInfos.putIfAbsent(worldName, new WorldInfo(world));
    }

    public void overrideGenerator(World world) {
        String worldName = world.getName();
        WorldInfo worldInfo = this.worldInfos.get(worldName);
        WorldChunkManager chunkManager = worldInfo.chunkServer.chunkGenerator.getWorldChunkManager();
        ChunkGenerator chunkGen = worldInfo.chunkServer.chunkGenerator;
        TallNether_ChunkGenerator generator = null;

        try {
            Field wField = ReflectionHelper.getField(chunkGen.getClass(), "w", true);
            wField.setAccessible(true);
            long i = wField.getLong(chunkGen);
            Field hField = ReflectionHelper.getField(chunkGen.getClass(), "h", true);
            hField.setAccessible(true);
            GeneratorSettingBase h;
            h = (GeneratorSettingBase) hField.get(chunkGen);
            generator = new TallNether_ChunkGenerator(worldInfo.nmsWorld, chunkManager, i, h);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Field chunkGenerator = ReflectionHelper.getField(worldInfo.chunkServer.getClass(), "chunkGenerator", true);
            chunkGenerator.setAccessible(true);
            ReflectionHelper.setFinal(chunkGenerator, worldInfo.chunkServer, generator);

            Field chunkMapGenerator = ReflectionHelper.getField(worldInfo.chunkServer.playerChunkMap.getClass(),
                    "chunkGenerator", true);
            chunkMapGenerator.setAccessible(true);
            ReflectionHelper.setFinal(chunkMapGenerator, worldInfo.chunkServer.playerChunkMap, generator);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get i and generatorsettingbase from fields w and h
        /*
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

        // Seems to now be an initialized DimensionManager ?
        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(worldInfo.nmsWorld,
                BiomeLayout.b.a(BiomeLayout.b.a(worldInfo.nmsWorld.getWorldData()).a(Biomes.NETHER)),
                generatorsettingsnether, this.configAccessor.getConfig(worldName));

        if (setGenerator(worldInfo, tallNetherGenerator, false)) {
            this.messages.enableSuccess(worldName);
        } else {
            this.messages.enableFailed(worldName);
        }*/
    }

    public boolean restoreGenerator(World world) {
        WorldInfo worldInfo = this.worldInfos.get(world.getName());
        return true;
        //return this.setGenerator(worldInfo, worldInfo.originalGenerator, true);
    }

    private boolean isRecognizedGenerator(Environment environment, String originalGenName) {
        if (environment == Environment.NETHER) {
            return originalGenName.equals("ChunkProviderHell") || originalGenName.equals("TimedChunkGenerator");
        }

        return false;
    }

    /*private boolean setGenerator(WorldInfo worldInfo, ChunkGenerator<?> generator, boolean heightValue) {
        try {
            Field chunkGenerator = ReflectionHelper.getField(worldInfo.chunkServer.getClass(), "chunkGenerator", true);
            chunkGenerator.setAccessible(true);
            ReflectionHelper.setFinal(chunkGenerator, worldInfo.chunkServer, generator);

            // Probably merged into DimensionManager logicalHeight
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
    }*/
}
