package com.minefit.XerxesTireIron.TallNether.v1_11_R1;

import java.util.Random;

import net.minecraft.server.v1_11_R1.MathHelper;
import net.minecraft.server.v1_11_R1.NoiseGeneratorOctaves;
import net.minecraft.server.v1_11_R1.NoiseGeneratorPerlin;

public class TallNether_NoiseGeneratorOctaves extends NoiseGeneratorOctaves {
    private NoiseGeneratorPerlin[] a;
    private int b;

    public TallNether_NoiseGeneratorOctaves(Random random, int i) {
        super(random, i);
        this.b = i;
        this.a = new NoiseGeneratorPerlin[i];

        for (int j = 0; j < i; ++j) {
            this.a[j] = new NoiseGeneratorPerlin(random);
        }
    }

    @Override
    public double[] a(double[] adouble, int i, int j, int k, int l, int i1, int j1, double d0, double d1, double d2) {
        if (adouble == null) {
            adouble = new double[l * i1 * j1];
        } else {
            for (int k1 = 0; k1 < adouble.length; ++k1) {
                adouble[k1] = 0.0D;
            }
        }

        double d3 = 1.0D;

        for (int l1 = 0; l1 < this.b; ++l1) {
            double d4 = (double) i * d3 * d0;
            double d5 = (double) j * d3 * d1;
            double d6 = (double) k * d3 * d2;
            long i2 = MathHelper.d(d4);
            long j2 = MathHelper.d(d6);

            d4 -= (double) i2;
            d6 -= (double) j2;
            // i2 %= 16777216L;
            // j2 %= 16777216L;
            d4 += (double) i2;
            d6 += (double) j2;
            this.a[l1].a(adouble, d4, d5, d6, l, i1, j1, d0 * d3, d1 * d3, d2 * d3, d3);
            d3 /= 2.0D;
        }

        return adouble;
    }
}
