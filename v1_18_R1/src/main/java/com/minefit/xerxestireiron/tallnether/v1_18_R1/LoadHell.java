package com.minefit.xerxestireiron.tallnether.v1_18_R1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.Messages;
import com.minefit.xerxestireiron.tallnether.ReflectionHelper;
import com.minefit.xerxestireiron.tallnether.WorldConfig;

import net.minecraft.core.WritableRegistry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSampler;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
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

        if (!this.decoratorsModified) {
            // Disabled for the moment. Vanilla decorators work, but no modified ones do
            //this.decoratorsModified = overrideDecorators(worldInfo);
        }
    }

    public void removeWorld(World world) {
        this.worldInfos.remove(world.getName());
    }

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

        if (!isRecognizedGenerator(environment, worldInfo.originalGenName)) {
            this.messages.unknownGenerator(worldName, worldInfo.originalGenName);
            return;
        }

        ChunkGenerator originalGenerator = worldInfo.originalGenerator;
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);
        int minY = 0;
        int height = 256;
        int logicalHeight = 256;
        // When height set to 256, we get out of bounds 31 in 17 error but only on a new world
        // Existing worlds are fine, even after deleting region files
        // Has something to do with initial world creation. Maybe spawn finding?

        try {
            NoiseBasedChunkGenerator noiseBasedChunkGenerator = (NoiseBasedChunkGenerator) originalGenerator;
            NoiseGeneratorSettings noiseGeneratorSettings = noiseBasedChunkGenerator.settings.get();
            NoiseSettings noiseSettings = noiseGeneratorSettings.noiseSettings();
            NoiseSampler sampler = (NoiseSampler) noiseBasedChunkGenerator.climateSampler();
            DimensionType dimensionType = worldInfo.nmsWorld.dimensionType();

            NoiseSettings newNoiseSettings = new NoiseSettings(minY, height, noiseSettings.noiseSamplingSettings(),
                    noiseSettings.topSlideSettings(), noiseSettings.bottomSlideSettings(), 1, 2, false, false, false,
                    TerrainProvider.nether());

            Field H = ReflectionHelper.getField(dimensionType.getClass(), "H", true); // minY -> H
            ReflectionHelper.fieldSetter(H, dimensionType, minY);

            Field I = ReflectionHelper.getField(dimensionType.getClass(), "I", true); // height -> I
            ReflectionHelper.fieldSetter(I, dimensionType, height);

            Field J = ReflectionHelper.getField(dimensionType.getClass(), "J", true); // logicalHeight -> J
            ReflectionHelper.fieldSetter(J, dimensionType, logicalHeight);

            Field l = ReflectionHelper.getField(noiseGeneratorSettings.getClass(), "l", true); // noiseSettings -> l
            ReflectionHelper.fieldSetter(l, noiseGeneratorSettings, newNoiseSettings);

            Field n = ReflectionHelper.getField(sampler.getClass(), "n", true); // noiseSettings -> n
            ReflectionHelper.fieldSetter(n, sampler, newNoiseSettings);

            // Sets it as expected but has no effect on actual generated lava sea level
            Field seaLevel = ReflectionHelper.getField(noiseGeneratorSettings.getClass(), "p", true); // seaLevel -> p
            ReflectionHelper.fieldSetter(seaLevel, noiseGeneratorSettings, worldConfig.getWorldValues().lavaSeaLevel);

            //this.experimentals(worldInfo);
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

                TallNether_BlendedNoise newBlendedNoise = new TallNether_BlendedNoise(minLimitNoise, maxLimitNoise,
                        mainNoise, noiseSamplingSettings, cellWidth, cellHeight, randomSource,
                        this.configAccessor.getWorldConfig(worldName), divisor);
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

    // Various experimental attempts to make stuff work
    private void experimentals(WorldInfo worldInfo) {
        RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
        RuleSource GRAVEL = SurfaceRules.state(Blocks.GRAVEL.defaultBlockState());
        RuleSource LAVA = SurfaceRules.state(Blocks.LAVA.defaultBlockState());
        RuleSource NETHERRACK = SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
        RuleSource SOUL_SAND = SurfaceRules.state(Blocks.SOUL_SAND.defaultBlockState());
        RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
        RuleSource BASALT = SurfaceRules.state(Blocks.BASALT.defaultBlockState());
        RuleSource BLACKSTONE = SurfaceRules.state(Blocks.BLACKSTONE.defaultBlockState());
        RuleSource WARPED_WART_BLOCK = SurfaceRules.state(Blocks.WARPED_WART_BLOCK.defaultBlockState());
        RuleSource WARPED_NYLIUM = SurfaceRules.state(Blocks.WARPED_NYLIUM.defaultBlockState());
        RuleSource NETHER_WART_BLOCK = SurfaceRules.state(Blocks.NETHER_WART_BLOCK.defaultBlockState());
        RuleSource CRIMSON_NYLIUM = SurfaceRules.state(Blocks.CRIMSON_NYLIUM.defaultBlockState());

        ConditionSource var0 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(47), 0);
        ConditionSource var1 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(48), 0);
        ConditionSource var2 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(46), 0);
        ConditionSource var3 = SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(50), 0));
        ConditionSource var4 = SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0);
        ConditionSource var5 = SurfaceRules.hole();
        ConditionSource var6 = SurfaceRules.noiseCondition(Noises.SOUL_SAND_LAYER, -0.012D);
        ConditionSource var7 = SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -0.012D);
        ConditionSource var8 = SurfaceRules.noiseCondition(Noises.PATCH, -0.012D);
        ConditionSource var9 = SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.54D);
        ConditionSource var10 = SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17D);
        ConditionSource var11 = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0D);
        RuleSource var12 = SurfaceRules.ifTrue(var8, SurfaceRules.ifTrue(var2, SurfaceRules.ifTrue(var3, GRAVEL)));
        @SuppressWarnings("unchecked")
        RuleSource srule = SurfaceRules.sequence(new RuleSource[] { SurfaceRules.ifTrue(SurfaceRules.verticalGradient(
                "bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK), SurfaceRules.ifTrue(
                        SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5),
                                VerticalAnchor.top())),
                        BEDROCK),
                SurfaceRules.ifTrue(var4, NETHERRACK),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.BASALT_DELTAS }),
                        SurfaceRules.sequence(
                                new RuleSource[] { SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, BASALT),
                                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                                SurfaceRules.sequence(new RuleSource[] { var12,
                                                        SurfaceRules.ifTrue(var11, BASALT), BLACKSTONE })) })),
                SurfaceRules
                        .ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.SOUL_SAND_VALLEY }),
                                SurfaceRules
                                        .sequence(new RuleSource[] {
                                                SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING,
                                                        SurfaceRules.sequence(new RuleSource[] {
                                                                SurfaceRules.ifTrue(var11, SOUL_SAND), SOUL_SOIL })),
                                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                                        SurfaceRules.sequence(new RuleSource[] { var12,
                                                                SurfaceRules.ifTrue(var11, SOUL_SAND),
                                                                SOUL_SOIL })) })),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(new RuleSource[] {
                        SurfaceRules.ifTrue(SurfaceRules.not(var1), SurfaceRules.ifTrue(var5, LAVA)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.WARPED_FOREST }),
                                SurfaceRules.ifTrue(SurfaceRules.not(var9),
                                        SurfaceRules.ifTrue(var0, SurfaceRules.sequence(new RuleSource[] {
                                                SurfaceRules.ifTrue(var10, WARPED_WART_BLOCK), WARPED_NYLIUM })))),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.CRIMSON_FOREST }),
                                SurfaceRules.ifTrue(SurfaceRules.not(var9),
                                        SurfaceRules.ifTrue(var0, SurfaceRules.sequence(new RuleSource[] {
                                                SurfaceRules.ifTrue(var10, NETHER_WART_BLOCK), CRIMSON_NYLIUM })))) })),
                SurfaceRules
                        .ifTrue(SurfaceRules.isBiome(new ResourceKey[] { Biomes.NETHER_WASTES }),
                                SurfaceRules
                                        .sequence(new RuleSource[] {
                                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                                        SurfaceRules.ifTrue(var6,
                                                                SurfaceRules.sequence(new RuleSource[] {
                                                                        SurfaceRules.ifTrue(SurfaceRules.not(var5),
                                                                                SurfaceRules.ifTrue(
                                                                                        var2,
                                                                                        SurfaceRules.ifTrue(var3,
                                                                                                SOUL_SAND))),
                                                                        NETHERRACK }))),
                                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                                        SurfaceRules.ifTrue(var0,
                                                                SurfaceRules.ifTrue(var3, SurfaceRules.ifTrue(var7,
                                                                        SurfaceRules.sequence(new RuleSource[] {
                                                                                SurfaceRules.ifTrue(var1, GRAVEL),
                                                                                SurfaceRules.ifTrue(
                                                                                        SurfaceRules.not(var5),
                                                                                        GRAVEL) }))))) })),
                NETHERRACK });

        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldInfo.worldName);
        NoiseBasedChunkGenerator noiseBasedChunkGenerator = (NoiseBasedChunkGenerator) worldInfo.nmsWorld
                .getChunkSource().getGenerator();
        NoiseGeneratorSettings noiseGeneratorSettings = noiseBasedChunkGenerator.settings.get();
        NoiseSettings noiseSettings = noiseGeneratorSettings.noiseSettings();

        StructureSettings structureSettings = noiseBasedChunkGenerator.getSettings();
        NoiseSettings newNoiseSettings = new NoiseSettings(0, 256, noiseSettings.noiseSamplingSettings(),
                noiseSettings.topSlideSettings(), noiseSettings.bottomSlideSettings(), 1, 2, false, false, false,
                TerrainProvider.nether());

        try {
            Constructor<NoiseGeneratorSettings> newNGS = NoiseGeneratorSettings.class.getDeclaredConstructor(
                    StructureSettings.class, NoiseSettings.class, BlockState.class, BlockState.class, RuleSource.class,
                    int.class, boolean.class, boolean.class, boolean.class, boolean.class, boolean.class,
                    boolean.class);
            newNGS.setAccessible(true);
            NoiseGeneratorSettings newNoiseGen = newNGS.newInstance(structureSettings, newNoiseSettings,
                    Blocks.NETHERRACK.defaultBlockState(), Blocks.WATER.defaultBlockState(), srule,
                    worldConfig.getWorldValues().lavaSeaLevel, false, false, false, false, false, true);
            Supplier<NoiseGeneratorSettings> supplierNGS = () -> newNoiseGen;

            NoiseGeneratorSettings correct = BuiltinRegistries.NOISE_GENERATOR_SETTINGS
                    .get(NoiseGeneratorSettings.NETHER);
            OptionalInt ngsID = OptionalInt.of(BuiltinRegistries.NOISE_GENERATOR_SETTINGS.getId(correct));
            Optional<ResourceKey<NoiseGeneratorSettings>> ngsKey = BuiltinRegistries.NOISE_GENERATOR_SETTINGS
                    .getResourceKey(correct);

            WritableRegistry<NoiseGeneratorSettings> wReg = (WritableRegistry<NoiseGeneratorSettings>) BuiltinRegistries.NOISE_GENERATOR_SETTINGS;
            wReg.registerOrOverride(ngsID, ngsKey.get(), newNoiseGen,
                    BuiltinRegistries.NOISE_GENERATOR_SETTINGS.lifecycle(noiseGeneratorSettings));

            Field f = ReflectionHelper.getField(noiseBasedChunkGenerator.getClass(), "f", true); // settings -> f
            ReflectionHelper.fieldSetter(f, noiseBasedChunkGenerator, supplierNGS);

            // Blind guess, didn't help either
            //int j = worldConfig.getWorldValues().lavaSeaLevel;
            //Aquifer.FluidStatus tallnetherAquifer = new Aquifer.FluidStatus(j, noiseGeneratorSettings.getDefaultFluid());

            //Field globalFluidPicker = ReflectionHelper.getField(noiseGeneratorSettings.getClass(), "n", true); // globalFluidPicker -> n
            //ReflectionHelper.fieldSetter(globalFluidPicker, noiseBasedChunkGenerator, tallnetherAquifer);

            //Field rs = ReflectionHelper.getField(noiseGeneratorSettings.getClass(), "o", true); // surfaceRule -> o
            //ReflectionHelper.fieldSetter(rs, noiseGeneratorSettings, srule);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
