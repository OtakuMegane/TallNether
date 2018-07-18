package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import javax.annotation.Nullable;

import org.bukkit.configuration.ConfigurationSection;

import net.minecraft.server.v1_13_R1.BiomeLayout;
import net.minecraft.server.v1_13_R1.BiomeLayoutFixedConfiguration;
import net.minecraft.server.v1_13_R1.Biomes;
import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.Blocks;
import net.minecraft.server.v1_13_R1.ChunkCoordIntPair;
import net.minecraft.server.v1_13_R1.ChunkGenerator;
import net.minecraft.server.v1_13_R1.ChunkGeneratorType;
import net.minecraft.server.v1_13_R1.DimensionManager;
import net.minecraft.server.v1_13_R1.GeneratorSettingsNether;
import net.minecraft.server.v1_13_R1.WorldBorder;
import net.minecraft.server.v1_13_R1.WorldProvider;
import net.minecraft.server.v1_13_R1.WorldProviderHell;

public class TallNether_WorldProviderHell extends WorldProvider {

    private final ConfigurationSection worldConfig;

    public TallNether_WorldProviderHell(ConfigurationSection worldConfig) {
        this.worldConfig = worldConfig;
    }

    public void m() {
        this.c = true;
        this.d = false;
        this.e = false;
    }

    protected void a() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float) i / 15.0F;

            this.f[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
        }

    }

    public ChunkGenerator<?> getChunkGenerator() {
        //GeneratorSettingsNether generatorsettingsnether = (GeneratorSettingsNether) ChunkGeneratorType.c.a();
        GeneratorSettingsNether generatorsettingsnether = (GeneratorSettingsNether) new TallNether_GeneratorSettingsNether();

        generatorsettingsnether.a(Blocks.NETHERRACK.getBlockData());
        generatorsettingsnether.b(Blocks.LAVA.getBlockData());
        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(this.b,
                BiomeLayout.c.a(((BiomeLayoutFixedConfiguration) BiomeLayout.c.a()).a(Biomes.j)),
                generatorsettingsnether, this.worldConfig);
        return tallNetherGenerator;
        //return ChunkGeneratorType.c.create(this.b, BiomeLayout.c.a(((BiomeLayoutFixedConfiguration) BiomeLayout.c.a()).a(Biomes.j)), generatorsettingsnether);
    }

    public boolean o() {
        return false;
    }

    @Nullable
    public BlockPosition a(ChunkCoordIntPair chunkcoordintpair, boolean flag) {
        return null;
    }

    @Nullable
    public BlockPosition a(int i, int j, boolean flag) {
        return null;
    }

    public float a(long i, float f) {
        return 0.5F;
    }

    public boolean p() {
        return false;
    }

    public WorldBorder getWorldBorder() {
        return new WorldBorder() {
            public double getCenterX() {
                return super.getCenterX(); // CraftBukkit
            }

            public double getCenterZ() {
                return super.getCenterZ(); // CraftBukkit
            }
        };
    }

    public DimensionManager getDimensionManager() {
        return DimensionManager.NETHER;
    }
}
