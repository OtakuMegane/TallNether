package com.minefit.xerxestireiron.tallnether.v1_16_R3.Decorators;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.WorldGenFeatureChanceDecoratorRangeConfiguration;

public class TallNether_WorldGenDecoratorRange extends TallNether_WorldGenDecoratorFeatureSimple<WorldGenFeatureChanceDecoratorRangeConfiguration> {

    private final String block;

    public TallNether_WorldGenDecoratorRange(Codec<WorldGenFeatureChanceDecoratorRangeConfiguration> codec, String biome, String block) {
        super(codec, biome);
        this.block = block;
    }

    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition, BiomeValues biomevalues, boolean vanilla)             {
        if (vanilla) {
            // TallNether: Ignore this decorator if vanilla
            if(this.block.equals("glowstone2")) {
                return Stream.empty();
            }

            //return a(random, worldgenfeaturechancedecoratorrangeconfiguration, blockposition);
        }

        int attempts = biomevalues.values.get(this.block + "-attempts");
        int minHeight = biomevalues.values.get(this.block + "-min-height");
        int max = biomevalues.values.get(this.block + "-max-height");
        int maxHeight = max > 0 ? max : 1;
        int maxOffset = biomevalues.values.get(this.block + "-max-offset");

        // TallNether: Double the attempts to scale with the extra height
        return IntStream.range(0, attempts).mapToObj((i) -> {
            // TallNether: Note this is based on the old NetherChance decorator
            int j = random.nextInt(16) + blockposition.getX();
            int k = random.nextInt(16) + blockposition.getZ();
            int l = random.nextInt(maxHeight - maxOffset) + minHeight;

            return new BlockPosition(j, l, k);
        });
    }

    // TallNether: Vanilla generation
    public Stream<BlockPosition> a(Random random, WorldGenFeatureChanceDecoratorRangeConfiguration worldgenfeaturechancedecoratorrangeconfiguration, BlockPosition blockposition) {
        int i = blockposition.getX();
        int j = blockposition.getZ();
        int k = random.nextInt(worldgenfeaturechancedecoratorrangeconfiguration.e - worldgenfeaturechancedecoratorrangeconfiguration.d) + worldgenfeaturechancedecoratorrangeconfiguration.c;

        return Stream.of(new BlockPosition(i, k, j));
    }
}
