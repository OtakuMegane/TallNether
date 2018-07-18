package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.util.Random;

import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.ChunkGenerator;
import net.minecraft.server.v1_13_R1.GeneratorAccess;
import net.minecraft.server.v1_13_R1.GeneratorSettings;
import net.minecraft.server.v1_13_R1.HeightMap;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorChanceConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenDecoratorChanceHeight;
import net.minecraft.server.v1_13_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenerator;

public class TallNether_WorldGenDecoratorChanceHeight extends WorldGenDecoratorChanceHeight {

    public TallNether_WorldGenDecoratorChanceHeight() {}

    @Override
    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenDecoratorChanceConfiguration worldgendecoratorchanceconfiguration, WorldGenerator<C> worldgenerator, C c0) {
        if (random.nextFloat() < 1.0F / (float) worldgendecoratorchanceconfiguration.a) {
            int i = random.nextInt(16);
            int j = random.nextInt(16);
            int k = generatoraccess.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING, blockposition.a(i, 0, j)).getY();

            if (k <= 0) {
                return false;
            }

            int l = random.nextInt(k);

            worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(i, l, j), c0);
        }

        return true;
    }
}