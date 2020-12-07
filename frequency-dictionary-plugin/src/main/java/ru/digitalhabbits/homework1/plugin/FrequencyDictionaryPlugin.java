package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    private final Pattern pattern = Pattern.compile("(\\b[a-zA-Z][a-zA-Z.0-9]*\\b)");

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        Map<String, Long> wordToCount = new TreeMap<>();
        wordToCount.putAll(
                pattern.matcher(text.toLowerCase()).results()
                        .map(MatchResult::group)
                        .collect(Collectors.groupingBy(key -> key, Collectors.counting())));

        StringBuilder builder = new StringBuilder();
        wordToCount.forEach((key, value) ->
                builder.append(key).append(" ").append(value).append("\n"));

        return builder.toString();
    }
}
