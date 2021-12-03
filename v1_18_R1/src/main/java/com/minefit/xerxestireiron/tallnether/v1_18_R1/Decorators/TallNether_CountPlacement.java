package com.minefit.xerxestireiron.tallnether.v1_18_R1.Decorators;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class TallNether_CountPlacement extends TallNether_RepeatingPlacement {
    public static final Codec<TallNether_CountPlacement> CODEC = IntProvider.codec(0, 256).fieldOf("count")
            .xmap(TallNether_CountPlacement::new, (countplacement) -> {
                return countplacement.count;
            }).codec();
    private final IntProvider count;
    private final String block;
    private final String biome;
    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_CountPlacement(IntProvider intProvider, String block, String biome) {
        this.count = intProvider;
        this.block = block;
        this.biome = biome;
    }

    private TallNether_CountPlacement(IntProvider var0) {
        this.count = var0;
        this.block = "";
        this.biome = "";
    }

    public static TallNether_CountPlacement of(IntProvider var0) {
        return new TallNether_CountPlacement(var0);
    }

    public static TallNether_CountPlacement of(int var0) {
        return of(ConstantInt.of(var0));
    }

    protected int count(Random var0, BlockPos var1) {
        return this.count.sample(var0);
    }

    protected int count(PlacementContext var0, Random var1, BlockPos var2) {
        String worldName = var0.getLevel().getMinecraftWorld().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);

        if (worldConfig.isVanilla) {
            return count(var1, var2);
        }

        BiomeValues biomeValues = worldConfig.getBiomeValues(this.biome);
        IntProvider intProvider = ConstantInt.of(biomeValues.values.get(this.block + "-attempts"));
        System.out.println("here1");
        return intProvider.sample(var1);
    }

    public PlacementModifierType<?> type() {
        return PlacementModifierType.COUNT;
    }
}
