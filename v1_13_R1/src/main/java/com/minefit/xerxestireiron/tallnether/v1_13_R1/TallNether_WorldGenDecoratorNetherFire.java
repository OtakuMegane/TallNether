package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;

import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.ChunkGenerator;
import net.minecraft.server.v1_13_R1.GeneratorAccess;
import net.minecraft.server.v1_13_R1.GeneratorSettings;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorNetherFire;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenerator;

public class TallNether_WorldGenDecoratorNetherFire extends WorldGenDecoratorNetherFire {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenDecoratorNetherFire() {
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, WorldGenerator<C> worldgenerator, C c0) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();

        for (int i = 0; i < random.nextInt(random.nextInt(this.configAccessor.getConfig(worldName).fireAttempts) + 1) + 1; ++i) {
            int j = random.nextInt(16);
            int k = random.nextInt(this.configAccessor.getConfig(worldName).fireMaxHeight + 1) + this.configAccessor.getConfig(worldName).fireMinHeight;
            int l = random.nextInt(16);

            k = (k > 256) ? 256 : k;
            worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(j, k, l), c0);
        }

        return true;
    }
}