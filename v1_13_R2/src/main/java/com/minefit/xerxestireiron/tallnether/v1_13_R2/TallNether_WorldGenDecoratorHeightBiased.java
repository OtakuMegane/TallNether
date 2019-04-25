package com.minefit.xerxestireiron.tallnether.v1_13_R2;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.ChunkGenerator;
import net.minecraft.server.v1_13_R2.GeneratorAccess;
import net.minecraft.server.v1_13_R2.GeneratorSettings;
import net.minecraft.server.v1_13_R2.WorldGenDecorator;
import net.minecraft.server.v1_13_R2.WorldGenFeatureChanceDecoratorCountConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenerator;

public class TallNether_WorldGenDecoratorHeightBiased extends WorldGenDecorator<WorldGenFeatureChanceDecoratorCountConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String blockType;

    public TallNether_WorldGenDecoratorHeightBiased(String blockType) {
        this.blockType = blockType;
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator, Random random, BlockPosition blockposition, WorldGenFeatureChanceDecoratorCountConfiguration worldgenfeaturechancedecoratorcountconfiguration, WorldGenerator<C> worldgenerator, C c0) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();

        if(this.blockType.equals("quartz")) {
            int innerRand = this.configAccessor.getConfig(worldName).quartzMaxHeight - this.configAccessor.getConfig(worldName).quartzMaxMinus;
            innerRand = innerRand > 0 ? innerRand : 1;
            int outerRand = this.configAccessor.getConfig(worldName).quartzMinHeight;
            outerRand = outerRand > 0 ? outerRand : 1;

            for (int i = 0; i < this.configAccessor.getConfig(worldName).quartzAttempts; ++i) {
                int j = random.nextInt(16);
                int k = random.nextInt(random.nextInt(innerRand) + outerRand);
                int l = random.nextInt(16);

                worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(j, k, l), c0);
            }
        } else if(this.blockType.equals("glowstone")) {
            int innerRand = this.configAccessor.getConfig(worldName).glowstone2MaxHeight - this.configAccessor.getConfig(worldName).glowstone2MaxMinus;
            innerRand = innerRand > 0 ? innerRand : 1;
            int outerRand = this.configAccessor.getConfig(worldName).glowstone2MinHeight;
            outerRand = outerRand > 0 ? outerRand : 1;

            for (int i = 0; i < this.configAccessor.getConfig(worldName).glowstone2Attempts; ++i) {
                int j = random.nextInt(16);
                int k = random.nextInt(random.nextInt(innerRand) + outerRand);
                int l = random.nextInt(16);

                worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(j, k, l), c0);
            }
        } else if(this.blockType.equals("hidden-lava")) {
            int innerRand = this.configAccessor.getConfig(worldName).hiddenLavaMaxHeight - this.configAccessor.getConfig(worldName).hiddenLavaMaxMinus;
            innerRand = innerRand > 0 ? innerRand : 1;
            int outerRand = this.configAccessor.getConfig(worldName).hiddenLavaMinHeight;
            outerRand = outerRand > 0 ? outerRand : 1;

            for (int i = 0; i < this.configAccessor.getConfig(worldName).hiddenLavaAttempts; ++i) {
                int j = random.nextInt(16);
                int k = random.nextInt(random.nextInt(innerRand) + outerRand);
                int l = random.nextInt(16);

                worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(j, k, l), c0);
            }
        } else if(this.blockType.equals("lavafall")) {
            int innerRand = this.configAccessor.getConfig(worldName).lavafallMaxHeight - this.configAccessor.getConfig(worldName).lavafallMaxMinus;
            innerRand = innerRand > 0 ? innerRand : 1;
            int outerRand = this.configAccessor.getConfig(worldName).lavafallMinHeight;
            outerRand = outerRand > 0 ? outerRand : 1;

            for (int i = 0; i < this.configAccessor.getConfig(worldName).lavafallAttempts; ++i) {
                int j = random.nextInt(16);
                int k = random.nextInt(random.nextInt(innerRand) + outerRand);
                int l = random.nextInt(16);

                worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(j, k, l), c0);
            }
        } else {
            for (int i = 0; i < worldgenfeaturechancedecoratorcountconfiguration.a; ++i) {
                int j = random.nextInt(16);
                int k = random.nextInt(random.nextInt(worldgenfeaturechancedecoratorcountconfiguration.d - worldgenfeaturechancedecoratorcountconfiguration.c) + worldgenfeaturechancedecoratorcountconfiguration.b);
                int l = random.nextInt(16);

                worldgenerator.generate(generatoraccess, chunkgenerator, random, blockposition.a(j, k, l), c0);
            }
        }


        return true;
    }
}
