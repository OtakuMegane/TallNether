package com.minefit.xerxestireiron.tallnether.v1_16_R3.BiomeModifiers;

import java.lang.reflect.Field;

import com.minefit.xerxestireiron.tallnether.ReflectionHelper;

import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.BiomeSettingsGeneration;

public class BiomeModifier {

    public boolean injectSettings(BiomeBase biomeBase, BiomeSettingsGeneration biomeSettingsGeneration) {
        try {
            Field f = biomeBase.getClass().getDeclaredField("k");
            f.setAccessible(true);
            ReflectionHelper.fieldSetter(f, biomeBase, biomeSettingsGeneration);
        } catch (Throwable t) {
            return false;
        }

        return true;
    }
}
