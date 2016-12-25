package com.minefit.XerxesTireIron.TallNether.v1_8_R2;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import com.minefit.XerxesTireIron.TallNether.TallNether;
import com.minefit.XerxesTireIron.TallNether.v1_8_R2.TallNether_WorldGenNether;

import net.minecraft.server.v1_8_R2.BiomeBase;
import net.minecraft.server.v1_8_R2.BlockFalling;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.BlockPredicate;
import net.minecraft.server.v1_8_R2.Blocks;
import net.minecraft.server.v1_8_R2.Chunk;
import net.minecraft.server.v1_8_R2.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R2.ChunkProviderHell;
import net.minecraft.server.v1_8_R2.ChunkSnapshot;
import net.minecraft.server.v1_8_R2.EnumCreatureType;
import net.minecraft.server.v1_8_R2.IBlockData;
import net.minecraft.server.v1_8_R2.IChunkProvider;
import net.minecraft.server.v1_8_R2.Material;
import net.minecraft.server.v1_8_R2.MathHelper;
import net.minecraft.server.v1_8_R2.NoiseGeneratorOctaves;
import net.minecraft.server.v1_8_R2.World;
import net.minecraft.server.v1_8_R2.WorldGenBase;
import net.minecraft.server.v1_8_R2.WorldGenFire;
import net.minecraft.server.v1_8_R2.WorldGenHellLava;
import net.minecraft.server.v1_8_R2.WorldGenLightStone1;
import net.minecraft.server.v1_8_R2.WorldGenLightStone2;
import net.minecraft.server.v1_8_R2.WorldGenMinable;
import net.minecraft.server.v1_8_R2.WorldGenMushrooms;
import net.minecraft.server.v1_8_R2.WorldGenNether;
import net.minecraft.server.v1_8_R2.WorldGenerator;

public class TallNether_ChunkProviderHell extends ChunkProviderHell implements IChunkProvider {
    private final ConfigurationSection worldConfig;
    private final World h;
    private final boolean i;
    private final Random j;
    private double[] k = new double[256];
    private double[] l = new double[256];
    private double[] m = new double[256];
    private double[] n;
    private final NoiseGeneratorOctaves o;
    private final NoiseGeneratorOctaves p;
    private final NoiseGeneratorOctaves q;
    private final NoiseGeneratorOctaves r;
    private final NoiseGeneratorOctaves s;
    public final NoiseGeneratorOctaves a;
    public final NoiseGeneratorOctaves b;
    private final WorldGenFire t = new WorldGenFire();
    private final WorldGenLightStone1 u = new WorldGenLightStone1();
    private final WorldGenLightStone2 v = new WorldGenLightStone2();
    private final WorldGenerator w;
    private final WorldGenHellLava x;
    private final WorldGenHellLava y;
    private final WorldGenMushrooms z;
    private final WorldGenMushrooms A;
    private final WorldGenNether B;
    private final WorldGenBase C;
    double[] c;
    double[] d;
    double[] e;
    double[] f;
    double[] g;

