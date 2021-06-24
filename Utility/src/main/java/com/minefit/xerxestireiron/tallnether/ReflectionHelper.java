package com.minefit.xerxestireiron.tallnether;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
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

    public static void fieldSetter(Field field, Object instance, Object obj) throws Throwable {
        try {
            field.setAccessible(true);
            Field modifiers = Field.class.getDeclaredField("modifiers"); // This fails in Java 12+
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(instance, obj);
        } catch (Exception e) {
            try {
                Lookup lookup = MethodHandles.lookup();
                MethodHandle handle = lookup.unreflectSetter(field);
                handle.invoke(instance, obj);
            } catch (Throwable t) {
                try {
                    // It's not good using this but sometimes necessary as last resort
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    Unsafe unsafe = (Unsafe) theUnsafe.get(null);
                    long offset = 0;

                    if (Modifier.isStatic(field.getModifiers())) {
                        offset = unsafe.staticFieldOffset(field);
                    } else {
                        offset = unsafe.objectFieldOffset(field);
                    }

                    unsafe.putObject(instance, offset, obj);
                } catch (Throwable t1) {
                    throw t1;
                }
            }
        }
    }
}