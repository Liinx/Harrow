package com.github.liinx.util;

import java.util.Collection;

public class Utils {

    public static boolean containsIgnoreCase(String word, Collection<String> collection) {
        return collection.stream().anyMatch(val -> val.equalsIgnoreCase(word));
    }

    public static String processCommand(String[] rawCommand) {
        StringBuilder sb = new StringBuilder();

        sb.append("/");
        for (int i = 0; i < rawCommand.length; i++) {
            sb.append(rawCommand[i]);
            if (i != (rawCommand.length - 1)) sb.append(" ");
        }
        return sb.toString();
    }

}