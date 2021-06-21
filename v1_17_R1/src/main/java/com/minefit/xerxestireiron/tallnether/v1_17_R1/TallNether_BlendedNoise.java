package com.minefit.xerxestireiron.tallnether.v1_17_R1;

import java.util.stream.IntStream;

import com.minefit.xerxestireiron.tallnether.WorldConfig;

import net.minecraft.util.MathHelper;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorOctaves;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorPerlin;

public class TallNether_BlendedNoise extends BlendedNoise {

    private final NoiseGeneratorOctaves minLimitNoise;
    private final NoiseGeneratorOctaves maxLimitNoise;
    private final NoiseGeneratorOctaves mainNoise;
    private final WorldConfig worldConfig;
    private final int lowX;
    private final int lowZ;
    private final int highX;
    private final int highZ;

    public TallNether_BlendedNoise(NoiseGeneratorOctaves noisegeneratoroctaves, NoiseGeneratorOctaves noisegeneratoroctaves1, NoiseGeneratorOctaves noisegeneratoroctaves2, WorldConfig worldConfig, int divisor) {
        super(noisegeneratoroctaves, noisegeneratoroctaves1, noisegeneratoroctaves2);
        this.minLimitNoise = noisegeneratoroctaves;
        this.maxLimitNoise = noisegeneratoroctaves1;
        this.mainNoise = noisegeneratoroctaves2;
        this.worldConfig = worldConfig;

        if(this.worldConfig != null) {
            this.highX = worldConfig.getWorldValues().farLandsHighX / divisor;
            this.highZ = worldConfig.getWorldValues().farLandsHighZ / divisor;
            this.lowX = worldConfig.getWorldValues().farLandsLowX / divisor;
            this.lowZ = worldConfig.getWorldValues().farLandsLowZ / divisor;
        } else {
            this.highX = Integer.MAX_VALUE;
            this.highZ = Integer.MAX_VALUE;
            this.lowX = Integer.MIN_VALUE;
            this.lowZ = Integer.MIN_VALUE;
        }
    }

    public TallNether_BlendedNoise(NoiseGeneratorOctaves noisegeneratoroctaves, NoiseGeneratorOctaves noisegeneratoroctaves1, NoiseGeneratorOctaves noisegeneratoroctaves2) {
        //this.minLimitNoise = noisegeneratoroctaves;
        //this.maxLimitNoise = noisegeneratoroctaves1;
        //this.mainNoise = noisegeneratoroctaves2;
        this(noisegeneratoroctaves2, noisegeneratoroctaves2, noisegeneratoroctaves2, null, 4);
    }

    public TallNether_BlendedNoise(RandomSource randomsource) {
        this(new NoiseGeneratorOctaves(randomsource, IntStream.rangeClosed(-15, 0)), new NoiseGeneratorOctaves(randomsource, IntStream.rangeClosed(-15, 0)), new NoiseGeneratorOctaves(randomsource, IntStream.rangeClosed(-7, 0)));
    }

    public double a(int i, int j, int k, double d0, double d1, double d2, double d3) {
        double d4 = 0.0D;
        double d5 = 0.0D;
        double d6 = 0.0D;
        boolean flag = true;
        double d7 = 1.0D;

        // FarLandsAgain: We inject a multiplier to force the integer overflow
        int iMultiX = 1;
        int kMultiZ = 1;

        if (i >= this.highX || i <= this.lowX) {
            iMultiX = 3137706;
        }

        if (k >= this.highZ || k <= this.lowZ) {
            kMultiZ = 3137706;
        }

        for (int l = 0; l < 8; ++l) {
            NoiseGeneratorPerlin noisegeneratorperlin = this.mainNoise.a(l);

            if (noisegeneratorperlin != null) {
                d6 += noisegeneratorperlin.a(NoiseGeneratorOctaves.a((double) i * d2 * d7), NoiseGeneratorOctaves.a((double) j * d3 * d7), NoiseGeneratorOctaves.a((double) k * d2 * d7), d3 * d7, (double) j * d3 * d7) / d7;
            }

            d7 /= 2.0D;
        }

        double d8 = (d6 / 10.0D + 1.0D) / 2.0D;
        boolean flag1 = d8 >= 1.0D;
        boolean flag2 = d8 <= 0.0D;

        d7 = 1.0D;

        for (int i1 = 0; i1 < 16; ++i1) {
            double d9 = NoiseGeneratorOctaves.a((double) i * d0 * d7);
            double d10 = NoiseGeneratorOctaves.a((double) j * d1 * d7);
            double d11 = NoiseGeneratorOctaves.a((double) k * d0 * d7);
            double d12 = d1 * d7;
            NoiseGeneratorPerlin noisegeneratorperlin1;

            // Inject extra multiplier here. This is so we can control where the Far Lands start.
            // Otherwise we would just undo the clamping in NoiseGeneratorOctaves.a(double)
            if (!flag1) {
                noisegeneratorperlin1 = this.minLimitNoise.a(i1);
                if (noisegeneratorperlin1 != null) {
                    d4 += noisegeneratorperlin1.a(d9 * iMultiX, d10, d11 * kMultiZ, d12, (double) j * d12) / d7;
                }
            }

            if (!flag2) {
                noisegeneratorperlin1 = this.maxLimitNoise.a(i1);
                if (noisegeneratorperlin1 != null) {
                    d5 += noisegeneratorperlin1.a(d9 * iMultiX, d10, d11 * kMultiZ, d12, (double) j * d12) / d7;
                }
            }

            d7 /= 2.0D;
        }

        return MathHelper.b(d4 / 512.0D, d5 / 512.0D, d8);
    }
}

