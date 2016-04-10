package com.minefit.XerxesTireIron.TallNether.v1_8_R2;

import java.util.Random;

import com.minefit.XerxesTireIron.TallNether.TallNether;

import net.minecraft.server.v1_8_R2.MathHelper;
import net.minecraft.server.v1_8_R2.NoiseGeneratorOctaves;
import net.minecraft.server.v1_8_R2.NoiseGeneratorPerlin;
import net.minecraft.server.v1_8_R2.World;

public class TallNether_NoiseGeneratorOctaves extends NoiseGeneratorOctaves {
    private NoiseGeneratorPerlin[] a;
    private int b;
    private TallNether plugin;
    private boolean enabled;

    public TallNether_NoiseGeneratorOctaves(Random random, int i) {
        super(random, i);
        this.b = i;
        this.a = new NoiseGeneratorPerlin[i];

        for (int j = 0; j < i; ++j) {
            this.a[j] = new NoiseGeneratorPerlin(random);
        }
    }

    public TallNether_NoiseGeneratorOctaves(World world, TallNether instance, Random random, int i) {
        this(random, i);
        this.plugin = instance;
        this.enabled = plugin.getConfig().getBoolean("worlds." + world.getWorldData().getName() + ".farlands", false);
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

            if ((i < 3137704 && i > -3137704) || !this.enabled) {
                i2 %= 16777216L;
            }

            if ((k < 3137704 && k > -3137704) || !this.enabled) {
                j2 %= 16777216L;
            }

            d4 += (double) i2;
            d6 += (double) j2;
            this.a[l1].a(adouble, d4, d5, d6, l, i1, j1, d0 * d3, d1 * d3, d2 * d3, d3);
            d3 /= 2.0D;
        }

        return adouble;
    }
}
