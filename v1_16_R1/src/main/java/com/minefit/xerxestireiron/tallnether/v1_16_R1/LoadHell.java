package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_16_R1.ChunkGenerator;
import net.minecraft.server.v1_16_R1.GeneratorSettingBase;
import net.minecraft.server.v1_16_R1.WorldChunkManager;

public class LoadHell {
    private final Messages messages;
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final HashMap<String, WorldInfo> worldInfos;
    private final HashMap<String, Boolean> generatorsDone;
    private boolean decoratorsDone = false;

    public LoadHell(ConfigurationSection worldConfig, String pluginName) {
        this.messages = new Messages(pluginName);
        this.worldInfos = new HashMap<>();
        this.generatorsDone = new HashMap<>();
    }

    // Returns whether or not decorators were successfully overriden
    public boolean overrideDecorators(boolean check) {
        if (this.decoratorsDone) {
            return false;
        }

        this.decoratorsDone = (new DecoratorsNetherWastes().set() && new DecoratorsWarpedForest().set()
                && new DecoratorsCrimsonForest().set() && new DecoratorsBasaltDeltas().set()
                && new DecoratorsSoulSandValley().set());
        return this.decoratorsDone;
    }

    public boolean restoreDecorators() {
        return true;
    }

    public void addWorld(World world, ConfigurationSection worldConfig) {
        String worldName = world.getName();
        this.configAccessor.newWorldConfig(worldName, new PaperSpigot().getSettingsMap(), true);
        this.worldInfos.putIfAbsent(worldName, new WorldInfo(world));
    }

    public void removeWorld(World world) {
        this.worldInfos.remove(world.getName());
    }

    public void overrideGenerator(World world) {
        String worldName = world.getName();

        if (generatorsDone.containsKey(worldName)) {
            return;
        }

        WorldInfo worldInfo = this.worldInfos.get(worldName);
        Environment environment = world.getEnvironment();

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(worldName, environment.toString());
            return;
        }

        if (worldInfo.originalGenName.equals("TallNether_ChunkGenerator")) {
            this.messages.alreadyEnabled(worldName);
            return;
        }

        if (!isRecognizedGenerator(environment, worldInfo.originalGenName)) {
            this.messages.unknownGenerator(worldName, worldInfo.originalGenName);
            return;
        }

        WorldChunkManager chunkManager = worldInfo.chunkServer.chunkGenerator.getWorldChunkManager();
        ChunkGenerator chunkGen = worldInfo.chunkServer.chunkGenerator;
        TallNether_ChunkGenerator tallNetherGenerator = null;

        try {
            Field wField = ReflectionHelper.getField(chunkGen.getClass(), "w", true);
            wField.setAccessible(true);
            long i = wField.getLong(chunkGen);
            Field hField = ReflectionHelper.getField(chunkGen.getClass(), "h", true);
            hField.setAccessible(true);
            GeneratorSettingBase h;
            h = (GeneratorSettingBase) hField.get(chunkGen);
            tallNetherGenerator = new TallNether_ChunkGenerator(worldInfo.nmsWorld, chunkManager, i, h);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (setGenerator(worldInfo, tallNetherGenerator, false)) {
            this.generatorsDone.put(worldName, true);
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
            return originalGenName.equals("ChunkGeneratorAbstract") || originalGenName.equals("TimedChunkGenerator");
        }

        return false;
    }

    private boolean setGenerator(WorldInfo worldInfo, ChunkGenerator generator, boolean heightValue) {
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
            return false;
        }

        return true;
    }
}
