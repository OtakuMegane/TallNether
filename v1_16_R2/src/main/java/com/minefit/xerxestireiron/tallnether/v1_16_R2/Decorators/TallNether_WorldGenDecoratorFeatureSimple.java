package com.minefit.xerxestireiron.tallnether.v1_16_R2.Decorators;

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
    private final String biome;

    public TallNether_WorldGenDecoratorFeatureSimple(Codec<DC> codec, String biome) {
        super(codec);
        this.biome = biome;
    }

    @Override
    public final Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc, BlockPosition blockposition) {
        GeneratorAccessSeed generatoraccessseed = null;

        try {
            Field a = worldgendecoratorcontext.getClass().getDeclaredField("a");
            a.setAccessible(true);
            generatoraccessseed = (GeneratorAccessSeed) a.get(worldgendecoratorcontext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);
        boolean vanilla = worldConfig == null || worldConfig.isVanilla;
        return this.a(random, dc, blockposition, getBiomeValues(worldName), vanilla);
    }

    private BiomeValues getBiomeValues(String worldName) {
        return this.configAccessor.getWorldConfig(worldName).getBiomeValues(this.biome);
    }

    protected abstract Stream<BlockPosition> a(Random random, DC dc, BlockPosition blockposition, BiomeValues biomeValues, boolean vanilla);

    protected abstract Stream<BlockPosition> a(Random random, DC dc, BlockPosition blockposition);
}
