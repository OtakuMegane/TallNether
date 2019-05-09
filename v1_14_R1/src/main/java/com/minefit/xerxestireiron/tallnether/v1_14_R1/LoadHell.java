package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import java.lang.reflect.Field;

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
import net.minecraft.server.v1_14_R1.WorldProvider;
import net.minecraft.server.v1_14_R1.WorldServer;

public class LoadHell {
    private final World world;
    private final WorldServer nmsWorld;
    private final String worldName;
    private String originalGenName;
    private WorldProvider worldProvider;
    private final Messages messages;
    private ChunkGenerator<?> originalGenerator;
    private ChunkProviderServer chunkServer;
    private boolean enabled = false;
    public final ConfigValues configValues;
    private final Decorators decorators;
    private final PaperSpigot paperSpigot;
    private final ConfigAccessor configAccessor = new ConfigAccessor();

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
        this.worldProvider = this.nmsWorld.worldProvider;
        this.decorators = new Decorators(this.configValues);
        overrideGenerator();

        if (this.enabled) {
            this.messages.enableSuccess(this.worldName);
        } else {
            this.messages.enableFailed(this.worldName);
        }
    }

    public void restoreGenerator() {
        if (this.enabled) {
            if (!setGenerator(this.originalGenerator, true) || !this.decorators.restore()) {
                this.messages.restoreFailed(this.worldName);
            }

            this.enabled = false;
        }
    }

    public void overrideGenerator() {
        GeneratorSettingsNether generatorsettingsnether = new TallNether_GeneratorSettingsNether();
        generatorsettingsnether.a(Blocks.NETHERRACK.getBlockData());
        generatorsettingsnether.b(Blocks.LAVA.getBlockData());
        Environment environment = this.world.getEnvironment();

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

        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(this.nmsWorld,
                BiomeLayout.b.a(BiomeLayout.b.a().a(Biomes.NETHER)), generatorsettingsnether, this.configValues);
        this.decorators.initialize();
        this.enabled = setGenerator(tallNetherGenerator, false);
    }

    private boolean isRecognizedGenerator(Environment environment, String originalGenName) {
        if (environment == Environment.NETHER) {
            return originalGenName.equals("ChunkProviderHell") || originalGenName.equals("TimedChunkGenerator");
        }

        return false;
    }

    private boolean setGenerator(ChunkGenerator<?> generator, boolean heightValue) {
        try {
            Field chunkGenerator = ReflectionHelper.getField(this.chunkServer.getClass(), "chunkGenerator", true);
            chunkGenerator.setAccessible(true);
            ReflectionHelper.setFinal(chunkGenerator, this.chunkServer, generator);

            Field worldHeight = ReflectionHelper.getField(this.worldProvider.getClass(), "d", true);
            worldHeight.setAccessible(true);
            worldHeight.setBoolean(this.worldProvider, heightValue);

            Field chunkMapGenerator = ReflectionHelper.getField(this.chunkServer.playerChunkMap.getClass(), "chunkGenerator", true);
            chunkMapGenerator.setAccessible(true);
            ReflectionHelper.setFinal(chunkMapGenerator, this.chunkServer.playerChunkMap, generator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
