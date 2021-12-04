package com.minefit.xerxestireiron.tallnether.v1_18_R1.BiomeModifiers;

import java.lang.reflect.Field;

import com.minefit.xerxestireiron.tallnether.ReflectionHelper;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;

public class BiomeModifier {

    public boolean injectSettings(Biome biome, BiomeGenerationSettings biomeGenerationSettings) {
        try {
            Field e = biome.getGenerationSettings().getClass().getDeclaredField("e");
            ReflectionHelper.fieldSetter(e, biome.getGenerationSettings(), biomeGenerationSettings.features());
        } catch (Throwable t) {
            return false;
        }

        return true;
    }
}