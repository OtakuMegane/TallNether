package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R2.WorldGenDecorator;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorContext;
import net.minecraft.server.v1_16_R2.WorldGenFeatureDecoratorConfiguration;

public abstract class TallNether_WorldGenDecoratorFeatureSimple<DC extends WorldGenFeatureDecoratorConfiguration> extends WorldGenDecorator<DC> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String biomeName;

    public TallNether_WorldGenDecoratorFeatureSimple(Codec<DC> codec, String biomeName) {
        super(codec);
        this.biomeName = biomeName;
    }

    @Override
    public final Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc, BlockPosition blockposition) {
        GeneratorAccessSeed generatoraccessseed;

        try {
            Field a = worldgendecoratorcontext.getClass().getDeclaredField("a");
            a.setAccessible(true);
            generatoraccessseed = (GeneratorAccessSeed) a.get(worldgendecoratorcontext);
        } catch (Exception e) {
            return this.a(random, dc, blockposition);
        }

        String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig == null || worldConfig.isVanilla) {
            return this.a(random, dc, blockposition);
        }

        return this.a(random, dc, blockposition, getBiomeValues(worldName));
    }

    private BiomeValues getBiomeValues(String worldName) {
        return this.configAccessor.getWorldConfig(worldName).getBiomeValues(this.biomeName);
    }

    protected abstract Stream<BlockPosition> a(Random random, DC dc, BlockPosition blockposition, BiomeValues biomeValues);

    protected abstract Stream<BlockPosition> a(Random random, DC dc, BlockPosition blockposition);
}
