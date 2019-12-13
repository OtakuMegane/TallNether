package com.minefit.xerxestireiron.tallnether.v1_15_R1;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.ConfigValues;

import net.minecraft.server.v1_15_R1.BiomeBase;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.Blocks;
import net.minecraft.server.v1_15_R1.ChunkGeneratorAbstract;
import net.minecraft.server.v1_15_R1.EnumCreatureType;
import net.minecraft.server.v1_15_R1.GeneratorSettingsNether;
import net.minecraft.server.v1_15_R1.IChunkAccess;
import net.minecraft.server.v1_15_R1.MathHelper;
import net.minecraft.server.v1_15_R1.NoiseGeneratorOctaves;
import net.minecraft.server.v1_15_R1.SeededRandom;
import net.minecraft.server.v1_15_R1.World;
import net.minecraft.server.v1_15_R1.WorldChunkManager;
import net.minecraft.server.v1_15_R1.WorldGenerator;

public class TallNether_ChunkProviderHell extends ChunkGeneratorAbstract<GeneratorSettingsNether> {

    private final double[] h = this.j();
    private final NoiseGeneratorOctaves o;
    private final NoiseGeneratorOctaves p;
    private final NoiseGeneratorOctaves q;

    private final ConfigAccessor configAccessor = new ConfigAccessor();
    private final ConfigValues configValues;
    private final int lowX;
    private final int lowZ;
    private final int highX;
    private final int highZ;
    private final boolean generateFarLands;

    public TallNether_ChunkProviderHell(World world, WorldChunkManager worldchunkmanager,
            GeneratorSettingsNether generatorsettingsnether, ConfigValues configValues) {
        super(world, worldchunkmanager, 4, 8, 256, generatorsettingsnether, false);
        this.configValues = this.configAccessor.getConfig(world.getWorld().getName());
        this.lowX = configValues.farLandsLowX / 4;
        this.lowZ = configValues.farLandsLowZ / 4;
        this.highX = configValues.farLandsHighX / 4;
        this.highZ = configValues.farLandsHighZ / 4;
        this.generateFarLands = this.configValues.generateFarLands;
        SeededRandom seededrandom = new SeededRandom(this.seed);
        this.o = new NoiseGeneratorOctaves(seededrandom, 15, 0);
        this.p = new NoiseGeneratorOctaves(seededrandom, 15, 0);
        this.q = new NoiseGeneratorOctaves(seededrandom, 7, 0);
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
        return 256;
    }

    @Override
    public int getSeaLevel() {
        return this.configValues.lavaSeaLevel;
    }

    // TallNether: Base from ChunkGeneratorAbstract for creating Far Lands
    private double a(int i, int j, int k, double d0, double d1, double d2, double d3) {

        double d4 = 0.0D;
        double d5 = 0.0D;
        double d6 = 0.0D;
        double d7 = 1.0D;

        int iMultiX = 1;
        int kMultiZ = 1;

        if (this.generateFarLands) {
            if (i >= this.highX || i <= this.lowX) {
                iMultiX = 3137706;
            }

            if (k >= this.highZ || k <= this.lowZ) {
                kMultiZ = 3137706;
            }
        }

        for (int l = 0; l < 16; ++l) {
            double d8 = NoiseGeneratorOctaves.a((double) i * d0 * d7); // same as original d4 (x-based)
            double d9 = NoiseGeneratorOctaves.a((double) j * d1 * d7);
            double d10 = NoiseGeneratorOctaves.a((double) k * d0 * d7); // same as original d6 (z-based)
            double d11 = d1 * d7;

            d4 += this.o.a(l).a(d8 * iMultiX, d9, d10 * kMultiZ, d11, (double) j * d11) / d7;
            d5 += this.p.a(l).a(d8 * iMultiX, d9, d10 * kMultiZ, d11, (double) j * d11) / d7;
            if (l < 8) {
                d6 += this.q.a(l).a(NoiseGeneratorOctaves.a((double) i * d2 * d7), NoiseGeneratorOctaves.a((double) j * d3 * d7), NoiseGeneratorOctaves.a((double) k * d2 * d7), d3 * d7, (double) j * d3 * d7) / d7;
            }

            d7 /= 2.0D;
        }

        return MathHelper.b(d4 / 512.0D, d5 / 512.0D, (d6 / 10.0D + 1.0D) / 2.0D);
    }

    // TallNether: Override for Far Lands
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
}
