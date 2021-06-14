package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class TallNether_WorldGenLightStone1 extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenLightStone1(Codec<WorldGenFeatureEmptyConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> featureplacecontext) {
        GeneratorAccessSeed generatoraccessseed = featureplacecontext.a();
        BlockPosition blockposition = featureplacecontext.d();
        Random random = featureplacecontext.c();

        if (!generatoraccessseed.isEmpty(blockposition)) {
            return false;
        } else {
            IBlockData iblockdata = generatoraccessseed.getType(blockposition.up());

            if (!iblockdata.a(TBlocks.NETHERRACK) && !iblockdata.a(TBlocks.BASALT) && !iblockdata.a(TBlocks.BLACKSTONE)) {
                return false;
            } else {
                // TallNether: Ignore this decorator if not vanilla
                String worldName = generatoraccessseed.getMinecraftWorld().getWorld().getName();
                WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

                if (worldConfig != null && !worldConfig.isVanilla) {
                    return false;
                }

                generatoraccessseed.setTypeAndData(blockposition, TBlocks.GLOWSTONE.getBlockData(), 2);

                for (int i = 0; i < 1500; ++i) {
                    BlockPosition blockposition1 = blockposition.c(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));

                    if (generatoraccessseed.getType(blockposition1).isAir()) {
                        int j = 0;
                        EnumDirection[] aenumdirection = EnumDirection.values();
                        int k = aenumdirection.length;

                        for (int l = 0; l < k; ++l) {
                            EnumDirection enumdirection = aenumdirection[l];

                            if (generatoraccessseed.getType(blockposition1.shift(enumdirection)).a(TBlocks.GLOWSTONE)) {
                                ++j;
                            }

                            if (j > 1) {
                                break;
                            }
                        }

                        if (j == 1) {
                            generatoraccessseed.setTypeAndData(blockposition1, TBlocks.GLOWSTONE.getBlockData(), 2);
                        }
                    }
                }

                return true;
            }
        }
    }
}
