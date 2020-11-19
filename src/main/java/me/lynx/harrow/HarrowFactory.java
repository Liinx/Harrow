package me.lynx.harrow;

import me.lynx.harrow.exception.InvalidHarrowPluginException;
import me.lynx.harrow.util.HarrowLogger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a container holding all of the instances of
 * plugins that are using harrow lib on this server.
 */
public class HarrowFactory {

    private static final Map<String,HarrowPlugin> instances;
    private static int counter;

    static {
        instances = new HashMap<>();
        counter = 0;
    }

    /**
     * Attempts to add harrow instance to the factory, if the instance
     * is already added it will skip adding it again.
     * @param plugin plugin instance to add
     * @return true if successfully added false otherwise
     */
    protected static boolean addInstance(HarrowPlugin plugin) {
        String jarName = getJarName(plugin);
        if (instanceExists(jarName)) {
            HarrowLogger.warn(plugin.getName() + " has been already initialized, skipping it now!");
            return false;
        }

        plugin.setLoadOrder(counter);
        counter++;

        instances.put(jarName, plugin);
        return true;
    }

    public static int getAmountOfInstances() {
        return instances.size();
    }

    /**
     * Gets instance of the plugin this class belongs to. Warning if
     * multiple plugins use exact same class it will return instance of the plugin
     * who loads that class first.
     * @param cmdClass any command you created
     * @return instance of harrow plugin
     * @throws InvalidHarrowPluginException if Harrow is not properly implemented in the plugin.
     */
    public static HarrowPlugin getHarrowInstance(final Object cmdClass)
            throws InvalidHarrowPluginException {
        String jarName = getJarName(cmdClass);
        HarrowLogger.severe(jarName + " in get instance");

        InvalidHarrowPluginException throwable = new InvalidHarrowPluginException(jarName +
                " is not using Harrow as intended, recheck the guide before trying again.");

        if (instanceExists(jarName)) {
            if (instances.get(jarName) == null) throw throwable;
            return instances.get(jarName);
        }

        if (instances.get(jarName) == null) throw throwable;
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
        return new File(plugin.getClass()
            .getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .getFile())
            .getName();
    }

}