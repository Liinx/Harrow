package me.lynx.harrow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class HarrowLogger {

    private static final String NAME;
    private static final String PREFIX;
    private static final ConsoleCommandSender SENDER;

    static {
        NAME = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "HARROW";
        PREFIX = "-%ph%" + ChatColor.DARK_GRAY + "]" + " ";
        SENDER = Bukkit.getConsoleSender();
    }

    private HarrowLogger() {}

    private static String replacePlaceholder(String replaceWith) {
        return PREFIX.replaceAll("%ph%", replaceWith);
    }

    /**
     * Log message to console with info level.
     * @param message message to log
     */
    public static void info(String message) {
        SENDER.sendMessage(NAME + replacePlaceholder("INFO") + ChatColor.WHITE + message);
    }

    /**
     * Log message to console with warning level.
     * @param message message to log
     */
    public static void warn(String message) {
        SENDER.sendMessage(NAME + replacePlaceholder("WARNING") + ChatColor.RED + message);
    }

    /**
     * Log message to console with severe level.
     * @param message message to log
     */
    public static void severe(String message) {
        SENDER.sendMessage(NAME + replacePlaceholder("SEVERE") + ChatColor.DARK_RED + message);
    }

}