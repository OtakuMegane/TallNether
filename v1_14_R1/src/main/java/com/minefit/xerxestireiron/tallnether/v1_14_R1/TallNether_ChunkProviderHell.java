package com.minefit.xerxestireiron.tallnether.v1_14_R1;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigValues;

import net.minecraft.server.v1_14_R1.BiomeBase;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.Blocks;
import net.minecraft.server.v1_14_R1.ChunkCoordIntPair;
import net.minecraft.server.v1_14_R1.ChunkGeneratorAbstract;
import net.minecraft.server.v1_14_R1.EnumCreatureType;
import net.minecraft.server.v1_14_R1.GeneratorSettingsNether;
import net.minecraft.server.v1_14_R1.HeightMap;
import net.minecraft.server.v1_14_R1.IChunkAccess;
import net.minecraft.server.v1_14_R1.MathHelper;
import net.minecraft.server.v1_14_R1.NoiseGenerator;
import net.minecraft.server.v1_14_R1.NoiseGeneratorOctaves;
import net.minecraft.server.v1_14_R1.SeededRandom;
import net.minecraft.server.v1_14_R1.World;
import net.minecraft.server.v1_14_R1.WorldChunkManager;
import net.minecraft.server.v1_14_R1.WorldGenerator;

public class TallNether_ChunkProviderHell extends ChunkGeneratorAbstract<GeneratorSettingsNether> {

    //protected static final IBlockData f = Blocks.AIR.getBlockData();
    //protected static final IBlockData g = Blocks.NETHERRACK.getBlockData();
    //protected static final IBlockData h = Blocks.LAVA.getBlockData();
    /*private final NoiseGeneratorOctaves i;
    private final NoiseGeneratorOctaves j;
    private final NoiseGeneratorOctaves k;
    private final NoiseGeneratorOctaves l;
    private final NoiseGeneratorOctaves m;
    private final NoiseGeneratorOctaves n;*/
    //private final GeneratorSettingsNether o;
    //private final IBlockData p;
    //private final IBlockData q;

   // 1.14
    private final double[] h = this.j();
    private final NoiseGeneratorOctaves o;
    private final NoiseGeneratorOctaves p;
    private final NoiseGeneratorOctaves q;
    private final NoiseGenerator r;


    private final ConfigValues configValues;

    public TallNether_ChunkProviderHell(World world, WorldChunkManager worldchunkmanager, GeneratorSettingsNether generatorsettingsnether, ConfigValues configValues) {
        super(world, worldchunkmanager, 4, 8, 256, generatorsettingsnether, false);
        this.configValues = configValues;
        //this.o = generatorsettingsnether;
        //this.p = this.o.r();
        //this.q = this.o.s();

        SeededRandom seededrandom = new SeededRandom(this.seed);
        //seededrandom.a(1048);

        if (this.configValues.generateFarLands) {
            this.o = new TallNether_NoiseGeneratorOctaves(this.configValues, seededrandom, 16);
            this.p = new TallNether_NoiseGeneratorOctaves(this.configValues, seededrandom, 16);
            this.q = new TallNether_NoiseGeneratorOctaves(this.configValues, seededrandom, 8);
            this.r = new TallNether_NoiseGeneratorOctaves(this.configValues, seededrandom, 4);
        } else {
            this.o = new NoiseGeneratorOctaves(seededrandom, 16);
            this.p = new NoiseGeneratorOctaves(seededrandom, 16);
            this.q = new NoiseGeneratorOctaves(seededrandom, 8);
            this.r = new NoiseGeneratorOctaves(seededrandom, 4);
        }
    }

    @Override
    protected void a(double[] adouble, int i, int j) {
        double d0 = 684.412D;
        double d1 = 2053.236D;
        double d2 = 8.555150000000001D;
        double d3 = 34.2206D;
        boolean flag = true;
        boolean flag1 = true;

        this.a(adouble, i, j, 684.412D, 2053.236D, 8.555150000000001D, 34.2206D, 3, -10);
    }

