package com.minefit.xerxestireiron.tallnether.v1_16_R2.WorldGenSurfaces;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import net.minecraft.server.v1_16_R2.BiomeBase;
import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.Blocks;
import net.minecraft.server.v1_16_R2.IBlockData;
import net.minecraft.server.v1_16_R2.IChunkAccess;
import net.minecraft.server.v1_16_R2.NoiseGeneratorOctaves;
import net.minecraft.server.v1_16_R2.SeededRandom;
import net.minecraft.server.v1_16_R2.WorldGenSurface;
import net.minecraft.server.v1_16_R2.WorldGenSurfaceConfigurationBase;

public class TallNether_WorldGenSurfaceNetherForest extends WorldGenSurface<WorldGenSurfaceConfigurationBase> {
    private static final IBlockData b = Blocks.CAVE_AIR.getBlockData();
    protected long a;
    private NoiseGeneratorOctaves c;

    public TallNether_WorldGenSurfaceNetherForest(Codec<WorldGenSurfaceConfigurationBase> codec) {
        super(codec);
    }

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase biomebase, int i, int j, int k, double d0, IBlockData iblockdata, IBlockData iblockdata1, int l, long i1, WorldGenSurfaceConfigurationBase worldgensurfaceconfigurationbase) {
        int j1 = l;
        int k1 = i & 15;
        int l1 = j & 15;
        double d1 = this.c.a((double) i * 0.1D, (double) l, (double) j * 0.1D);
        boolean flag = d1 > 0.15D + random.nextDouble() * 0.35D;
        double d2 = this.c.a((double) i * 0.1D, 109.0D, (double) j * 0.1D);
        boolean flag1 = d2 > 0.25D + random.nextDouble() * 0.9D;
        int i2 = (int) (d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
        int j2 = -1;
        IBlockData iblockdata2 = worldgensurfaceconfigurationbase.b();

        // TallNether: Originally 127, set to 255
        for (int k2 = 255; k2 >= 0; --k2) {
            blockposition_mutableblockposition.d(k1, k2, l1);
            IBlockData iblockdata3 = worldgensurfaceconfigurationbase.a();
            IBlockData iblockdata4 = ichunkaccess.getType(blockposition_mutableblockposition);

            if (iblockdata4.isAir()) {
                j2 = -1;
            } else if (iblockdata4.a(iblockdata.getBlock())) {
                if (j2 == -1) {
                    boolean flag2 = false;

                    if (i2 <= 0) {
                        flag2 = true;
                        iblockdata2 = worldgensurfaceconfigurationbase.b();
                    }

                    if (flag) {
                        iblockdata3 = worldgensurfaceconfigurationbase.b();
                    } else if (flag1) {
                        iblockdata3 = worldgensurfaceconfigurationbase.c();
                    }

                    if (k2 < j1 && flag2) {
                        iblockdata3 = iblockdata1;
                    }

                    j2 = i2;
                    if (k2 >= j1 - 1) {
                        ichunkaccess.setType(blockposition_mutableblockposition, iblockdata3, false);
                    } else {
                        ichunkaccess.setType(blockposition_mutableblockposition, iblockdata2, false);
                    }
                } else if (j2 > 0) {
                    --j2;
                    ichunkaccess.setType(blockposition_mutableblockposition, iblockdata2, false);
                }
            }
        }

    }

    @Override
    public void a(long i) {
        if (this.a != i || this.c == null) {
            this.c = new NoiseGeneratorOctaves(new SeededRandom(i), ImmutableList.of(0));
        }

        this.a = i;
    }
}
