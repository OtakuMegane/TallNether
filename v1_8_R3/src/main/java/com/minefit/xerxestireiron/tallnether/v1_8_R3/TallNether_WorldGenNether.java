package com.minefit.xerxestireiron.tallnether.v1_8_R3;

import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.StructurePiece;
import net.minecraft.server.v1_8_R3.StructureStart;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenNether;
import net.minecraft.server.v1_8_R3.WorldGenNetherPieces;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TallNether_WorldGenNether extends WorldGenNether {

    private ConfigValues configValues;

    public TallNether_WorldGenNether(ConfigValues configValues) {
        super();
        this.configValues = configValues;
    }

    @Override
    protected StructureStart b(int i, int j) {
        return new TallNether_WorldGenNether.WorldGenNetherStart(this.c, this.b, i, j, this.configValues);
    }

    public static class WorldGenNetherStart extends StructureStart {

        public WorldGenNetherStart() {
        }

        public WorldGenNetherStart(World world, Random random, int i, int j, ConfigValues configValues) {
            super(i, j);
            WorldGenNetherPieces.WorldGenNetherPiece15 worldgennetherpieces_worldgennetherpiece15 = new WorldGenNetherPieces.WorldGenNetherPiece15(
                    random, (i << 4) + 2, (j << 4) + 2);

            this.a.add(worldgennetherpieces_worldgennetherpiece15);
            worldgennetherpieces_worldgennetherpiece15.a((StructurePiece) worldgennetherpieces_worldgennetherpiece15,
                    (List) this.a, random);
            List list = worldgennetherpieces_worldgennetherpiece15.e;

            while (!list.isEmpty()) {
                int k = random.nextInt(list.size());
                StructurePiece structurepiece = (StructurePiece) list.remove(k);

                structurepiece.a((StructurePiece) worldgennetherpieces_worldgennetherpiece15, (List) this.a, random);
            }

            this.c();
            this.a(world, random, configValues.fortressMin, configValues.fortressMax);
        }
    }
}