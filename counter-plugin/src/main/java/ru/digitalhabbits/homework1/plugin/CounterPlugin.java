package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {
    private final Pattern pattern = Pattern.compile("(\\b[a-zA-Z][a-zA-Z.0-9]*\\b)");


    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        //String[] countWord=text.split("\\s+");
        var countWord = pattern.matcher(text).results().count();
        String[] countRow = text.split("\\n");
        var letter = text.chars().filter(Character::isLetter).count();
        return countRow.length + ";"+countWord+";"+letter;
    }
}
