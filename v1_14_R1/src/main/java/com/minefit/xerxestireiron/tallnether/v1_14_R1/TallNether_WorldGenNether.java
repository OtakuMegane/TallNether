package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.minefit.xerxestireiron.tallnether.ConfigValues;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_14_R1.BiomeBase;
import net.minecraft.server.v1_14_R1.ChunkGenerator;
import net.minecraft.server.v1_14_R1.DefinedStructureManager;
import net.minecraft.server.v1_14_R1.StructureBoundingBox;
import net.minecraft.server.v1_14_R1.StructureGenerator;
import net.minecraft.server.v1_14_R1.StructurePiece;
import net.minecraft.server.v1_14_R1.StructureStart;
import net.minecraft.server.v1_14_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenNether;
import net.minecraft.server.v1_14_R1.WorldGenNetherPieces;

@SuppressWarnings({ "rawtypes" })
public class TallNether_WorldGenNether extends WorldGenNether {

    public static ConfigValues configValues;

    public TallNether_WorldGenNether(Function<Dynamic<?>, ? extends WorldGenFeatureEmptyConfiguration> function, ConfigValues configValues) {
        super(function);
        TallNether_WorldGenNether.configValues = configValues;
    }

    @Override
    public StructureGenerator.a a() {
        return TallNether_WorldGenNether.a::new;
    }

    public static class a extends StructureStart {
        public a(StructureGenerator<?> structuregenerator, int i, int j, BiomeBase biomebase, StructureBoundingBox structureboundingbox, int k, long l) {
            super(structuregenerator, i, j, biomebase, structureboundingbox, k, l);
        }

        @Override
        public void a(ChunkGenerator<?> chunkgenerator, DefinedStructureManager definedstructuremanager, int i, int j, BiomeBase biomebase) {
            WorldGenNetherPieces.WorldGenNetherPiece15 worldgennetherpieces_worldgennetherpiece15 = new WorldGenNetherPieces.WorldGenNetherPiece15(this.d, (i << 4) + 2, (j << 4) + 2);

            this.b.add(worldgennetherpieces_worldgennetherpiece15);
            worldgennetherpieces_worldgennetherpiece15.a((StructurePiece) worldgennetherpieces_worldgennetherpiece15, this.b, (Random) this.d);
            List list = worldgennetherpieces_worldgennetherpiece15.d;

            while (!list.isEmpty()) {
                int k = this.d.nextInt(list.size());
                StructurePiece structurepiece = (StructurePiece) list.remove(k);

                structurepiece.a((StructurePiece) worldgennetherpieces_worldgennetherpiece15, this.b, (Random) this.d);
            }

            this.b();
            this.a(this.d, TallNether_WorldGenNether.configValues.fortressMin, TallNether_WorldGenNether.configValues.fortressMax);
        }
    }
}