package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.GeneratorAccess;
import net.minecraft.server.v1_14_R1.WorldGenFeatureChanceDecoratorCountConfiguration;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TallNether_WorldGenDecoratorNetherHeight extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenFeatureChanceDecoratorCountConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String blockType;

    public TallNether_WorldGenDecoratorNetherHeight(Function<Dynamic<?>, ? extends WorldGenFeatureChanceDecoratorCountConfiguration> function, String blockType) {
        super(function);
        this.blockType = blockType;
    }

    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, Random random, WorldGenFeatureChanceDecoratorCountConfiguration worldgenfeaturechancedecoratorcountconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        int attempts;
        int innerRand;
        int outerRand;

        if(this.blockType.equals("quartz")) {
            attempts = this.configAccessor.getConfig(worldName).quartzAttempts;
            innerRand = this.configAccessor.getConfig(worldName).quartzMaxHeight - this.configAccessor.getConfig(worldName).quartzMaxMinus;
            outerRand = this.configAccessor.getConfig(worldName).quartzMinHeight;
        } else if(this.blockType.equals("glowstone")) {
            attempts = this.configAccessor.getConfig(worldName).glowstone2Attempts;
            innerRand = this.configAccessor.getConfig(worldName).glowstone2MaxHeight - this.configAccessor.getConfig(worldName).glowstone2MaxMinus;
            outerRand = this.configAccessor.getConfig(worldName).glowstone2MinHeight;
        } else if(this.blockType.equals("hidden-lava")) {
            attempts = this.configAccessor.getConfig(worldName).hiddenLavaAttempts;
            innerRand = this.configAccessor.getConfig(worldName).hiddenLavaMaxHeight - this.configAccessor.getConfig(worldName).hiddenLavaMaxMinus;
            outerRand = this.configAccessor.getConfig(worldName).hiddenLavaMinHeight;
        } else if(this.blockType.equals("lavafall")) {
            attempts = this.configAccessor.getConfig(worldName).lavafallAttempts;
            innerRand = this.configAccessor.getConfig(worldName).lavafallMaxHeight - this.configAccessor.getConfig(worldName).lavafallMaxMinus;
            outerRand = this.configAccessor.getConfig(worldName).lavafallMinHeight;
        } else {
            attempts = worldgenfeaturechancedecoratorcountconfiguration.a;
            innerRand = worldgenfeaturechancedecoratorcountconfiguration.d - worldgenfeaturechancedecoratorcountconfiguration.c;
            outerRand = worldgenfeaturechancedecoratorcountconfiguration.b;
        }

        innerRand = innerRand > 0 ? innerRand : 1;
        outerRand = outerRand > 0 ? outerRand : 1;

        return a(random, worldgenfeaturechancedecoratorcountconfiguration, blockposition, attempts, innerRand, outerRand);
    }

    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorCountConfiguration worldgenfeaturechancedecoratorcountconfiguration, BlockPosition blockposition, int attempts, int innerRand, int outerRand) {
        return IntStream.range(0, attempts).mapToObj((i) -> {
            int j = random.nextInt(16);
            int k = random.nextInt(innerRand) + outerRand;
            int l = random.nextInt(16);

            return blockposition.b(j, k, l);
        });
    }

    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorCountConfiguration worldgenfeaturechancedecoratorcountconfiguration, BlockPosition blockposition) {
        return IntStream.range(0, worldgenfeaturechancedecoratorcountconfiguration.a).mapToObj((i) -> {
            int j = random.nextInt(16);
            int k = random.nextInt(worldgenfeaturechancedecoratorcountconfiguration.d - worldgenfeaturechancedecoratorcountconfiguration.c) + worldgenfeaturechancedecoratorcountconfiguration.b;
            int l = random.nextInt(16);

            return blockposition.b(j, k, l);
        });
    }
}
