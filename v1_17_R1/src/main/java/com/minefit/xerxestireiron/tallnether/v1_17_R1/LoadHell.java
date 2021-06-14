package com.minefit.xerxestireiron.tallnether.v1_17_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.function.Supplier;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.Messages;
import com.minefit.xerxestireiron.tallnether.ReflectionHelper;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.PaperSpigot;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.TallNether_ChunkGeneratorAbstract;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.TallNether_ChunkGeneratorAbstract_Paper;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldInfo;

import net.minecraft.core.QuartPos;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.NoiseSampler;
import net.minecraft.world.level.levelgen.NoiseSettings;
import sun.misc.Unsafe;

public class LoadHell {
    private final Messages messages;
    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final HashMap<String, WorldInfo> worldInfos;
    private final boolean isPaper;
    private boolean decoratorsModified;

    public LoadHell(ConfigurationSection worldConfig, boolean isPaper, String pluginName) {
        this.messages = new Messages(pluginName);
        this.worldInfos = new HashMap<>();
        this.isPaper = isPaper;
    }

    public boolean overrideDecorators(WorldInfo worldInfo) {
        worldInfo.collectBiomeData();
        worldInfo.basaltDeltasModifier.modify();
        worldInfo.crimsonForestModifier.modify();
        worldInfo.warpedForestModifier.modify();
        worldInfo.netherWastesModifier.modify();
        worldInfo.soulSandValleyModifier.modify();
        return true;
    }

    public boolean restoreDecorators(WorldInfo worldInfo) {
        return true;
    }

    public void addWorld(World world, ConfigurationSection worldConfig) {
        Environment environment = world.getEnvironment();

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(world.getName(), environment.toString());
            return;
        }

        String worldName = world.getName();
        this.configAccessor.newWorldConfig(worldName, new PaperSpigot(worldName, this.isPaper).settingsMap, true);
        WorldInfo worldInfo = new WorldInfo(world);
        this.worldInfos.putIfAbsent(worldName, worldInfo);

