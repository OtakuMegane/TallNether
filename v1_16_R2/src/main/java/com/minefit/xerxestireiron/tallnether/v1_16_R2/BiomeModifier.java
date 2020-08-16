package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.lang.reflect.Field;

import net.minecraft.server.v1_16_R2.BiomeBase;
import net.minecraft.server.v1_16_R2.BiomeSettingsGeneration;

public class BiomeModifier {

    public boolean injectSettings(BiomeBase biomeBase, BiomeSettingsGeneration biomeSettingsGeneration) {
        try {
            Field f = biomeBase.getClass().getDeclaredField("k");
            f.setAccessible(true);
            ReflectionHelper.setFinal(f, biomeBase, biomeSettingsGeneration);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
