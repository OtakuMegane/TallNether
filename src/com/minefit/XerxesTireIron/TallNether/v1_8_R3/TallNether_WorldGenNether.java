package com.minefit.XerxesTireIron.TallNether.v1_8_R3;

import java.util.List;
import java.util.Random;

import com.minefit.XerxesTireIron.TallNether.TallNether;

import net.minecraft.server.v1_8_R3.StructurePiece;
import net.minecraft.server.v1_8_R3.StructureStart;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenNether;
import net.minecraft.server.v1_8_R3.WorldGenNetherPieces;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TallNether_WorldGenNether extends WorldGenNether {

    private String worldConfig;
    private TallNether plugin;

    public TallNether_WorldGenNether(World world, String config, TallNether instance) {
        super();
        this.plugin = instance;
        this.worldConfig = config;
    }

    @Override
    protected StructureStart b(int i, int j) {
        return new TallNether_WorldGenNether.WorldGenNetherStart(this.c, this.b, i, j, worldConfig, plugin);
    }

    public static class WorldGenNetherStart extends StructureStart {

        public WorldGenNetherStart() {
        }

        public WorldGenNetherStart(World world, Random random, int i, int j, String worldConfig, TallNether plugin) {
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
            this.a(world, random, plugin.getConfig().getInt(worldConfig + "fortress-min", 64),
                    plugin.getConfig().getInt(worldConfig + "fortress-max", 140));
        }
    }
}