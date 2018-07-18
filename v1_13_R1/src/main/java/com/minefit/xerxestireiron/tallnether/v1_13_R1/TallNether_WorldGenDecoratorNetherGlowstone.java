package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.ChunkGenerator;
import net.minecraft.server.v1_13_R1.GeneratorAccess;
import net.minecraft.server.v1_13_R1.GeneratorSettings;
import net.minecraft.server.v1_13_R1.WorldGenDecorator;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenerator;

public class TallNether_WorldGenDecoratorNetherGlowstone extends WorldGenDecorator<WorldGenDecoratorFrequencyConfiguration> {

    private final ConfigurationSection worldConfig;
    
    public TallNether_WorldGenDecoratorNetherGlowstone(ConfigurationSection worldConfig) {
        this.worldConfig = worldConfig;
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, WorldGenerator<C> worldgenerator, C c0) {
        for (int i = 0; i < random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a) + 1); ++i) {
            int j = random.nextInt(16);
            int k = random.nextInt(248) + 4;
            int l = random.nextInt(16);

            worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(j, k, l), c0);
        }

        return true;
    }
}
