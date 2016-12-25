package com.minefit.XerxesTireIron.TallNether.v1_8_R2;

import java.util.List;
import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import net.minecraft.server.v1_8_R2.StructurePiece;
import net.minecraft.server.v1_8_R2.StructureStart;
import net.minecraft.server.v1_8_R2.World;
import net.minecraft.server.v1_8_R2.WorldGenNether;
import net.minecraft.server.v1_8_R2.WorldGenNetherPieces;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TallNether_WorldGenNether extends WorldGenNether {
    private ConfigurationSection worldConfig;

    public TallNether_WorldGenNether(World world, ConfigurationSection worldConfig) {
        super();
        this.worldConfig = worldConfig;
    }

    @Override
    protected StructureStart b(int i, int j) {
        return new TallNether_WorldGenNether.WorldGenNetherStart(this.c, this.b, i, j, worldConfig);
    }

    public static class WorldGenNetherStart extends StructureStart {

        public WorldGenNetherStart() {
        }

        public WorldGenNetherStart(World world, Random random, int i, int j, ConfigurationSection worldConfig) {
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
            this.a(world, random, worldConfig.getInt("fortress-min", 64), worldConfig.getInt("fortress-max", 90));
        }
    }
}