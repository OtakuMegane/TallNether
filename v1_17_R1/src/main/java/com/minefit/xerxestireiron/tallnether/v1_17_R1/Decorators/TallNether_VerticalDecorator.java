package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.data.RegistryGeneration;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.WorldGenDecorator;
import net.minecraft.world.level.levelgen.placement.WorldGenDecoratorContext;

public abstract class TallNether_VerticalDecorator<DC extends WorldGenFeatureDecoratorConfiguration>
        extends WorldGenDecorator<DC> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_VerticalDecorator(Codec<DC> codec) {
        super(codec);
    }

    protected abstract int a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc, int i);

    // Returns stream of BlockPosition; vanilla does one, we can adapt to multiple to match attempts
    // Same basic plan as FeatureSimple from 1.16
    // The abstract int method requests a single y-coordinate
    // We can probably generate within this method, and pass it on to the default way if vanilla world
    // IMPORTANT: We would need to instantiate the downstream decorator anyway!
    // Current: We may need to pass our custom config block name in after all and do the work in the decorator instances
    // Current: Let's also do a conversion in BiomeValues from the config block name to the real block name
    // (note glowstone2 as just glowstone for use here; glowstone1 is handled by itself and can stay as such)
    // Current: A change in config to actual block values may come later
    // TL;DR more or less implement like we did in 1.16 lol

    @Override
    public final Stream<BlockPosition> a(WorldGenDecoratorContext worldgendecoratorcontext, Random random, DC dc,
            BlockPosition blockposition) {
        GeneratorAccessSeed generatorAccessSeed = worldgendecoratorcontext.d();
        String worldName = generatorAccessSeed.getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig.isVanilla) {
            return Stream.of(new BlockPosition(blockposition.getX(),
                    this.a(worldgendecoratorcontext, random, dc, blockposition.getY()), blockposition.getZ()));
        } else {
            BiomeBase biome = generatorAccessSeed.getBiome(blockposition);
            String biomeName = RegistryGeneration.i.getKey(biome).getKey();
            BiomeValues biomeValues = worldConfig.getBiomeValues(biomeName);
            Block block = generatorAccessSeed.getType(blockposition).getBlock();
            String blockName = IRegistry.W.getKey(block).getKey();
            int attempts = biomeValues.values.get(blockName + "-attempts");
            int minHeight = biomeValues.values.get(blockName + "-min-height");
            int max = biomeValues.values.get(blockName + "-max-height");
            int maxHeight = max > 0 ? max : 1;
            int maxOffset = biomeValues.values.get(blockName + "-max-offset");

            // TallNether: Double the attempts to scale with the extra height
            return IntStream.range(0, attempts).mapToObj((i) -> {
                // TallNether: Note this is based on the old NetherChance decorator
                int j = random.nextInt(16) + blockposition.getX();
                int k = random.nextInt(16) + blockposition.getZ();
                int l = random.nextInt(maxHeight - maxOffset) + minHeight;

                return new BlockPosition(j, l, k);
            });
        }
    }
}