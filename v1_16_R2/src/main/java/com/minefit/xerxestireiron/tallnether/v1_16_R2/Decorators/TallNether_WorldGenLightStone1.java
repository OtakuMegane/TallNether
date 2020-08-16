package com.minefit.xerxestireiron.tallnether.v1_16_R2.Decorators;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.Blocks;
import net.minecraft.server.v1_16_R2.ChunkGenerator;
import net.minecraft.server.v1_16_R2.EnumDirection;
import net.minecraft.server.v1_16_R2.GeneratorAccessSeed;
import net.minecraft.server.v1_16_R2.IBlockData;
import net.minecraft.server.v1_16_R2.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_16_R2.WorldGenerator;

public class TallNether_WorldGenLightStone1 extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenLightStone1(Codec<WorldGenFeatureEmptyConfiguration> codec) {
        super(codec);
    }

    public boolean generate(GeneratorAccessSeed generatoraccessseed, ChunkGenerator chunkgenerator, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration worldgenfeatureemptyconfiguration) {
        if (!generatoraccessseed.isEmpty(blockposition)) {
            return false;
        } else {
            IBlockData iblockdata = generatoraccessseed.getType(blockposition.up());

            if (!iblockdata.a(Blocks.NETHERRACK) && !iblockdata.a(Blocks.BASALT) && !iblockdata.a(Blocks.BLACKSTONE)) {
                return false;
            } else {
                // TallNether: Ignore this decorator if not vanilla
                String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
                WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

                if (worldConfig != null && !worldConfig.isVanilla) {
                    return false;
                }

                generatoraccessseed.setTypeAndData(blockposition, Blocks.GLOWSTONE.getBlockData(), 2);

                for (int i = 0; i < 1500; ++i) {
                    BlockPosition blockposition1 = blockposition.b(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));

                    if (generatoraccessseed.getType(blockposition1).isAir()) {
                        int j = 0;
                        EnumDirection[] aenumdirection = EnumDirection.values();
                        int k = aenumdirection.length;

                        for (int l = 0; l < k; ++l) {
                            EnumDirection enumdirection = aenumdirection[l];

                            if (generatoraccessseed.getType(blockposition1.shift(enumdirection)).a(Blocks.GLOWSTONE)) {
                                ++j;
                            }

                            if (j > 1) {
                                break;
                            }
                        }

                        if (j == 1) {
                            generatoraccessseed.setTypeAndData(blockposition1, Blocks.GLOWSTONE.getBlockData(), 2);
                        }
                    }
                }

                return true;
            }
        }
    }
}
