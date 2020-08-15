package com.minefit.xerxestireiron.tallnether.v1_16_R2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionHelper {
    public static Field getField(Class<?> baseClass, String fieldName, boolean declared) throws NoSuchFieldException {
        Field field;

        try {
            if (declared) {
                field = baseClass.getDeclaredField(fieldName);
            } else {
                field = baseClass.getField(fieldName);
            }
        } catch (NoSuchFieldException e) {
            Class<?> superClass = baseClass.getSuperclass();

            if (superClass != null) {
                field = ReflectionHelper.getField(superClass, fieldName, declared);
            } else {
                throw e;
            }
        }

        return field;
    }

    public static void setFinal(Field field, Object instance, Object obj) throws Exception {
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(instance, obj);
    }
}
