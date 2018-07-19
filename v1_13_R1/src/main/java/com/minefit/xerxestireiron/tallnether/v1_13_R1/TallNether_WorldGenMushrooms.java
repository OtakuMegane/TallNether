package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.ChunkGenerator;
import net.minecraft.server.v1_13_R1.GeneratorAccess;
import net.minecraft.server.v1_13_R1.GeneratorSettings;
import net.minecraft.server.v1_13_R1.IBlockData;
import net.minecraft.server.v1_13_R1.WorldGenFeatureMushroomConfiguration;
import net.minecraft.server.v1_13_R1.WorldGenMushrooms;

public class TallNether_WorldGenMushrooms extends WorldGenMushrooms {

    private final ConfigurationSection worldConfig;
    private final ConfigValues configValues;

    public TallNether_WorldGenMushrooms(ConfigurationSection worldConfig) {
        this.worldConfig = worldConfig;
        this.configValues = new ConfigValues(this.worldConfig);
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> chunkgenerator,
            Random random, BlockPosition blockposition,
            WorldGenFeatureMushroomConfiguration worldgenfeaturemushroomconfiguration) {
        int i = 0;
        IBlockData iblockdata = worldgenfeaturemushroomconfiguration.a.getBlockData();

        for (int ii = 0; ii < ConfigValues.brownShroomAttempts; ++ii) {
            for (int j = 0; j < 64; ++j) {
                BlockPosition blockposition1 = blockposition.a(random.nextInt(8) - random.nextInt(8),
                        random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

                if (generatoraccess.isEmpty(blockposition1) && blockposition1.getY() < ConfigValues.brownShroomMaxHeight
                        && blockposition1.getY() > ConfigValues.brownShroomMinHeight
                        && iblockdata.canPlace(generatoraccess, blockposition1)) {
                    generatoraccess.setTypeAndData(blockposition1, iblockdata, 2);
                    ++i;
                }
            }
        }

        return i > 0;
    }
}
