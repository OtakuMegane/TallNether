package com.minefit.xerxestireiron.tallnether.v1_18_R1.Decorators;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public abstract class TallNether_RepeatingPlacement extends PlacementModifier {

    protected abstract int count(Random var1, BlockPos var2);

    protected abstract int count(PlacementContext var0, Random var1, BlockPos var2);

    public Stream<BlockPos> getPositions(PlacementContext var0, Random var1, BlockPos var2) {
        return IntStream.range(0, this.count(var0, var1, var2)).mapToObj((i) -> {
            return var2;
        });
    }
}