    @Override
    protected double[] a(int i, int j) {
        return new double[] { 0.0D, 0.0D};
    }

    @Override
    protected double a(double d0, double d1, int i) {
        return this.h[i];
    }

    private double[] j() {
        double[] adouble = new double[this.i()];

        for (int i = 0; i < this.i(); ++i) {
            adouble[i] = Math.cos((double) i * 3.141592653589793D * 6.0D / (double) this.i()) * 2.0D;
            double d0 = (double) i;

            if (i > this.i() / 2) {
                d0 = (double) (this.i() - 1 - i);
            }

            if (d0 < 4.0D) {
                d0 = 4.0D - d0;
                adouble[i] -= d0 * d0 * d0 * 10.0D;
            }
        }

        return adouble;
    }

    @Override
    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
        if (enumcreaturetype == EnumCreatureType.MONSTER) {
            if (WorldGenerator.NETHER_BRIDGE.b(this.a, blockposition)) {
                return WorldGenerator.NETHER_BRIDGE.e();
            }

            if (WorldGenerator.NETHER_BRIDGE.a(this.a, blockposition) && this.a.getType(blockposition.down()).getBlock() == Blocks.NETHER_BRICKS) {
                return WorldGenerator.NETHER_BRIDGE.e();
            }
        }

        return super.getMobsFor(enumcreaturetype, blockposition);
    }

    public int getSpawnHeight() {
        return this.configValues.lavaSeaLevel + 1;
    }

    @Override
    public int getGenerationDepth() {
        return 128;
    }

    @Override
    public int getSeaLevel() {
        return this.configValues.lavaSeaLevel;
    }

    /*public void a(int i, int j, IChunkAccess ichunkaccess) {
        boolean flag = true;
        int k = this.a.getSeaLevel() / 2 + 1;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        int b0 = 33;
        double[] adouble = this.a(i * 4, 0, j * 4, 5, b0, 5);
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

        for (int l = 0; l < 4; ++l) {
            for (int i1 = 0; i1 < 4; ++i1) {
                for (int j1 = 0; j1 < 32; ++j1) {
                    double d0 = 0.125D;
                    double d1 = adouble[((l + 0) * 5 + i1 + 0) * b0 + j1 + 0];
                    double d2 = adouble[((l + 0) * 5 + i1 + 1) * b0 + j1 + 0];
                    double d3 = adouble[((l + 1) * 5 + i1 + 0) * b0 + j1 + 0];
                    double d4 = adouble[((l + 1) * 5 + i1 + 1) * b0 + j1 + 0];
                    double d5 = (adouble[((l + 0) * 5 + i1 + 0) * b0 + j1 + 1] - d1) * 0.125D;
                    double d6 = (adouble[((l + 0) * 5 + i1 + 1) * b0 + j1 + 1] - d2) * 0.125D;
                    double d7 = (adouble[((l + 1) * 5 + i1 + 0) * b0 + j1 + 1] - d3) * 0.125D;
                    double d8 = (adouble[((l + 1) * 5 + i1 + 1) * b0 + j1 + 1] - d4) * 0.125D;

                    for (int k1 = 0; k1 < 8; ++k1) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for (int l1 = 0; l1 < 4; ++l1) {
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.25D;

                            for (int i2 = 0; i2 < 4; ++i2) {
                                IBlockData iblockdata = Blocks.AIR.getBlockData();

                                if (j1 * 8 + k1 < this.configValues.lavaSeaLevel + 1) {
                                    iblockdata = this.q;
                                }

                                if (d15 > 0.0D) {
                                    iblockdata = this.p;
                                }

                                int j2 = l1 + l * 4;
                                int k2 = k1 + j1 * 8;
                                int l2 = i2 + i1 * 4;

                                ichunkaccess.setType((BlockPosition) blockposition_mutableblockposition.c(j2, k2, l2), iblockdata, false);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }

    }*/

    // TallNether: Overrides from ChunkGeneratorAbstract for flat bedrock
    @SuppressWarnings("rawtypes")
    @Override
    protected void a(IChunkAccess ichunkaccess, Random random) {
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
        int i = ichunkaccess.getPos().d();
        int j = ichunkaccess.getPos().e();
        GeneratorSettingsNether t0 = this.getSettings(); // TallNether: Originally type T
        int k = t0.u();
        int l = t0.t();
        Iterator iterator = BlockPosition.b(i, 0, j, i + 15, 0, j + 15).iterator();

        while (iterator.hasNext()) {
            BlockPosition blockposition = (BlockPosition) iterator.next();
            int i1;

            if (l > 0) {
                for (i1 = l; i1 >= l - 4; --i1) {
                    if (i1 >= l - (this.configValues.flatBedrockCeiling ? 0 : random.nextInt(5))) {
                        ichunkaccess.setType(blockposition_mutableblockposition.d(blockposition.getX(), i1, blockposition.getZ()), Blocks.BEDROCK.getBlockData(), false);
                    }
                }
            }

            if (k < 256) {
                for (i1 = k + 4; i1 >= k; --i1) {
                    if (i1 <= k + (this.configValues.flatBedrockCeiling ? 0 : random.nextInt(5))) {
                        ichunkaccess.setType(blockposition_mutableblockposition.d(blockposition.getX(), i1, blockposition.getZ()), Blocks.BEDROCK.getBlockData(), false);
                    }
                }
            }
        }

    }

    // Unsure if farlands can still be done?
    /*
    // TallNether: Taken from ChunkGeneratorAbstract for NoiseGeneratorOctaves
    private double a(int i, int j, int k, double d0, double d1, double d2, double d3) {
        double d4 = 0.0D;
        double d5 = 0.0D;
        double d6 = 0.0D;
        double d7 = 1.0D;

        for (int l = 0; l < 16; ++l) {
            double d8 = TallNether_NoiseGeneratorOctaves.a((double) i * d0 * d7);
            double d9 = TallNether_NoiseGeneratorOctaves.a((double) j * d1 * d7);
            double d10 = TallNether_NoiseGeneratorOctaves.a((double) k * d0 * d7);
            double d11 = d1 * d7;

            d4 += this.o.a(l).a(d8, d9, d10, d11, (double) j * d11) / d7;
            d5 += this.p.a(l).a(d8, d9, d10, d11, (double) j * d11) / d7;
            if (l < 8) {
                d6 += this.q.a(l).a(NoiseGeneratorOctaves.a((double) i * d2 * d7), NoiseGeneratorOctaves.a((double) j * d3 * d7), TallNether_NoiseGeneratorOctaves.a((double) k * d2 * d7), d3 * d7, (double) j * d3 * d7) / d7;
            }

            d7 /= 2.0D;
        }

        return MathHelper.b(d4 / 512.0D, d5 / 512.0D, (d6 / 10.0D + 1.0D) / 2.0D);
    }

    // TallNether: Overrides from ChunkGeneratorAbstract for NoiseGeneratorOctaves
    @Override
    protected void a(double[] adouble, int i, int j, double d0, double d1, double d2, double d3, int k, int l) {
        double[] adouble1 = this.a(i, j);
        double d4 = adouble1[0];
        double d5 = adouble1[1];
        double d6 = this.g();
        double d7 = this.h();

        for (int i1 = 0; i1 < this.i(); ++i1) {
            double d8 = this.a(i, i1, j, d0, d1, d2, d3);

            d8 -= this.a(d4, d5, i1);
            if ((double) i1 > d6) {
                d8 = MathHelper.b(d8, (double) l, ((double) i1 - d6) / (double) k);
            } else if ((double) i1 < d7) {
                d8 = MathHelper.b(d8, -30.0D, (d7 - (double) i1) / (d7 - 1.0D));
            }

            adouble[i1] = d8;
        }

    }

    // TallNether: Overrides from ChunkGeneratorAbstract for NoiseGeneratorOctaves
    @Override
    public void buildBase(IChunkAccess ichunkaccess) {
        ChunkCoordIntPair chunkcoordintpair = ichunkaccess.getPos();
        int i = chunkcoordintpair.x;
        int j = chunkcoordintpair.z;
        SeededRandom seededrandom = new SeededRandom();

        seededrandom.a(i, j);
        ChunkCoordIntPair chunkcoordintpair1 = ichunkaccess.getPos();
        int k = chunkcoordintpair1.d();
        int l = chunkcoordintpair1.e();
        double d0 = 0.0625D;
        BiomeBase[] abiomebase = ichunkaccess.getBiomeIndex();

        for (int i1 = 0; i1 < 16; ++i1) {
            for (int j1 = 0; j1 < 16; ++j1) {
                int k1 = k + i1;
                int l1 = l + j1;
                int i2 = ichunkaccess.a(HeightMap.Type.WORLD_SURFACE_WG, i1, j1) + 1;
                double d1 = this.r.a((double) k1 * 0.0625D, (double) l1 * 0.0625D, 0.0625D, (double) i1 * 0.0625D);

                abiomebase[j1 * 16 + i1].a(seededrandom, ichunkaccess, k1, l1, i2, d1, this.getSettings().r(), this.getSettings().s(), this.getSeaLevel(), this.a.getSeed());
            }
        }

        this.a(ichunkaccess, seededrandom);
    }*/

    /*private double[] a(int i, int j, int k, int l, int i1, int j1) {
        double[] adouble = new double[l * i1 * j1];
        double d0 = 684.412D;
        double d1 = 2053.236D;

        this.m.a(i, j, k, l, 1, j1, 1.0D, 0.0D, 1.0D);
        this.n.a(i, j, k, l, 1, j1, 100.0D, 0.0D, 100.0D);
        double[] adouble1 = this.k.a(i, j, k, l, i1, j1, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        double[] adouble2 = this.i.a(i, j, k, l, i1, j1, 684.412D, 2053.236D, 684.412D);
        double[] adouble3 = this.j.a(i, j, k, l, i1, j1, 684.412D, 2053.236D, 684.412D);
        double[] adouble4 = new double[i1];

        int k1;

        for (k1 = 0; k1 < i1; ++k1) {
            adouble4[k1] = Math.cos((double) k1 * 3.141592653589793D * 6.0D / (double) i1) * 2.0D;
            double d2 = (double) k1;

            if (k1 > i1 / 2) {
                d2 = (double) (i1 - 1 - k1);
            }

            if (d2 < 4.0D) {
                d2 = 4.0D - d2;
                adouble4[k1] -= d2 * d2 * d2 * 10.0D;
            }
        }

        k1 = 0;

        for (int l1 = 0; l1 < l; ++l1) {
            for (int i2 = 0; i2 < j1; ++i2) {
                double d3 = 0.0D;

                for (int j2 = 0; j2 < i1; ++j2) {
                    double d4 = adouble4[j2];
                    double d5 = adouble2[k1] / 512.0D;
                    double d6 = adouble3[k1] / 512.0D;
                    double d7 = (adouble1[k1] / 10.0D + 1.0D) / 2.0D;
                    double d8;

                    if (d7 < 0.0D) {
                        d8 = d5;
                    } else if (d7 > 1.0D) {
                        d8 = d6;
                    } else {
                        d8 = d5 + (d6 - d5) * d7;
                    }

                    d8 -= d4;
                    double d9;

                    if (j2 > i1 - 4) {
                        d9 = (double) ((float) (j2 - (i1 - 4)) / 3.0F);
                        d8 = d8 * (1.0D - d9) - 10.0D * d9;
                    }

                    if ((double) j2 < 0.0D) {
                        d9 = (0.0D - (double) j2) / 4.0D;
                        d9 = MathHelper.a(d9, 0.0D, 1.0D);
                        d8 = d8 * (1.0D - d9) - 10.0D * d9;
                    }

                    adouble[k1] = d8;
                    ++k1;
                }
            }
        }

        return adouble;
    }*/
}
