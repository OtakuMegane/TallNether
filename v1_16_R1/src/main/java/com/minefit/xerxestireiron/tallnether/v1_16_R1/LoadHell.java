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

public class LoadHell {
    private final Messages messages;
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final HashMap<String, WorldInfo> worldInfos;
    private boolean decoratorsDone = false;
    private final boolean isPaper;

    public LoadHell(ConfigurationSection worldConfig, boolean isPaper, String pluginName) {
        this.messages = new Messages(pluginName);
        this.worldInfos = new HashMap<>();
        this.isPaper = isPaper;
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
        this.configAccessor.newWorldConfig(worldName, new PaperSpigot(worldName, this.isPaper).settingsMap, true);
        this.worldInfos.putIfAbsent(worldName, new WorldInfo(world));
    }

    public void removeWorld(World world) {
        this.worldInfos.remove(world.getName());
    }

    public void overrideGenerator(World world) {
        String worldName = world.getName();
        WorldInfo worldInfo = this.worldInfos.get(worldName);

        Environment environment = world.getEnvironment();

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(worldName, environment.toString());
            return;
        }

        if (worldInfo.originalGenName.equals("TallNether_ChunkGeneratorAbstract")
                || worldInfo.originalGenName.equals("TallNether_ChunkGeneratorAbstract_Paper")) {
            this.messages.alreadyEnabled(worldName);
            return;
        }

        if (!isRecognizedGenerator(environment, worldInfo.originalGenName)) {
            this.messages.unknownGenerator(worldName, worldInfo.originalGenName);
            return;
        }

        try {
            Field hField = ReflectionHelper.getField(worldInfo.originalGenerator.getClass(), "h", true);
            hField.setAccessible(true);
            GeneratorSettingBase h;
            h = (GeneratorSettingBase) hField.get(worldInfo.originalGenerator);

            if (this.isPaper) {
                if (setGenerator(worldInfo, new TallNether_ChunkGeneratorAbstract_Paper(worldInfo.nmsWorld,
                        worldInfo.originalChunkManager, worldInfo.nmsWorld.getSeed(), h), false)) {
                    this.messages.enableSuccess(worldName);
                } else {
                    this.messages.enableFailed(worldName);
                }
            } else {
                if (setGenerator(worldInfo, new TallNether_ChunkGeneratorAbstract(worldInfo.nmsWorld,
                        worldInfo.originalChunkManager, worldInfo.nmsWorld.getSeed(), h), false)) {
                    this.messages.enableSuccess(worldName);
                } else {
                    this.messages.enableFailed(worldName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

            if (heightValue) {
                Field logicalHeight = ReflectionHelper.getField(worldInfo.dimensionManager.getClass(), "logicalHeight",
                        true);
                logicalHeight.setAccessible(true);
                logicalHeight.setInt(worldInfo.dimensionManager, 256);
            }

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
