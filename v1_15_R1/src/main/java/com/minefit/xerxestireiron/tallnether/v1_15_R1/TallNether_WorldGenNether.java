package com.minefit.xerxestireiron.tallnether.v1_15_R1;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ReflectionHelper;
import com.mojang.datafixers.Dynamic;

import net.minecraft.server.v1_15_R1.BiomeBase;
import net.minecraft.server.v1_15_R1.BiomeManager;
import net.minecraft.server.v1_15_R1.ChunkGenerator;
import net.minecraft.server.v1_15_R1.DefinedStructureManager;
import net.minecraft.server.v1_15_R1.GeneratorAccess;
import net.minecraft.server.v1_15_R1.StructureBoundingBox;
import net.minecraft.server.v1_15_R1.StructureGenerator;
import net.minecraft.server.v1_15_R1.StructurePiece;
import net.minecraft.server.v1_15_R1.StructureStart;
import net.minecraft.server.v1_15_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_15_R1.WorldGenNether;
import net.minecraft.server.v1_15_R1.WorldGenNetherPieces;

@SuppressWarnings({ "rawtypes" })
public class TallNether_WorldGenNether extends WorldGenNether {

    public TallNether_WorldGenNether(Function<Dynamic<?>, ? extends WorldGenFeatureEmptyConfiguration> function) {
        super(function);
    }

    @Override
    public boolean a(BiomeManager biomemanager, ChunkGenerator<?> chunkgenerator, Random random, int i, int j, BiomeBase biomebase) {
        int k = i >> 4;
        int l = j >> 4;

        random.setSeed((long) (k ^ l << 4) ^ chunkgenerator.getSeed());
        random.nextInt();
        return random.nextInt(3) != 0 ? false : (i != (k << 4) + 4 + random.nextInt(8) ? false : (j != (l << 4) + 4 + random.nextInt(8) ? false : chunkgenerator.canSpawnStructure(biomebase, this)));
    }

    @Override
    public StructureGenerator.a a() {
        return TallNether_WorldGenNether.a::new;
    }

    public static class a extends StructureStart {
        private final ConfigAccessor configAccessor = new ConfigAccessor();

        public a(StructureGenerator<?> structuregenerator, int i, int j, StructureBoundingBox structureboundingbox, int k, long l) {
            super(structuregenerator, i, j, structureboundingbox, k, l);
        }

        @Override
        public void a(ChunkGenerator<?> chunkgenerator, DefinedStructureManager definedstructuremanager, int i, int j, BiomeBase biomebase) {
            WorldGenNetherPieces.WorldGenNetherPiece15 worldgennetherpieces_worldgennetherpiece15 = new WorldGenNetherPieces.WorldGenNetherPiece15(this.d, (i << 4) + 2, (j << 4) + 2);
            GeneratorAccess generatoraccess = null;

            try {
                Field gAccess;
                gAccess = ReflectionHelper.getField(chunkgenerator.getClass(), "a", true);
                gAccess.setAccessible(true);
                generatoraccess = (GeneratorAccess) gAccess.get(chunkgenerator);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int fortressMin = 48;
            int fortressMax = 70;

            if (generatoraccess != null) {
                String worldName = generatoraccess.getMinecraftWorld().getWorld().getName();
                fortressMin = this.configAccessor.getConfig(worldName).fortressMin;
                fortressMax = this.configAccessor.getConfig(worldName).fortressMax;
            }

            this.b.add(worldgennetherpieces_worldgennetherpiece15);
            worldgennetherpieces_worldgennetherpiece15.a((StructurePiece) worldgennetherpieces_worldgennetherpiece15, this.b, (Random) this.d);
            List list = worldgennetherpieces_worldgennetherpiece15.d;

            while (!list.isEmpty()) {
                int k = this.d.nextInt(list.size());
                StructurePiece structurepiece = (StructurePiece) list.remove(k);

                structurepiece.a((StructurePiece) worldgennetherpieces_worldgennetherpiece15, this.b, (Random) this.d);
            }

            this.b();

            // TallNether: Minecraft default is 48 min, 72 max; change to use fortress parameters from config
            this.a(this.d, fortressMin, fortressMax);
        }
    }
}