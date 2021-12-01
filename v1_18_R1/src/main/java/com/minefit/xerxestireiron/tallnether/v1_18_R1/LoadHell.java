package com.minefit.xerxestireiron.tallnether.v1_18_R1;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.Messages;
import com.minefit.xerxestireiron.tallnether.ReflectionHelper;
import com.minefit.xerxestireiron.tallnether.WorldConfig;

import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSampler;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.NoiseSlider;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom.Algorithm;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

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

        /*if (worldInfo.originalGenName.equals("TallNether_ChunkGeneratorAbstract")
                || worldInfo.originalGenName.equals("TallNether_ChunkGeneratorAbstract_Paper")) {
            this.messages.alreadyEnabled(worldName);
            return;
        }*/

        if (!isRecognizedGenerator(environment, worldInfo.originalGenName)) {
            this.messages.unknownGenerator(worldName, worldInfo.originalGenName);
            return;
        }

        ChunkGenerator originalGenerator = worldInfo.originalGenerator;
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);
        int minY = 0;
        int height = 128; // When set to 256, we get out of bounds 31 in 17; check what's causing this

        try {
            NoiseBasedChunkGenerator noiseBasedChunkGenerator = (NoiseBasedChunkGenerator) originalGenerator;
            NoiseGeneratorSettings noiseGeneratorSettings = noiseBasedChunkGenerator.settings.get();
            NoiseSettings noiseSettings = noiseGeneratorSettings.noiseSettings();
            Sampler sampler = noiseBasedChunkGenerator.climateSampler();

            Field dimHeight = ReflectionHelper.getField(worldInfo.dimensionType.getClass(), "I", true); // height -> I
            ReflectionHelper.fieldSetter(dimHeight, worldInfo.dimensionType, 256);

            Field logicalHeight = ReflectionHelper.getField(worldInfo.dimensionType.getClass(), "J", true); // logicalHeight -> J
            ReflectionHelper.fieldSetter(logicalHeight, worldInfo.dimensionType, 256);

            Field seaLevel = ReflectionHelper.getField(noiseGeneratorSettings.getClass(), "p", true); // seaLevel -> p
            ReflectionHelper.fieldSetter(seaLevel, noiseGeneratorSettings, worldConfig.getWorldValues().lavaSeaLevel);

            NoiseSettings newNoiseSettings = new NoiseSettings(minY, height, new NoiseSamplingSettings(1.0D, 3.0D, 80.0D, 60.0D),
                    new NoiseSlider(0.9375D, 3, 0), new NoiseSlider(2.5D, 4, -1), 1, 2, false, false, false,
                    TerrainProvider.nether());
            Field l = ReflectionHelper.getField(noiseGeneratorSettings.getClass(), "l", true); // noiseSettings -> l
            ReflectionHelper.fieldSetter(l, noiseGeneratorSettings, newNoiseSettings);

            /*Field noiseMinY = ReflectionHelper.getField(NoiseSettings.class, "b", true); // minY -> b
            ReflectionHelper.fieldSetter(noiseMinY, noiseSettings, minY);

            Field noiseHeight = ReflectionHelper.getField(NoiseSettings.class, "c", true); // height -> c
            ReflectionHelper.fieldSetter(noiseHeight, noiseSettings, height);*/

            /*Field genHeight = ReflectionHelper.getField(originalGenerator.getClass(), "t", true); // height -> t ???
            ReflectionHelper.fieldSetter(genHeight, originalGenerator, 256);*/

            // Test first if this is still needed (only occurs in NoiseChunk)
            /*int cellCountY = 256 / QuartPos.b(noiseSettings.g());
            Field genCellCountY = ReflectionHelper.getField(originalGenerator.getClass(), "m", true); // cellCountY -> m ???
            ReflectionHelper.fieldSetter(genCellCountY, originalGenerator, 256 / QuartPos.b(noiseSettings.g()));

            Field samplerCellCountY = ReflectionHelper.getField(sampler.getClass(), "f", true); // cellCountY -> f ???
            ReflectionHelper.fieldSetter(samplerCellCountY, sampler, cellCountY);*/

            enableFarLands(world, originalGenerator, environment);

            this.messages.enableSuccess(worldName);
        } catch (Throwable t) {
            t.printStackTrace();
            this.messages.enableFailed(worldName);
        }
    }

    // Borrowed from FarLandsAgain
    private void enableFarLands(World world, ChunkGenerator generator, Environment environment) {
        String worldName = world.getName();
        WorldInfo worldInfo = this.worldInfos.get(worldName);
        int divisor = (environment == Environment.THE_END) ? 8 : 1;
        NoiseBasedChunkGenerator noiseBasedChunkGenerator = (NoiseBasedChunkGenerator) worldInfo.originalGenerator;
        Algorithm al = noiseBasedChunkGenerator.settings.get().getRandomSource();
        // This is as good as we got for now. Still not sure it matters given how it's used.
        RandomSource randomSource = al.newInstance(worldInfo.nmsWorld.getSeed());
        NoiseSamplingSettings noiseSamplingSettings = noiseBasedChunkGenerator.settings.get().noiseSettings()
                .noiseSamplingSettings();
        NoiseSampler noiseSampler = (NoiseSampler) noiseBasedChunkGenerator.climateSampler();

        try {
            Field blendedNoiseField = ReflectionHelper.getField(noiseSampler.getClass(), "q", true);
            blendedNoiseField.setAccessible(true);
            BlendedNoise blendedNoise = (BlendedNoise) blendedNoiseField.get(noiseSampler);
            String blendedNoiseName = blendedNoise.getClass().getSimpleName();

            if (blendedNoiseName.equals("BlendedNoise")) {
                Field minLimitNoiseField = ReflectionHelper.getField(blendedNoise.getClass(), "a", true);
                minLimitNoiseField.setAccessible(true);
                PerlinNoise minLimitNoise = (PerlinNoise) minLimitNoiseField.get(blendedNoise);

                Field maxLimitNoiseField = ReflectionHelper.getField(blendedNoise.getClass(), "b", true);
                maxLimitNoiseField.setAccessible(true);
                PerlinNoise maxLimitNoise = (PerlinNoise) maxLimitNoiseField.get(blendedNoise);

                Field mainNoiseField = ReflectionHelper.getField(blendedNoise.getClass(), "c", true);
                mainNoiseField.setAccessible(true);
                PerlinNoise mainNoise = (PerlinNoise) mainNoiseField.get(blendedNoise);

                Field cellWidthField = ReflectionHelper.getField(blendedNoise.getClass(), "h", true);
                cellWidthField.setAccessible(true);
                int cellWidth = cellWidthField.getInt(blendedNoise);

                Field cellHeightField = ReflectionHelper.getField(blendedNoise.getClass(), "i", true);
                cellHeightField.setAccessible(true);
                int cellHeight = cellHeightField.getInt(blendedNoise);

                TallNether_BlendedNoise newBlendedNoise = new TallNether_BlendedNoise(minLimitNoise, maxLimitNoise, mainNoise,
                        noiseSamplingSettings, cellWidth, cellHeight, randomSource, this.configAccessor.getWorldConfig(worldName), divisor);
                ReflectionHelper.fieldSetter(blendedNoiseField, noiseSampler, newBlendedNoise);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public boolean restoreGenerator(World world) {
        return true;
    }

    private boolean isRecognizedGenerator(Environment environment, String originalGenName) {
        if (environment == Environment.NETHER) {
            return originalGenName.equals("ChunkGeneratorAbstract");
        }

        return false;
    }
}
