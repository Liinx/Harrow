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
        PREFIX = "-%level%%op%" + ChatColor.DARK_GRAY + "]" + " ";
        SENDER = Bukkit.getConsoleSender();
    }

    private HarrowLogger() {}

    private static String replacePlaceholder(String replaceWith, String pluginName) {
        return PREFIX.replaceAll("%level%", replaceWith)
                .replaceAll("%op%", pluginName == null ? "" : " " + pluginName);
    }

    /**
     * Log message to console with info level.
     * @param message message to log
     */
    public static void info(String message) {
        SENDER.sendMessage(NAME + replacePlaceholder("INFO", null) + ChatColor.WHITE + message);
    }

    /**
     * Log message to console with warning level.
     * @param message message to log
     */
    public static void warn(String message) {
        SENDER.sendMessage(NAME + replacePlaceholder("WARNING", null) + ChatColor.RED + message);
    }

    /**
     * Log message to console with severe level.
     * @param message message to log
     */
    public static void severe(String message) {
        SENDER.sendMessage(NAME + replacePlaceholder("SEVERE", null) + ChatColor.DARK_RED + message);
    }

    /**
     * Log message to console with info level.
     * @param message message to log
     * @param pluginName name of the plugin
     */
    public static void info(String message, String pluginName) {
        SENDER.sendMessage(NAME + replacePlaceholder("INFO", pluginName) + ChatColor.WHITE + message);
    }

    /**
     * Log message to console with warning level.
     * @param message message to log
     * @param pluginName name of the plugin
     */
    public static void warn(String message, String pluginName) {
        SENDER.sendMessage(NAME + replacePlaceholder("WARNING", pluginName) + ChatColor.RED + message);
    }

    /**
     * Log message to console with severe level.
     * @param message message to log
     * @param pluginName name of the plugin
     */
    public static void severe(String message, String pluginName) {
        SENDER.sendMessage(NAME + replacePlaceholder("SEVERE", pluginName) + ChatColor.DARK_RED + message);
    }

}