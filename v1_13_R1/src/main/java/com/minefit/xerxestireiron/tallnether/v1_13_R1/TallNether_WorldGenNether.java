package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.util.List;
import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigValues;

import net.minecraft.server.v1_13_R1.BiomeBase;
import net.minecraft.server.v1_13_R1.Biomes;
import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.ChunkGenerator;
import net.minecraft.server.v1_13_R1.GeneratorAccess;
import net.minecraft.server.v1_13_R1.IBlockAccess;
import net.minecraft.server.v1_13_R1.SeededRandom;
import net.minecraft.server.v1_13_R1.StructurePiece;
import net.minecraft.server.v1_13_R1.StructureStart;
import net.minecraft.server.v1_13_R1.WorldGenNether;
import net.minecraft.server.v1_13_R1.WorldGenNetherPieces;

@SuppressWarnings({ "rawtypes" })
public class TallNether_WorldGenNether extends WorldGenNether {

    private final ConfigValues configValues;

    public TallNether_WorldGenNether(ConfigValues configValues) {
        this.configValues = configValues;
    }

    @Override
    protected StructureStart a(GeneratorAccess generatoraccess, ChunkGenerator<?> chunkgenerator, SeededRandom seededrandom, int i, int j) {
        BiomeBase biomebase = chunkgenerator.getWorldChunkManager().getBiome(new BlockPosition((i << 4) + 9, 0, (j << 4) + 9), Biomes.b);

        return new TallNether_WorldGenNether.a(generatoraccess, seededrandom, i, j, biomebase, this.configValues);
    }

    public static class a extends StructureStart {

        public a() {}

        public a(GeneratorAccess generatoraccess, SeededRandom seededrandom, int i, int j, BiomeBase biomebase, ConfigValues configValues) {
            super(i, j, biomebase, seededrandom, generatoraccess.getSeed());
            WorldGenNetherPieces.WorldGenNetherPiece15 worldgennetherpieces_worldgennetherpiece15 = new WorldGenNetherPieces.WorldGenNetherPiece15(seededrandom, (i << 4) + 2, (j << 4) + 2);

            this.a.add(worldgennetherpieces_worldgennetherpiece15);
            worldgennetherpieces_worldgennetherpiece15.a((StructurePiece) worldgennetherpieces_worldgennetherpiece15, this.a, (Random) seededrandom);
            List list = worldgennetherpieces_worldgennetherpiece15.d;

            while (!list.isEmpty()) {
                int k = seededrandom.nextInt(list.size());
                StructurePiece structurepiece = (StructurePiece) list.remove(k);

                structurepiece.a((StructurePiece) worldgennetherpieces_worldgennetherpiece15, this.a, (Random) seededrandom);
            }

            this.a((IBlockAccess) generatoraccess);
            // Structure gen fix for 1.13 IndexArrayOutOfBounds
            int min = configValues.fortressMin > 210 ? 210 : configValues.fortressMin;
            int max = configValues.fortressMax > 210 ? 210 : configValues.fortressMax;
            this.a(generatoraccess, seededrandom, min, max);
        }
    }
}