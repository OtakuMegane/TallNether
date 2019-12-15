package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_14_R1.BiomeLayout;
import net.minecraft.server.v1_14_R1.Biomes;
import net.minecraft.server.v1_14_R1.Blocks;
import net.minecraft.server.v1_14_R1.ChunkGenerator;
import net.minecraft.server.v1_14_R1.ChunkProviderServer;
import net.minecraft.server.v1_14_R1.GeneratorSettingsNether;
import net.minecraft.server.v1_14_R1.WorldServer;

public class LoadHell {
    private final World world;
    private final WorldServer nmsWorld;
    private final String worldName;
    private String originalGenName;
    private final Messages messages;
    private ChunkGenerator<?> originalGenerator;
    private ChunkProviderServer chunkServer;
    public final ConfigValues configValues;
    private final Decorators decorators;
    private final PaperSpigot paperSpigot;
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final HashMap<String, WorldInfo> worldInfo;

    public LoadHell(World world, ConfigurationSection worldConfig, String pluginName) {
        this.world = world;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        this.worldName = this.world.getName();
        this.paperSpigot = new PaperSpigot(this.worldName, false);
        this.configValues = new ConfigValues(this.worldName, worldConfig, this.paperSpigot.getSettingsMap());
        this.configAccessor.addConfig(null, new ConfigValues(null, worldConfig, this.paperSpigot.getSettingsMap()));
        this.configAccessor.addConfig(worldName, this.configValues);
        this.messages = new Messages(pluginName);
        this.chunkServer = (ChunkProviderServer) this.nmsWorld.getChunkProvider();
        this.originalGenerator = this.chunkServer.getChunkGenerator();
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        this.decorators = new Decorators(this.configValues);
        this.worldInfo = new HashMap<>();
    }

    public boolean overrideDecorators() {
        return this.decorators.set();
    }

    public boolean restoreDecorators() {
        return this.decorators.restore();
    }

    public void overrideGenerator(World world) {
        WorldInfo worldInfo = this.worldInfo.get(world.getName());
        GeneratorSettingsNether generatorsettingsnether = new TallNether_GeneratorSettingsNether();
        generatorsettingsnether.a(Blocks.NETHERRACK.getBlockData());
        generatorsettingsnether.b(Blocks.LAVA.getBlockData());
        Environment environment = this.world.getEnvironment();

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(worldInfo.worldName, environment.toString());
            return;
        }

        if (this.originalGenName.equals("TallNether_ChunkProviderHell")) {
            this.messages.alreadyEnabled(worldInfo.worldName);
            return;
        }

        if (!isRecognizedGenerator(environment, worldInfo.originalGenName)) {
            this.messages.unknownGenerator(worldInfo.worldName, worldInfo.originalGenName);
            return;
        }

        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(worldInfo.nmsWorld,
                BiomeLayout.b.a(BiomeLayout.b.a().a(Biomes.NETHER)), generatorsettingsnether, worldInfo.configValues);

        if (setGenerator(worldInfo, tallNetherGenerator, false)) {
            this.messages.enableSuccess(worldInfo.worldName);
        } else {
            this.messages.enableFailed(worldInfo.worldName);
        }
    }

    public boolean restoreGenerator(World world) {
        WorldInfo worldInfo = this.worldInfo.get(world.getName());
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

            Field chunkMapGenerator = ReflectionHelper.getField(worldInfo.chunkServer.playerChunkMap.getClass(), "chunkGenerator", true);
            chunkMapGenerator.setAccessible(true);
            ReflectionHelper.setFinal(chunkMapGenerator, worldInfo.chunkServer.playerChunkMap, generator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
