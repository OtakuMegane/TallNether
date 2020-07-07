package com.minefit.xerxestireiron.tallnether.v1_15_R1;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.GeneratorAccess;
import net.minecraft.server.v1_15_R1.WorldGenFeatureChanceDecoratorCountConfiguration;

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

    // TallNether: Methods added to allow per-world config access through GeneratorAccess
    public Stream<BlockPosition> a(GeneratorAccess generatoraccess, Random random, WorldGenFeatureChanceDecoratorCountConfiguration worldgenfeaturechancedecoratorcountconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        ConfigValues worldConfig = this.configAccessor.getConfig(worldName);
        int attempts;
        int innerRand;
        int outerRand;

        if(this.blockType.equals("quartz")) {
            attempts = worldConfig.quartzAttempts;
            innerRand = worldConfig.quartzMaxHeight;
            outerRand = worldConfig.quartzMinHeight;
        } else if(this.blockType.equals("glowstone")) {
            attempts = worldConfig.glowstone2Attempts;
            innerRand = worldConfig.glowstone2MaxHeight;
            outerRand = worldConfig.glowstone2MinHeight;
        } else if(this.blockType.equals("hidden-lava")) {
            attempts = worldConfig.hiddenLavaAttempts;
            innerRand = worldConfig.hiddenLavaMaxHeight;
            outerRand = worldConfig.hiddenLavaMinHeight;
        } else if(this.blockType.equals("lavafall")) {
            attempts = worldConfig.lavafallAttempts;
            innerRand = worldConfig.lavafallMaxHeight;
            outerRand = worldConfig.lavafallMinHeight;
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

    // TallNether: Override default gen so it doesn't actually do anything
    @Override
    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorCountConfiguration worldgenfeaturechancedecoratorcountconfiguration, BlockPosition blockposition) {
        return IntStream.empty().mapToObj((i) -> {
            return blockposition.b(0, 0, 0);
        });
    }
}