    public TallNether_ChunkProviderHell(World world, boolean flag, long i, ConfigurationSection worldConfig) {
        super(world, flag, i);
        this.worldConfig = worldConfig;
        this.w = new WorldGenMinable(Blocks.QUARTZ_ORE.getBlockData(), 14, BlockPredicate.a(Blocks.NETHERRACK));
        this.x = new WorldGenHellLava(Blocks.FLOWING_LAVA, true);
        this.y = new WorldGenHellLava(Blocks.FLOWING_LAVA, false);
        this.z = new WorldGenMushrooms(Blocks.BROWN_MUSHROOM);
        this.A = new WorldGenMushrooms(Blocks.RED_MUSHROOM);
        this.B = new TallNether_WorldGenNether(world, worldConfig);
        this.C = new TallNether_WorldGenCavesHell();
        this.h = world;
        this.i = flag;
        this.j = new Random(i);

        try {
            Method bb = net.minecraft.server.v1_8_R2.WorldGenFactory.class.getDeclaredMethod("b",
                    new Class[] { Class.class, String.class });
            bb.setAccessible(true);
            bb.invoke(net.minecraft.server.v1_8_R2.WorldGenFactory.class,
                    new Object[] { TallNether_WorldGenNether.WorldGenNetherStart.class, "Fortress" });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.worldConfig.getBoolean("farlands", false)) {
            this.o = new TallNether_NoiseGeneratorOctaves(this.worldConfig, this.j, 16);
            this.p = new TallNether_NoiseGeneratorOctaves(this.worldConfig, this.j, 16);
            this.q = new TallNether_NoiseGeneratorOctaves(this.worldConfig, this.j, 8);
            this.r = new TallNether_NoiseGeneratorOctaves(this.worldConfig, this.j, 4);
            this.s = new TallNether_NoiseGeneratorOctaves(this.worldConfig, this.j, 4);
            this.a = new TallNether_NoiseGeneratorOctaves(this.worldConfig, this.j, 10);
            this.b = new TallNether_NoiseGeneratorOctaves(this.worldConfig, this.j, 16);
            world.b(63);
        } else {
            this.o = new NoiseGeneratorOctaves(this.j, 16);
            this.p = new NoiseGeneratorOctaves(this.j, 16);
            this.q = new NoiseGeneratorOctaves(this.j, 8);
            this.r = new NoiseGeneratorOctaves(this.j, 4);
            this.s = new NoiseGeneratorOctaves(this.j, 4);
            this.a = new NoiseGeneratorOctaves(this.j, 10);
            this.b = new NoiseGeneratorOctaves(this.j, 16);
            world.b(63);
        }
    }

