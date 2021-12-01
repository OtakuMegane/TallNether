package com.minefit.xerxestireiron.tallnether.v1_18_R1.Decorators;

import java.util.Random;
import java.util.stream.Stream;

import com.minefit.xerxestireiron.tallnether.BiomeValues;
import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementContext;

public class TallNether_InSquarePlacement extends InSquarePlacement {

    private final String block;
    private final String biome;
    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_InSquarePlacement(String block, String biome) {
        this.block = block;
        this.biome = biome;
    }

    public Stream<BlockPos> getPositions(PlacementContext var0, Random var1, BlockPos var2) {
        String worldName = var0.getLevel().getLevel().getWorld().getName();
        WorldConfig worldConfig = this.configAccessor.getWorldConfig(worldName);
        int var3 = var1.nextInt(16) + var2.getX();
        int var4 = var1.nextInt(16) + var2.getZ();

        if (worldConfig.isVanilla) {
            return Stream.of(new BlockPos(var3, var2.getY(), var4));
        }

        BiomeValues biomeValues = worldConfig.getBiomeValues(this.biome);
        int minHeight = biomeValues.values.get(this.block + "-min-height");
        int maxHeight = biomeValues.values.get(this.block + "-max-height");
        minHeight = minHeight > maxHeight ? maxHeight : minHeight;
        return Stream.of(new BlockPos(var3, Mth.nextInt(var1, minHeight, maxHeight), var4));
    }
}
