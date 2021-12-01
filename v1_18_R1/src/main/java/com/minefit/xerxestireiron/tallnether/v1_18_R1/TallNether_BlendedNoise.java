package com.minefit.xerxestireiron.tallnether.v1_18_R1;

import com.google.common.annotations.VisibleForTesting;
import com.minefit.xerxestireiron.tallnether.WorldConfig;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class TallNether_BlendedNoise extends BlendedNoise {
    private final PerlinNoise minLimitNoise;
    private final PerlinNoise maxLimitNoise;
    private final PerlinNoise mainNoise;
    private final double xzScale;
    private final double yScale;
    private final double xzMainScale;
    private final double yMainScale;
    private final int cellWidth;
    private final int cellHeight;
    private final WorldConfig worldConfig;
    private final int lowX;
    private final int lowZ;
    private final int highX;
    private final int highZ;

    public TallNether_BlendedNoise(PerlinNoise var0, PerlinNoise var1, PerlinNoise var2, NoiseSamplingSettings var3, int var4,
            int var5, RandomSource randomSource, WorldConfig worldConfig, int divisor) {
        super(randomSource, var3, var5, var5);
        this.minLimitNoise = var0;
        this.maxLimitNoise = var1;
        this.mainNoise = var2;
        this.xzScale = 684.412D * var3.xzScale();
        this.yScale = 684.412D * var3.yScale();
        this.xzMainScale = this.xzScale / var3.xzFactor();
        this.yMainScale = this.yScale / var3.yFactor();
        this.cellWidth = var4;
        this.cellHeight = var5;
        this.worldConfig = worldConfig;

        if(this.worldConfig != null) {
            this.highX = worldConfig.getWorldValues().farLandsHighX / 1;
            this.highZ = worldConfig.getWorldValues().farLandsHighZ / 1;
            this.lowX = worldConfig.getWorldValues().farLandsLowX / 1;
            this.lowZ = worldConfig.getWorldValues().farLandsLowZ / 1;
        } else {
            this.highX = Integer.MAX_VALUE;
            this.highZ = Integer.MAX_VALUE;
            this.lowX = Integer.MIN_VALUE;
            this.lowZ = Integer.MIN_VALUE;
        }
    }

    @SuppressWarnings("deprecation")
    public double calculateNoise(int var0, int var1, int var2) {
        int var3 = Math.floorDiv(var0, this.cellWidth);
        int var4 = Math.floorDiv(var1, this.cellHeight);
        int var5 = Math.floorDiv(var2, this.cellWidth);
        double var6 = 0.0D;
        double var8 = 0.0D;
        double var10 = 0.0D;
        boolean var12 = true;
        double var13 = 1.0D;

        // FarLandsAgain: We inject a multiplier to force the integer overflow
        int MultiX = 1;
        int MultiZ = 1;

        if (var0 >= this.highX || var0 <= this.lowX) {
            MultiX = 3137706;
        }

        if (var2 >= this.highZ || var2 <= this.lowZ) {
            MultiZ = 3137706;
        }

        for (int var15 = 0; var15 < 8; ++var15) {
            ImprovedNoise var16 = this.mainNoise.getOctaveNoise(var15);
            if (var16 != null) {
                var10 += var16.noise(PerlinNoise.wrap((double) var3 * this.xzMainScale * var13),
                        PerlinNoise.wrap((double) var4 * this.yMainScale * var13),
                        PerlinNoise.wrap((double) var5 * this.xzMainScale * var13), this.yMainScale * var13,
                        (double) var4 * this.yMainScale * var13) / var13;
            }

            var13 /= 2.0D;
        }

        double var15 = (var10 / 10.0D + 1.0D) / 2.0D;
        boolean var17 = var15 >= 1.0D;
        boolean var18 = var15 <= 0.0D;
        var13 = 1.0D;

        for (int var19 = 0; var19 < 16; ++var19) {
            double var20 = PerlinNoise.wrap((double) var3 * this.xzScale * var13);
            double var22 = PerlinNoise.wrap((double) var4 * this.yScale * var13);
            double var24 = PerlinNoise.wrap((double) var5 * this.xzScale * var13);
            double var26 = this.yScale * var13;
            ImprovedNoise var28;

            // FarLandsAgain: Inject extra multiplier here. This is so we can control where the Far Lands start.
            // Otherwise we would just block the clamping in PerlinNoise.wrap(double)
            if (!var17) {
                var28 = this.minLimitNoise.getOctaveNoise(var19);
                if (var28 != null) {
                    var6 += var28.noise(var20 * MultiX, var22, var24 * MultiZ, var26, (double) var4 * var26) / var13;
                }
            }

            if (!var18) {
                var28 = this.maxLimitNoise.getOctaveNoise(var19);
                if (var28 != null) {
                    var8 += var28.noise(var20 * MultiX, var22, var24 * MultiZ, var26, (double) var4 * var26) / var13;
                }
            }

            var13 /= 2.0D;
        }

        return Mth.clampedLerp(var6 / 512.0D, var8 / 512.0D, var15) / 128.0D;
    }

    @VisibleForTesting
    public void parityConfigString(StringBuilder var0) {
        var0.append("BlendedNoise{minLimitNoise=");
        this.minLimitNoise.parityConfigString(var0);
        var0.append(", maxLimitNoise=");
        this.maxLimitNoise.parityConfigString(var0);
        var0.append(", mainNoise=");
        this.mainNoise.parityConfigString(var0);
        var0.append(String.format(
                ", xzScale=%.3f, yScale=%.3f, xzMainScale=%.3f, yMainScale=%.3f, cellWidth=%d, cellHeight=%d",
                this.xzScale, this.yScale, this.xzMainScale, this.yMainScale, this.cellWidth, this.cellHeight))
                .append('}');
    }
}