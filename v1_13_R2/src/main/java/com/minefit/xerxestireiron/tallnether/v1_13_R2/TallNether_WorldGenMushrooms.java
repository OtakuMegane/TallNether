package com.minefit.xerxestireiron.tallnether.v1_13_R2;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.Blocks;
import net.minecraft.server.v1_13_R2.ChunkGenerator;
import net.minecraft.server.v1_13_R2.GeneratorAccess;
import net.minecraft.server.v1_13_R2.GeneratorSettings;
import net.minecraft.server.v1_13_R2.IBlockData;
import net.minecraft.server.v1_13_R2.WorldGenFeatureMushroomConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenMushrooms;

public class TallNether_WorldGenMushrooms extends WorldGenMushrooms {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenMushrooms() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator,
            Random random, BlockPosition blockposition,
            WorldGenFeatureMushroomConfiguration worldgenfeaturemushroomconfiguration) {
        String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
        int i = 0;
        IBlockData iblockdata = worldgenfeaturemushroomconfiguration.a.getBlockData();
        int attempts = this.configAccessor.getConfig(worldName).brownShroomAttempts;
        int min = this.configAccessor.getConfig(worldName).brownShroomMinHeight;
        int max = this.configAccessor.getConfig(worldName).brownShroomMaxHeight;

        if (worldgenfeaturemushroomconfiguration.a == Blocks.RED_MUSHROOM) {
            attempts = this.configAccessor.getConfig(worldName).redShroomAttempts;
            min = this.configAccessor.getConfig(worldName).redShroomMinHeight;
            max = this.configAccessor.getConfig(worldName).redShroomMaxHeight;
        }

        for (int ii = 0; ii < attempts; ++ii) {
            for (int j = 0; j < 64; ++j) {
                BlockPosition blockposition1 = blockposition.a(random.nextInt(8) - random.nextInt(8),
                        random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

                if (generatoraccess.isEmpty(blockposition1) && blockposition1.getY() < max
                        && blockposition1.getY() > min && iblockdata.canPlace(generatoraccess, blockposition1)) {
                    generatoraccess.setTypeAndData(blockposition1, iblockdata, 2);
                    ++i;
                }
            }
        }

        return i > 0;
    }
}
