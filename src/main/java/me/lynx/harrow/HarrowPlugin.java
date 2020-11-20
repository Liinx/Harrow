package me.lynx.harrow;

import me.lynx.harrow.command.CommandService;
import me.lynx.harrow.file.FileService;
import me.lynx.harrow.util.CallPriority;
import me.lynx.harrow.util.HarrowLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Comparator;

/**
 * Represents a instance of a bukkit plugin that is using a harrow
 * lib for setup. You should extend you main class with this instead
 * of {@link JavaPlugin}.
 */
public class HarrowPlugin extends JavaPlugin {



    private CallPriority priority;
    private int loadOrder;
    private HarrowPlugin plugin;
    private CommandService commandService;
    private FileService fileService;

    protected HarrowPlugin() {}

    /**
     * This method goes in your {@link #onEnable()} and should be
     * called before anything else.
     * @param plugin instance of your plugin class
     */
    public void enableHarrow(HarrowPlugin plugin) {
        this.plugin = plugin;
        priority = CallPriority.NORMAL;

        if (HarrowFactory.addInstance(plugin)) {
            HarrowLogger.info("Enabling " + plugin.getName() + " using Harrow with "
                + priority.getName() + " priority.");

            commandService = new CommandService(plugin);
            fileService = new FileService(plugin);
        }
    }

    /**
     * This method goes in your {@link #onEnable()} and should be
     * called before anything else. Load order decides which commands will be called
     * first if there is a conflict between plugins using harrow.
     * @param plugin instance of your plugin class
     * @param priority load priority of the plugin
     */
    public void enableHarrow(HarrowPlugin plugin, CallPriority priority) {
        this.plugin = plugin;
        this.priority = priority;

        if (HarrowFactory.addInstance(plugin)) {
            HarrowLogger.info("Enabling " + plugin.getName() + " using Harrow with "
                + getPriority().getName() + " priority.");

            commandService = new CommandService(plugin);
            fileService = new FileService(plugin);
        }
    }

    public void disableHarrow() {
        HarrowFactory.clear(plugin);
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

    final void setLoadOrder(int loadOrder) {
        this.loadOrder = loadOrder;
    }

    final public static class PriorityComparator implements Comparator<HarrowPlugin> {

        public PriorityComparator() {}

        @Override
        public int compare(HarrowPlugin plugin1, HarrowPlugin plugin2) {

            int priorityCompare = plugin1.getPriority().getID() - plugin2.getPriority().getID();
            int loadOrderCompare = plugin1.getLoadOrder() - plugin2.getLoadOrder();

            if (priorityCompare == 0) {
                return ((loadOrderCompare == 0) ? priorityCompare : loadOrderCompare);
            } else {
                return priorityCompare;
            }
        }
    }

}