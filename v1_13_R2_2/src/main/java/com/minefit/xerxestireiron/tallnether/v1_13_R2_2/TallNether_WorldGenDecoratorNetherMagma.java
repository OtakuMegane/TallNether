package com.minefit.xerxestireiron.tallnether.v1_13_R2_2;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.ChunkGenerator;
import net.minecraft.server.v1_13_R2.GeneratorAccess;
import net.minecraft.server.v1_13_R2.GeneratorSettings;
import net.minecraft.server.v1_13_R2.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenDecoratorNetherMagma;
import net.minecraft.server.v1_13_R2.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenerator;

public class TallNether_WorldGenDecoratorNetherMagma extends WorldGenDecoratorNetherMagma {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorNetherMagma() {
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, WorldGenerator<C> worldgenerator, C c0) {
        int i = generatoraccess.getSeaLevel() / 2 + 1;
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();

        for (int j = 0; j < this.configAccessor.getConfig(worldName).magmaAttempts; ++j) {
            int k = random.nextInt(16);
            int l = this.configAccessor.getConfig(worldName).magmaMinHeight + random.nextInt(this.configAccessor.getConfig(worldName).magmaRangeSize);
            int i1 = random.nextInt(16);

            worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(k, l, i1), c0);
        }

        return true;
    }
}