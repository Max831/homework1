package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        String[] countWord=text.split("\\s+");
        String[] countRow = text.split("\\n");
        return countRow.length + ";"+countWord.length+";"+text.length();
    }
}
