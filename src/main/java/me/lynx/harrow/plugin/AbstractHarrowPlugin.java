package me.lynx.harrow.plugin;

import me.lynx.harrow.HarrowLogger;
import me.lynx.harrow.util.CallPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Comparator;

/**
 * Represents a instance of a bukkit plugin that is using a harrow
 * lib for setup. You should extend you main class with this instead
 * of {@link JavaPlugin}.
 */
public abstract class AbstractHarrowPlugin extends JavaPlugin {

    private CallPriority priority;
    private int loadOrder;
    private AbstractHarrowPlugin instance;
    private CommandService commandService;
    private FileService fileService;

    /**
     * This method goes in your {@link #onEnable()} and should be
     * called before anything else.
     * @param plugin instance of your plugin class
     */
    public void enableHarrow(AbstractHarrowPlugin plugin) {
        this.instance = plugin;

        HarrowLogger.info("Enabling " + instance.getName() + " using Harrow with "
                + getPriority().getName() + " priority.");

        commandService = new CommandService(instance);
        fileService = new FileService(instance);

        HarrowFactory.addInstance(instance);
    }

    /**
     * This method goes in your {@link #onEnable()} and should be
     * called before anything else. Load order decides which commands will be called
     * first if there is a conflict between plugins using harrow.
     * @param plugin instance of your plugin class
     * @param priority load priority of the plugin
     */
    public void enableHarrow(AbstractHarrowPlugin plugin, CallPriority priority) {
        this.instance = plugin;
        this.priority = priority;

        HarrowLogger.info("Enabling " + instance.getName() + " using Harrow with "
                + getPriority().getName() + " priority.");

        commandService = new CommandService(instance);
        fileService = new FileService(instance);

        HarrowFactory.addInstance(instance);
    }

    @Override
    public void onDisable() {
        HarrowFactory.clear(instance);
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setPriority(CallPriority priority) {
        this.priority = priority;
    }

    public CallPriority getPriority() {
        if (priority == null) priority = CallPriority.NORMAL;
        return priority;
    }

    public int getLoadOrder() {
        return loadOrder;
    }

    void setLoadOrder(int loadOrder) {
        this.loadOrder = loadOrder;
    }

    public static class PriorityComparator implements Comparator<AbstractHarrowPlugin> {
        @Override
        public int compare(AbstractHarrowPlugin plugin1, AbstractHarrowPlugin plugin2) {

            int priorityCompare = plugin1.getPriority().getNumValue()
                - plugin2.getPriority().getNumValue();
            int loadOrderCompare = plugin1.getLoadOrder() - plugin2.getLoadOrder();

            if (priorityCompare == 0) {
                return ((loadOrderCompare == 0) ? priorityCompare : loadOrderCompare);
            } else {
                return priorityCompare;
            }
        }
    }

}