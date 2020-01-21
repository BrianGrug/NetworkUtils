package me.daddy.utils;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.stream.Collectors;

public class BukkitUtils {
    public static List<String> getCompletions(String[] arguments, List<String> input) {
        return getCompletions(arguments, input, 80);
    }

    public static List<String> getCompletions(String[] arguments, List<String> input, int limit) {
        Preconditions.checkNotNull(arguments);
        Preconditions.checkArgument(arguments.length != 0);
        String argument = arguments[arguments.length - 1];
        return input.stream().filter(string -> string.regionMatches(true, 0, argument, 0, argument.length())).limit(limit).collect(Collectors.toList());
    }

}
