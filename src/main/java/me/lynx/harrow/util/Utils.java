package me.lynx.harrow.util;

import java.util.Collection;

public class Utils {

    public static boolean containsIgnoreCase(String word, Collection<String> collection) {
        return collection.stream().anyMatch(val -> val.equalsIgnoreCase(word));
    }

    public static String processCommand(String[] rawCommand, boolean addSlash) {
        StringBuilder sb = new StringBuilder();

        if (addSlash) sb.append("/");
        for (int i = 0; i < rawCommand.length; i++) {
            sb.append(rawCommand[i]);
            if (i != (rawCommand.length - 1)) sb.append(" ");
        }
        return sb.toString();
    }

}