        // For the moment Minecraft still shares a single biome instance for all worlds
        // We need the chunk manager to get them
        if (!this.decoratorsModified) {
            this.decoratorsModified = overrideDecorators(worldInfo);
        }
    }

    public void removeWorld(World world) {
        this.worldInfos.remove(world.getName());
    }

    @SuppressWarnings("unchecked")
    public void overrideGenerator(World world) {
        String worldName = world.getName();
        WorldInfo worldInfo = this.worldInfos.get(worldName);

        if (worldInfo.modified) {
            return;
        }

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


        //NoiseSamplingSettings noiseSamplingSettings = new NoiseSamplingSettings(1.0D, 3.0D, 80.0D, 60.0D);


        try {
            WorldServer nmsWorld = ((CraftWorld) world).getHandle();
            ChunkProviderServer chunkServer = (ChunkProviderServer) nmsWorld.getChunkProvider();
            ChunkGenerator originalGenerator = chunkServer.getChunkGenerator();

            Field gField = ReflectionHelper.getField(originalGenerator.getClass(), "g", true);
            gField.setAccessible(true);
            Supplier<GeneratorSettingBase> g = (Supplier<GeneratorSettingBase>) gField.get(originalGenerator); // settings -> g
            GeneratorSettingBase generatorsettingbase = (GeneratorSettingBase) g.get();
            NoiseSettings noisesettings = generatorsettingbase.b();
            Field uField = ReflectionHelper.getField(originalGenerator.getClass(), "u", true);
            uField.setAccessible(true);
            NoiseSampler sampler = (NoiseSampler) uField.get(originalGenerator);
            /*Field jField = ReflectionHelper.getField(sampler.getClass(), "j", true);
            jField.setAccessible(true);
            jField.set(sampler, noisegeneratoroctaves);
            Field dField = ReflectionHelper.getField(noisesettings.getClass(), "d", true); // noiseSamplingSettings -> d
            dField.setAccessible(true);
            ReflectionHelper.setFinal(dField, noisesettings, noiseSamplingSettings);*/

            // Basic height achieved, all decorators seem to scale
            // Caves modding not needed any more
            // only need to extend surfaces now
            // Experimental height
            DimensionManager dimensionManager = nmsWorld.getDimensionManager();
            Field GField = ReflectionHelper.getField(dimensionManager.getClass(), "G", true); // height -> G
            Field HField = ReflectionHelper.getField(dimensionManager.getClass(), "H", true); // logicalHeight -> H
            //Field mField = ReflectionHelper.getField(generatorsettingbase.getClass(), "m", true); // bedrockRoofPosition -> m
            Field oField = ReflectionHelper.getField(generatorsettingbase.getClass(), "o", true); // seaLevel -> o
            Field cField = ReflectionHelper.getField(noisesettings.getClass(), "c", true); // height -> c
            //Field hField = ReflectionHelper.getField(noisesettings.getClass(), "h", true); // noiseSizeVertical -> h
            Field tField = ReflectionHelper.getField(originalGenerator.getClass(), "t", true); // height -> t
            int cellCountY = 256 / QuartPos.b(noisesettings.g());
            Field mField2 = ReflectionHelper.getField(originalGenerator.getClass(), "m", true); // cellCountY -> m
            Field fField = ReflectionHelper.getField(sampler.getClass(), "f", true); // cellCountY -> f
            this.setFinalInt(GField, dimensionManager, 256);
            this.setFinalInt(HField, dimensionManager, 256);
            //this.setFinalInt(mField, generatorsettingbase, 256);
            this.setFinalInt(oField, generatorsettingbase, 48);
            this.setFinalInt(cField, noisesettings, 256);
            //this.setFinalInt(hField, noisesettings, 2);
            this.setFinalInt(tField, originalGenerator, 256);
            this.setFinalInt(mField2, originalGenerator, cellCountY);
            this.setFinalInt(fField, sampler, cellCountY);
            this.messages.enableSuccess(worldName);
        } catch (Exception e) {
            e.printStackTrace();
            this.messages.enableFailed(worldName);
        }



        /*try {
            Field hField = ReflectionHelper.getField(worldInfo.originalGenerator.getClass(), "h", true);
            hField.setAccessible(true);
            Supplier<GeneratorSettingBase> h;
            h = (Supplier<GeneratorSettingBase>) hField.get(worldInfo.originalGenerator);

            if (this.isPaper) {
                if (setGenerator(worldInfo, new TallNether_ChunkGeneratorAbstract_Paper(worldInfo.nmsWorld,
                        worldInfo.originalChunkManager, worldInfo.nmsWorld.getSeed(), h), false)) {
                    this.messages.enableSuccess(worldName);
                    worldInfo.modified = true;
                } else {
                    this.messages.enableFailed(worldName);
                }
            } else {
                if (setGenerator(worldInfo, new TallNether_ChunkGeneratorAbstract(worldInfo.nmsWorld,
                        worldInfo.originalChunkManager, worldInfo.nmsWorld.getSeed(), h), false)) {
                    this.messages.enableSuccess(worldName);
                    worldInfo.modified = true;
                } else {
                    this.messages.enableFailed(worldName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void setFinalInt(Field field, Object instance, int value) throws Exception
    {
        try {
            field.setAccessible(true);
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.setInt(instance, value);
        } catch (Exception e) {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                Unsafe unsafe = (Unsafe) theUnsafe.get(null);
                long offset = unsafe.objectFieldOffset(field);
                unsafe.putInt(instance, offset, value);
            } catch (Exception e1) {
                throw e1;
            }
        }
    }

    public boolean restoreGenerator(World world) {
        WorldInfo worldInfo = this.worldInfos.get(world.getName());
        return true;
        //return this.setGenerator(worldInfo, worldInfo.originalGenerator, true);
    }

    private boolean isRecognizedGenerator(Environment environment, String originalGenName) {
        if (environment == Environment.NETHER) {
            return originalGenName.equals("ChunkGeneratorAbstract") || originalGenName.equals("TimedChunkGenerator");
        }

        return false;
    }

    /*private boolean setGenerator(WorldInfo worldInfo, ChunkGenerator generator, boolean heightValue) {
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
    }*/
}
