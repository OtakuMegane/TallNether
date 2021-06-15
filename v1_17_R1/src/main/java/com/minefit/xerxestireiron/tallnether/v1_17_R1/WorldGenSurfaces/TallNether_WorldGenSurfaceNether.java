package com.minefit.xerxestireiron.tallnether.v1_17_R1.WorldGenSurfaces;

import java.util.Random;
import java.util.stream.IntStream;

import com.minefit.xerxestireiron.tallnether.v1_17_R1.Transition.TBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurface;
import net.minecraft.world.level.levelgen.surfacebuilders.WorldGenSurfaceConfigurationBase;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorOctaves;

public class TallNether_WorldGenSurfaceNether extends WorldGenSurface<WorldGenSurfaceConfigurationBase> {

    private static final IBlockData AIR = TBlocks.AIR.getBlockData();
    private static final IBlockData GRAVEL = TBlocks.GRAVEL.getBlockData();
    private static final IBlockData SOUL_SAND = TBlocks.SOUL_SAND.getBlockData();
    protected long seed;
    protected NoiseGeneratorOctaves decorationNoise;

    public TallNether_WorldGenSurfaceNether(Codec<WorldGenSurfaceConfigurationBase> codec) {
        super(codec);
    }

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase biomebase, int i, int j, int k, double d0, IBlockData iblockdata, IBlockData iblockdata1, int l, int i1, long j1, WorldGenSurfaceConfigurationBase worldgensurfaceconfigurationbase) {
        int k1 = l;
        int l1 = i & 15;
        int i2 = j & 15;
        double d1 = 0.03125D;
        boolean flag = this.decorationNoise.a((double) i * 0.03125D, (double) j * 0.03125D, 0.0D) * 75.0D + random.nextDouble() > 0.0D;
        boolean flag1 = this.decorationNoise.a((double) i * 0.03125D, 109.0D, (double) j * 0.03125D) * 75.0D + random.nextDouble() > 0.0D;
        int j2 = (int) (d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
        int k2 = -1;
        IBlockData iblockdata2 = worldgensurfaceconfigurationbase.a();
        IBlockData iblockdata3 = worldgensurfaceconfigurationbase.b();

        // A bit hacky but seems to work for now
        int start = (ichunkaccess.getType(blockposition_mutableblockposition.d(l1, 255, i2)).isAir()) ? 127 : 255;

        for (int l2 = start; l2 >= i1; --l2) {
            blockposition_mutableblockposition.d(l1, l2, i2);
            IBlockData iblockdata4 = ichunkaccess.getType(blockposition_mutableblockposition);

            if (iblockdata4.isAir()) {
                k2 = -1;
            } else if (iblockdata4.a(iblockdata.getBlock())) {
                if (k2 == -1) {
                    boolean flag2 = false;

                    if (j2 <= 0) {
                        flag2 = true;
                        iblockdata3 = worldgensurfaceconfigurationbase.b();
                    } else if (l2 >= k1 - 4 && l2 <= k1 + 1) {
                        iblockdata2 = worldgensurfaceconfigurationbase.a();
                        iblockdata3 = worldgensurfaceconfigurationbase.b();
                        if (flag1) {
                            iblockdata2 = TallNether_WorldGenSurfaceNether.GRAVEL;
                            iblockdata3 = worldgensurfaceconfigurationbase.b();
                        }

                        if (flag) {
                            iblockdata2 = TallNether_WorldGenSurfaceNether.SOUL_SAND;
                            iblockdata3 = TallNether_WorldGenSurfaceNether.SOUL_SAND;
                        }
                    }

                    if (l2 < k1 && flag2) {
                        iblockdata2 = iblockdata1;
                    }

                    k2 = j2;
                    if (l2 >= k1 - 1) {
                        ichunkaccess.setType(blockposition_mutableblockposition, iblockdata2, false);
                    } else {
                        ichunkaccess.setType(blockposition_mutableblockposition, iblockdata3, false);
                    }
                } else if (k2 > 0) {
                    --k2;
                    ichunkaccess.setType(blockposition_mutableblockposition, iblockdata3, false);
                }
            }
        }

    }

    @Override
    public void a(long i) {
        if (this.seed != i || this.decorationNoise == null) {
            this.decorationNoise = new NoiseGeneratorOctaves(new SeededRandom(i), IntStream.rangeClosed(-3, 0));
        }

        this.seed = i;
    }
}
