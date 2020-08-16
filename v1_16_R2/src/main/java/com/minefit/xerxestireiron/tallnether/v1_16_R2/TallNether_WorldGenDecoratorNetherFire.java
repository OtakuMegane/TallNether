package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import com.google.common.collect.Lists;
import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R2.WorldGenDecoratorFrequencyConfiguration;

public class TallNether_WorldGenDecoratorNetherFire extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenDecoratorFrequencyConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final String blockType;

    public TallNether_WorldGenDecoratorNetherFire(Codec<WorldGenDecoratorFrequencyConfiguration> codec, String blockType) {
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

        String biomeName = this.configAccessor.biomeClassToConfig(generatoraccessseed.getBiome(blockposition).toString());
        System.out.println(biomeName);
        System.out.println(worldConfig);
        BiomeValues biomeConfig = worldConfig.getBiomeValues(biomeName);
        int attempts;
        int min;
        int max;

        if (this.blockType.equals("fire")) {
            attempts = biomeConfig.values.get("fire-attempts");
            max = biomeConfig.values.get("fire-max-height");
            min = biomeConfig.values.get("fire-min-height");
        } else if (this.blockType.equals("soul-fire")) {
            attempts = biomeConfig.values.get("soul-fire-attempts");
            max = biomeConfig.values.get("soul-fire-max-height");
            min = biomeConfig.values.get("soul-fire-min-height");
        } else {
            return a(random, worldgendecoratorfrequencyconfiguration, blockposition);
        }

        max = max > 0 ? max : 1;
        attempts = attempts > 0 ? attempts : 1;
        return a(random, worldgendecoratorfrequencyconfiguration, blockposition, attempts, min, max);
    }

    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition, int attempts, int min, int max) {
        List<BlockPosition> list = Lists.newArrayList();

        for (int i = 0; i < random.nextInt(random.nextInt(attempts) + 1) + 1; ++i) {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(max) + min;

            list.add(new BlockPosition(j, l, k));
        }

        return list.stream();
    }

    // TallNether: Vanilla generation
    public Stream<BlockPosition> a(Random random, WorldGenDecoratorFrequencyConfiguration worldgendecoratorfrequencyconfiguration, BlockPosition blockposition)             {
        List<BlockPosition> list = Lists.newArrayList();

        for (int i = 0; i < random.nextInt(random.nextInt(worldgendecoratorfrequencyconfiguration.a().a(random)) + 1) + 1; ++i) {
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(120) + 4;

            list.add(new BlockPosition(j, l, k));
        }

        return list.stream();
    }
}