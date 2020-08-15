package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;

public class TallNether_WorldGenDecoratorNetherGlowstone extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorFrequencyConfiguration>  {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String blockType;

    public TallNether_WorldGenDecoratorNetherGlowstone(Codec<WorldGenDecoratorFrequencyConfiguration> codec, String blockType) {
        super(codec);
        this.blockType = blockType;
    }

    // TallNether: Methods added to allow per-world config access through GeneratorAccess
    public Stream<BlockPosition> a(GeneratorAccessSeed generatoraccessseed, Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig == null || worldConfig.isVanilla) {
            return a(random, worldgendecoratorfrequencyconfiguration, blockposition);
        }

        String biomeName = this.configAccessor.biomeClassToConfig(generatoraccessseed.getBiome(blockposition).getClass().getSimpleName());
        BiomeValues biomeConfig = worldConfig.getBiomeValues(biomeName);
        int attempts;
        int max;
        int min;

        if (this.blockType.equals("glowstone1")) {
            attempts = biomeConfig.values.get("glowstone1-attempts");
            max = biomeConfig.values.get("glowstone1-max-height");
            min = biomeConfig.values.get("glowstone1-min-height");
        } else {
            return a(random, worldgendecoratorfrequencyconfiguration, blockposition);
        }

        max = max > 0 ? max : 1;
        attempts = attempts > 0 ? attempts : 1;
        return a(random, worldgendecoratorfrequencyconfiguration, blockposition, attempts, min, max);
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, int attempts, int min, int max) {
        return IntStream.range(0, random.nextInt(random.nextInt(attempts) + 1)).mapToObj((i) -> {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(max) + min;

            return new BlockPosition(j, l, k);
        });
    }

    // TallNether: Vanilla generation
    @Override
    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition) {
        return IntStream.range(0, random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a().a(random)) + 1)).mapToObj((i) -> {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(120) + 4;

            return new BlockPosition(j, l, k);
        });
    }
}
