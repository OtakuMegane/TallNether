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
import net.minecraft.server.v1_16_R2.WorldGenFeatureEmptyConfiguration2;

public class TallNether_WorldGenDecoratorNetherMagma extends WorldGenDecorator<WorldGenFeatureEmptyConfiguration2> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String biome;

    public TallNether_WorldGenDecoratorNetherMagma(Codec<WorldGenFeatureEmptyConfiguration2> codec, String biome) {
        super(codec);
        this.biome = biome;
    }

    public Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, WorldGenFeatureEmptyConfiguration2 worldgenfeatureemptyconfiguration2, BlockPosition blockposition) {
        GeneratorAccessSeed generatoraccessseed;

        try {
            Field a = worldgendecoratorcontext.getClass().getDeclaredField("a");
            a.setAccessible(true);
            generatoraccessseed = (GeneratorAccessSeed) a.get(worldgendecoratorcontext);
        } catch (Exception e) {
            // TallNether: Vanilla generation
            int i = worldgendecoratorcontext.b();
            int j = i - 5 + random.nextInt(10);

            return Stream.of(new BlockPosition(blockposition.getX(), j, blockposition.getZ()));
        }

        String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig == null || worldConfig.isVanilla) {
            // TallNether: Vanilla generation
            int i = worldgendecoratorcontext.b();
            int j = i - 5 + random.nextInt(10);

            return Stream.of(new BlockPosition(blockposition.getX(), j, blockposition.getZ()));
        }

        BiomeValues biomeValues = worldConfig.getBiomeValues(this.biome);
        int rangeSize = biomeValues.values.get("magma-block-range-size");
        int j = biomeValues.values.get("magma-block-min-height") + random.nextInt(rangeSize);

        return Stream.of(new BlockPosition(blockposition.getX(), j, blockposition.getZ()));
    }
}