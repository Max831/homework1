package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginEngine {

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
        String result = "";
        try {
            Method applyMethod = cls.getMethod("apply", String.class);
            Class<?> returnType = applyMethod.getReturnType();
            if (returnType == String.class){
                result = (String) applyMethod.invoke(cls.getConstructor().newInstance(),text);
            }
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }
}
