package com.minefit.xerxestireiron.tallnether.v1_17_R1.BiomeModifiers;

import java.lang.reflect.Field;

import com.minefit.xerxestireiron.tallnether.ReflectionHelper;

import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;

public class BiomeModifier {

    public boolean injectSettings(BiomeBase biomeBase, BiomeSettingsGeneration biomeSettingsGeneration) {
        try {
            Field l = biomeBase.getClass().getDeclaredField("l"); // generationSettings
            ReflectionHelper.fieldSetter(l, biomeBase, biomeSettingsGeneration);
        } catch (Throwable t) {
            return false;
        }

        return true;
    }
}
