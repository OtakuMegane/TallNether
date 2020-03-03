package com.minefit.xerxestireiron.tallnether.v1_13_R2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.minefit.xerxestireiron.tallnether.Messages;

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

    public LoadHell(World world, ConfigurationSection worldConfig, String pluginName) {
        // Add vanilla values
        this.configAccessor.addConfig(null, new ConfigValues(null, worldConfig, new PaperSpigot().getSettingsMap()));
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
                new ConfigValues(worldName, worldConfig, new PaperSpigot(worldName).getSettingsMap()));
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
                BiomeLayout.b.a(BiomeLayout.b.b().a(Biomes.j)), generatorsettingsnether, worldInfo.configValues);

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
            Field chunkGenerator = getField(worldInfo.chunkServer.getClass(), "chunkGenerator", true);
            chunkGenerator.setAccessible(true);
            setFinal(chunkGenerator, worldInfo.chunkServer, generator);

            Field worldHeight = getField(worldInfo.worldProvider.getClass(), "d", true);
            worldHeight.setAccessible(true);
            worldHeight.setBoolean(worldInfo.worldProvider, heightValue);

            Field scheduler = getField(worldInfo.chunkServer.getClass(), "chunkScheduler", true);
            scheduler.setAccessible(true);
            ChunkTaskScheduler taskScheduler = (ChunkTaskScheduler) scheduler.get(worldInfo.chunkServer);

            Field schedulerGenerator = getField(taskScheduler.getClass(), "d", true);
            scheduler.setAccessible(true);
            setFinal(schedulerGenerator, taskScheduler, generator);
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
