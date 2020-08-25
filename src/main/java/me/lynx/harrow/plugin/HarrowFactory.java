package me.lynx.harrow.plugin;

import me.lynx.harrow.command.Command;
import me.lynx.harrow.util.exception.InvalidHarrowPluginException;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a container holding all of the instances of
 * plugins that are using harrow lib on this server.
 */
public class HarrowFactory {

    private static final Map<String, AbstractHarrowPlugin> instances;
    private static int counter;

    static {
        instances = new HashMap<>();
        counter = 0;
    }

    protected static void addInstance(AbstractHarrowPlugin plugin) {
        plugin.setLoadOrder(counter);
        counter++;

        String jarName = getJarName(plugin);
        instances.put(jarName, plugin);
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
    public static AbstractHarrowPlugin getHarrowInstance(final Command cmdClass)
            throws InvalidHarrowPluginException {
        String jarName = getJarName(cmdClass);
        InvalidHarrowPluginException throwable = new InvalidHarrowPluginException(jarName +
                " is not using Harrow as intended, recheck the guide before trying again.");

        if (instanceExists(jarName)) {
            if (instances.get(jarName) == null) throw throwable;
            return instances.get(jarName);
        }

        if (instances.get(jarName) == null) throw throwable;
        return null;
    }

    protected static void clear(AbstractHarrowPlugin plugin) {
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