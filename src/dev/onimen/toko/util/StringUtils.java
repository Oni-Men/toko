package dev.onimen.toko.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern SPLIT_PATTERN = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");
    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("([A-Z][a-z]*)");

    public enum CapitalizeType {
        UPPER_ALL,
        LOWER_ALL,
        UPPER_FIRST
    }

    public static List<String> richSplit(String string) {
        var result = new ArrayList<String>();
        var matcher = SPLIT_PATTERN.matcher(string);
        while (matcher.find()) {
            var token = matcher.group(1);
            if (token.startsWith("\"")) {
                token = token.substring(1);
            }
            if (token.endsWith("\"")) {
                token = token.substring(0, token.length() - 1);
            }
            result.add(token);
        }
        return result;
    }

    public static boolean isNullOrEmpty(String string) {
        if (string == null) {
            return false;
        }
        return string.isEmpty();
    }

    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String toSnakeCase(String string) {
        return toSnakeCase(string, CapitalizeType.LOWER_ALL);
    }

    public static String toSnakeCase(String string, CapitalizeType type) {
        var words = new ArrayList<String>();
        var matcher = CAMEL_CASE_PATTERN.matcher(string);
        while (matcher.find()) {
            var word = matcher.group(1);
            words.add(word);
        }

        return switch (type) {
            case UPPER_ALL -> String.join("_", words.stream().map(w -> w.toUpperCase()).toList());
            case LOWER_ALL -> String.join("_", words.stream().map(w -> w.toLowerCase()).toList());
            case UPPER_FIRST -> String.join("_", words.stream().map(w -> capitalize(w)).toList());
        };
    }

}
