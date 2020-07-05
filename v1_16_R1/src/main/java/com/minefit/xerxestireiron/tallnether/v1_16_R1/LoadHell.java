package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.minefit.xerxestireiron.tallnether.Messages;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R1.Biomes;
import net.minecraft.server.v1_16_R1.Blocks;
import net.minecraft.server.v1_16_R1.ChunkGenerator;
import net.minecraft.server.v1_16_R1.DimensionManager;
import net.minecraft.server.v1_16_R1.GeneratorSettingBase;
import net.minecraft.server.v1_16_R1.GeneratorSettingBase.a;
import net.minecraft.server.v1_16_R1.IRegistry;
import net.minecraft.server.v1_16_R1.IRegistryCustom;
import net.minecraft.server.v1_16_R1.MinecraftKey;
import net.minecraft.server.v1_16_R1.StructureGenerator;
import net.minecraft.server.v1_16_R1.StructureSettings;
import net.minecraft.server.v1_16_R1.StructureSettingsFeature;
import net.minecraft.server.v1_16_R1.WorldChunkManager;
import net.minecraft.server.v1_16_R1.WorldChunkManagerMultiNoise;
import net.minecraft.server.v1_16_R1.WorldGenFeatureEmptyConfiguration;

public class LoadHell {
    private final Messages messages;
    private final RegisterFortress registerFortress = new RegisterFortress();
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final HashMap<String, WorldInfo> worldInfos;
    private final HashMap<String, Boolean> generatorsDone;
    private final StructureGenerator<WorldGenFeatureEmptyConfiguration> vanillaFortress = StructureGenerator.FORTRESS;
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

    public boolean registerFortress() {
        return true;
        //return this.registerFortress.set(false);
    }

    public boolean restoreFortress() {
        return true;
        //return this.registerFortress.set(true);
    }

    public void addWorld(World world, ConfigurationSection worldConfig) {
        String worldName = world.getName();
        this.configAccessor.newWorldConfig(worldName, new PaperSpigot().getSettingsMap(), true);
        this.worldInfos.putIfAbsent(worldName, new WorldInfo(world));
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

            // We'll come back for this
            /*for (java.util.Map.Entry<StructureGenerator<?>, StructureSettingsFeature> entry : h.a().a().entrySet()) {
                System.out.println("GENERATOR: " + entry.getKey());
            }
            h.a().a().remove(this.vanillaFortress);
            h.a().a().put(StructureGenerator.FORTRESS, new StructureSettingsFeature(27, 4, 30084232));

            Field h2Field = ReflectionHelper.getField(chunkGen.getClass(), "h", true);
            h2Field.setAccessible(true);
            GeneratorSettingBase h2;
            h2 = (GeneratorSettingBase) h2Field.get(chunkGen);
            for (java.util.Map.Entry<StructureGenerator<?>, StructureSettingsFeature> entry : h2.a().a().entrySet()) {
                System.out.println("GENERATOR: " + entry.getKey());
            }*/
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
