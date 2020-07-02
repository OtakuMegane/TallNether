package com.minefit.xerxestireiron.tallnether.v1_16_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import net.minecraft.server.v1_16_R1.IRegistry;
import net.minecraft.server.v1_16_R1.StructureGenerator;
import net.minecraft.server.v1_16_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_16_R1.WorldGenStage;

public class RegisterFortress {

    private StructureGenerator<WorldGenFeatureEmptyConfiguration> vanilla_fortress = StructureGenerator.FORTRESS;

    public RegisterFortress() {

    }

    public boolean set(boolean restore) {
        StructureGenerator<WorldGenFeatureEmptyConfiguration> fortressGen;

        if (restore) {
            fortressGen = this.vanilla_fortress;
        } else {
            fortressGen = new TallNether_WorldGenNether(WorldGenFeatureEmptyConfiguration.a);
        }

        try {
            Class<?>[] methodParams = new Class<?>[] { String.class, StructureGenerator.class,
                    WorldGenStage.Decoration.class };
                    Method[] methods = StructureGenerator.class.getDeclaredMethods();
            Method methodA = StructureGenerator.class.getDeclaredMethod("a", methodParams);
            methodA.setAccessible(true);
            Object[] methodValues = new Object[] { "Fortress", fortressGen,
                    WorldGenStage.Decoration.UNDERGROUND_DECORATION };
            methodA.invoke(null, methodValues);

            // We do this since for now we don't get the return value from the above method invoke
            // We would also use sGen to replace for any other variables holding the fortress instance
            StructureGenerator<WorldGenFeatureEmptyConfiguration> sGen = IRegistry.a(IRegistry.STRUCTURE_FEATURE,
                    "Fortress".toLowerCase(Locale.ROOT), fortressGen);

            Field FORTRESS = ReflectionHelper.getField(StructureGenerator.class, "FORTRESS", false);
            ReflectionHelper.setFinal(FORTRESS, null, sGen);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