    public void a(int i, int j, ChunkSnapshot chunksnapshot) {
        byte b0 = 4;
        int k = this.h.F() / 2 + 1;
        int l = b0 + 1;
        byte b1 = 33;
        int i1 = b0 + 1;

        this.n = this.a(this.n, i * b0, 0, j * b0, l, b1, i1);

        for (int j1 = 0; j1 < b0; ++j1) {
            for (int k1 = 0; k1 < b0; ++k1) {
                for (int l1 = 0; l1 < 32; ++l1) {
                    double d0 = 0.125D;
                    double d1 = this.n[((j1 + 0) * i1 + k1 + 0) * b1 + l1 + 0];
                    double d2 = this.n[((j1 + 0) * i1 + k1 + 1) * b1 + l1 + 0];
                    double d3 = this.n[((j1 + 1) * i1 + k1 + 0) * b1 + l1 + 0];
                    double d4 = this.n[((j1 + 1) * i1 + k1 + 1) * b1 + l1 + 0];
                    double d5 = (this.n[((j1 + 0) * i1 + k1 + 0) * b1 + l1 + 1] - d1) * d0;
                    double d6 = (this.n[((j1 + 0) * i1 + k1 + 1) * b1 + l1 + 1] - d2) * d0;
                    double d7 = (this.n[((j1 + 1) * i1 + k1 + 0) * b1 + l1 + 1] - d3) * d0;
                    double d8 = (this.n[((j1 + 1) * i1 + k1 + 1) * b1 + l1 + 1] - d4) * d0;

                    for (int i2 = 0; i2 < 8; ++i2) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int j2 = 0; j2 < 4; ++j2) {
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 4; ++k2) {
                                IBlockData iblockdata = null;

                                if (l1 * 8 + i2 < this.worldConfig.getInt("lava-sea-level", 48) + 1) {
                                    iblockdata = Blocks.LAVA.getBlockData();
                                }

                                if (d15 > 0.0D) {
                                    iblockdata = Blocks.NETHERRACK.getBlockData();
                                }

                                int l2 = j2 + j1 * 4;
                                int i3 = i2 + l1 * 8;
                                int j3 = k2 + k1 * 4;

                                chunksnapshot.a(l2, i3, j3, iblockdata);
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

    }

    public void b(int i, int j, ChunkSnapshot chunksnapshot) {
        int k = this.h.F() + 1;

        if (this.worldConfig.getInt("gravel-soulsand-limit", 128) > 0) {
            k = this.worldConfig.getInt("gravel-soulsand-limit", 128);
        }

        double d0 = 0.03125D;

        this.k = this.r.a(this.k, i * 16, j * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.l = this.r.a(this.l, i * 16, 109, j * 16, 16, 1, 16, d0, 1.0D, d0);
        this.m = this.s.a(this.m, i * 16, j * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int l = 0; l < 16; ++l) {
            for (int i1 = 0; i1 < 16; ++i1) {
                boolean flag = this.k[l + i1 * 16] + this.j.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.l[l + i1 * 16] + this.j.nextDouble() * 0.2D > 0.0D;
                int j1 = (int) (this.m[l + i1 * 16] / 3.0D + 3.0D + this.j.nextDouble() * 0.25D);
                int k1 = -1;
                IBlockData iblockdata = Blocks.NETHERRACK.getBlockData();
                IBlockData iblockdata1 = Blocks.NETHERRACK.getBlockData();

                for (int l1 = 255; l1 >= 0; --l1) {
                    if (l1 < 255 - this.j.nextInt(5) && l1 > this.j.nextInt(5)) {
                        IBlockData iblockdata2 = chunksnapshot.a(i1, l1, l);

                        if (iblockdata2.getBlock() != null && iblockdata2.getBlock().getMaterial() != Material.AIR) {
                            if (iblockdata2.getBlock() == Blocks.NETHERRACK) {
                                if (k1 == -1) {
                                    if (j1 <= 0) {
                                        iblockdata = null;
                                        iblockdata1 = Blocks.NETHERRACK.getBlockData();
                                    } else if (l1 >= k - 4 && l1 <= k + 1) {
                                        iblockdata = Blocks.NETHERRACK.getBlockData();
                                        iblockdata1 = Blocks.NETHERRACK.getBlockData();
                                        if (flag1) {
                                            iblockdata = Blocks.GRAVEL.getBlockData();
                                            iblockdata1 = Blocks.NETHERRACK.getBlockData();
                                        }

                                        if (flag) {
                                            iblockdata = Blocks.SOUL_SAND.getBlockData();
                                            iblockdata1 = Blocks.SOUL_SAND.getBlockData();
                                        }
                                    }

                                    if (l1 < k && (iblockdata == null
                                            || iblockdata.getBlock().getMaterial() == Material.AIR)) {
                                        iblockdata = Blocks.LAVA.getBlockData();
                                    }

                                    k1 = j1;
                                    if (l1 >= k - 1) {
                                        chunksnapshot.a(i1, l1, l, iblockdata);
                                    } else {
                                        chunksnapshot.a(i1, l1, l, iblockdata1);
                                    }
                                } else if (k1 > 0) {
                                    --k1;
                                    chunksnapshot.a(i1, l1, l, iblockdata1);
                                }
                            }
                        } else {
                            k1 = -1;
                        }
                    } else {
                        chunksnapshot.a(i1, l1, l, Blocks.BEDROCK.getBlockData());
                    }
                }
            }
        }

    }

    public Chunk getOrCreateChunk(int i, int j) {
        this.j.setSeed((long) i * 341873128712L + (long) j * 132897987541L);
        ChunkSnapshot chunksnapshot = new ChunkSnapshot();

        this.a(i, j, chunksnapshot);
        this.b(i, j, chunksnapshot);
        this.C.a(this, this.h, i, j, chunksnapshot);
        if (this.i) {
            this.B.a(this, this.h, i, j, chunksnapshot);
        }

        Chunk chunk = new Chunk(this.h, chunksnapshot, i, j);
        BiomeBase[] abiomebase = this.h.getWorldChunkManager().getBiomeBlock((BiomeBase[]) null, i * 16, j * 16, 16,
                16);
        byte[] abyte = chunk.getBiomeIndex();

        for (int k = 0; k < abyte.length; ++k) {
            abyte[k] = (byte) abiomebase[k].id;
        }

        chunk.l();
        return chunk;
    }

    private double[] a(double[] adouble, int i, int j, int k, int l, int i1, int j1) {
        if (adouble == null) {
            adouble = new double[l * i1 * j1];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;

        this.f = this.a.a(this.f, i, j, k, l, 1, j1, 1.0D, 0.0D, 1.0D);
        this.g = this.b.a(this.g, i, j, k, l, 1, j1, 100.0D, 0.0D, 100.0D);
        this.c = this.q.a(this.c, i, j, k, l, i1, j1, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.d = this.o.a(this.d, i, j, k, l, i1, j1, d0, d1, d0);
        this.e = this.p.a(this.e, i, j, k, l, i1, j1, d0, d1, d0);
        int k1 = 0;
        double[] adouble1 = new double[i1];

        int l1;

        for (l1 = 0; l1 < i1; ++l1) {
            adouble1[l1] = Math.cos((double) l1 * 3.141592653589793D * 6.0D / (double) i1) * 2.0D;
            double d2 = (double) l1;

            if (l1 > i1 / 2) {
                d2 = (double) (i1 - 1 - l1);
            }

            if (d2 < 4.0D) {
                d2 = 4.0D - d2;
                adouble1[l1] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (l1 = 0; l1 < l; ++l1) {
            for (int i2 = 0; i2 < j1; ++i2) {
                double d3 = 0.0D;

                for (int j2 = 0; j2 < i1; ++j2) {
                    double d4 = 0.0D;
                    double d5 = adouble1[j2];
                    double d6 = this.d[k1] / 512.0D;
                    double d7 = this.e[k1] / 512.0D;
                    double d8 = (this.c[k1] / 10.0D + 1.0D) / 2.0D;

                    if (d8 < 0.0D) {
                        d4 = d6;
                    } else if (d8 > 1.0D) {
                        d4 = d7;
                    } else {
                        d4 = d6 + (d7 - d6) * d8;
                    }

                    d4 -= d5;
                    double d9;

                    if (j2 > i1 - 4) {
                        d9 = (double) ((float) (j2 - (i1 - 4)) / 3.0F);
                        d4 = d4 * (1.0D - d9) + -10.0D * d9;
                    }

                    if ((double) j2 < d3) {
                        d9 = (d3 - (double) j2) / 4.0D;
                        d9 = MathHelper.a(d9, 0.0D, 1.0D);
                        d4 = d4 * (1.0D - d9) + -10.0D * d9;
                    }

                    adouble[k1] = d4;
                    ++k1;
                }
            }
        }

        return adouble;
    }

    private int setDecoration(String setting, int defaultValue) {
        return this.worldConfig.getInt(setting, defaultValue);
    }

    private int randomRange(int min, int max) {
        int range = (max - min);

        if (range <= 0) {
            return max;
        }

        return min + this.j.nextInt(range);
    }

    public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
        BlockFalling.instaFall = true;
        BlockPosition blockposition = new BlockPosition(i * 16, 0, j * 16);
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);

        int lftries = this.setDecoration("lavafall-attempts", 16);
        int lfmin = this.setDecoration("lavafall-min-height", 4);
        int lfmax = this.setDecoration("lavafall-max-height", 248);
        int ftries = this.setDecoration("fire-attempts", 20);
        int fmin = this.setDecoration("fire-min-height", 4);
        int fmax = this.setDecoration("fire-max-height", 248);
        int g1tries = this.setDecoration("glowstone1-attempts", 20);
        int g1min = this.setDecoration("glowstone1-min-height", 4);
        int g1max = this.setDecoration("glowstone1-max-height", 248);
        int g2tries = this.setDecoration("glowstone2-attempts", 20);
        int g2min = this.setDecoration("glowstone2-min-height", 0);
        int g2max = this.setDecoration("glowstone2-max-height", 256);
        int mbtries = this.setDecoration("brown-shroom-attempts", 2);
        int mbmin = this.setDecoration("brown-shroom-min-height", 0);
        int mbmax = this.setDecoration("brown-shroom-max-height", 256);
        int mrtries = this.setDecoration("red-shroom-attempts", 2);
        int mrmin = this.setDecoration("red-shroom-min-height", 0);
        int mrmax = this.setDecoration("red-shroom-max-height", 256);
        int qtries = this.setDecoration("quartz-attempts", 32);
        int qmin = this.setDecoration("quartz-min-height", 10);
        int qmax = this.setDecoration("quartz-max-height", 246);
        int hltries = this.setDecoration("hidden-lava-attempts", 32);
        int hlmin = this.setDecoration("hidden-lava-min-height", 10);
        int hlmax = this.setDecoration("hidden-lava-max-height", 246);

        this.B.a(this.h, this.j, chunkcoordintpair);

        int k;

        if (lftries > 0 && lfmax > 0) {
            for (k = 0; k < lftries; ++k) {
                this.y.generate(this.h, this.j,
                        blockposition.a(this.j.nextInt(16) + 8, randomRange(lfmin, lfmax), this.j.nextInt(16) + 8));
            }
        }

        if (ftries > 0 && fmax > 0) {
            for (k = 0; k < this.j.nextInt(this.j.nextInt(ftries) + 1) + 1; ++k) {
                this.t.generate(this.h, this.j,
                        blockposition.a(this.j.nextInt(16) + 8, randomRange(fmin, fmax), this.j.nextInt(16) + 8));
            }
        }

        if (g1tries > 0 && g1max > 0) {
            for (k = 0; k < this.j.nextInt(this.j.nextInt(g1tries) + 1); ++k) {
                this.u.generate(this.h, this.j,
                        blockposition.a(this.j.nextInt(16) + 8, randomRange(g1min, g1max), this.j.nextInt(16) + 8));
            }
        }

        if (g2tries > 0 && g2max > 0) {
            for (k = 0; k < g2tries; ++k) {
                this.v.generate(this.h, this.j,
                        blockposition.a(this.j.nextInt(16) + 8, randomRange(g2min, g2max), this.j.nextInt(16) + 8));
            }
        }

        if (mbtries > 0 && mbmax > 0) {
            for (k = 0; k < mbtries; ++k) {
                this.z.generate(this.h, this.j,
                        blockposition.a(this.j.nextInt(16) + 8, randomRange(mbmin, mbmax), this.j.nextInt(16) + 8));
            }
        }

        if (mrtries > 0 && mrmax > 0) {

            for (k = 0; k < mrtries; ++k) {
                this.A.generate(this.h, this.j,
                        blockposition.a(this.j.nextInt(16) + 8, randomRange(mrmin, mrmax), this.j.nextInt(16) + 8));
            }
        }

        if (qtries > 0 && qmax > 0) {

            for (k = 0; k < qtries; ++k) {
                this.w.generate(this.h, this.j,
                        blockposition.a(this.j.nextInt(16), randomRange(qmin, qmax), this.j.nextInt(16)));
            }
        }

        if (hltries > 0 && hlmax > 0) {
            for (k = 0; k < hltries; ++k) {
                this.x.generate(this.h, this.j,
                        blockposition.a(this.j.nextInt(16), randomRange(hlmin, hlmax), this.j.nextInt(16)));
            }
        }

        BlockFalling.instaFall = false;
    }

    @Override
    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
        if (enumcreaturetype == EnumCreatureType.MONSTER) {
            if (this.B.b(blockposition)) {
                return this.B.b();
            }

            if (this.B.a(this.h, blockposition)
                    && this.h.getType(blockposition.down()).getBlock() == Blocks.NETHER_BRICK) {
                return this.B.b();
            }
        }

        BiomeBase biomebase = this.h.getBiome(blockposition);

        return biomebase.getMobs(enumcreaturetype);
    }

    @Override
    public BlockPosition findNearestMapFeature(World world, String s, BlockPosition blockposition) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunk, int i, int j) {
        this.B.a(this, this.h, i, j, (ChunkSnapshot) null);
    }

    @Override
    public Chunk getChunkAt(BlockPosition blockposition) {
        return this.getOrCreateChunk(blockposition.getX() >> 4, blockposition.getZ() >> 4);
    }
}
