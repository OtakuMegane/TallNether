package com.minefit.xerxestireiron.tallnether;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
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
        try {
            field.setAccessible(true);
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(instance, obj);
        } catch (Exception e) {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                Unsafe unsafe = (Unsafe) theUnsafe.get(null);
                long offset = unsafe.objectFieldOffset(field);
                unsafe.putObject(instance, offset, obj);
            } catch (Exception e1) {
                throw e1;
            }
        }
    }
}