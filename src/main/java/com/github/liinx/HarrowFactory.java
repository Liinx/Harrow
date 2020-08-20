package com.github.liinx;

import com.github.liinx.command.Command;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Represents a container holding all of the instances of
 * plugins that are using harrow lib on this server.
 */
public class HarrowFactory {

    private static final Map<String, HarrowPlugin> instances;
    private static int counter;

    static {
        instances = new WeakHashMap<>();
        counter = 0;
    }

    protected static void addInstance(HarrowPlugin plugin) {
        plugin.setLoadOrder(counter);
        counter++;

        String jarName = getJarName(plugin);
        instances.put(jarName, plugin);
    }

    /**
     * Gets instance of the plugin this class belongs to. Warning if
     * multiple plugins use exact same class it will return instance of the plugin
     * who loads that class first.
     * @param cmdClass any command you created
     * @return instnace of harrow plugin
     */
    public static HarrowPlugin getHarrowInstance(final Command cmdClass) {
        String jarName = getJarName(cmdClass);
        if (instanceExists(jarName)) {
            return instances.get(jarName);
        }

        return null;
    }

    protected static void clear(HarrowPlugin plugin) {
        String jarName = getJarName(plugin);

        if (!instanceExists(jarName)) return;
        instances.remove(jarName);
    }

    private static boolean instanceExists(String pluginName) {
        return instances.keySet().stream().anyMatch(name -> name.equalsIgnoreCase(pluginName));
    }

    private static String getJarName(Object plugin) {
        return new java.io.File(plugin.getClass()
            .getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .getFile())
            .getName();
    }

}