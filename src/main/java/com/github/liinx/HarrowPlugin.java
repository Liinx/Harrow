package com.github.liinx;

import com.github.liinx.util.CallPriority;
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
    private HarrowPlugin instance;
    private CommandService commandService;

    /**
     * This method goes in your {@link #onEnable()} and should be
     * called before anything else.
     * @param plugin instance of your plugin class
     */
    public void enableHarrow(HarrowPlugin plugin) {
        this.instance = plugin;
        priority = CallPriority.NORMAL;
        commandService = new CommandService(instance);

        HarrowFactory.addInstance(instance);


        HarrowLogger.info("Enabling " + instance.getName() + " using Harrow with " + priority.getName()
            + " priority.");
    }

    @Override
    public void onDisable() {
        HarrowFactory.clear(instance);
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public void setPriority(CallPriority priority) {
        this.priority = priority;
    }

    public CallPriority getPriority() {
        return priority;
    }

    public int getLoadOrder() {
        return loadOrder;
    }

    protected void setLoadOrder(int loadOrder) {
        this.loadOrder = loadOrder;
    }

    public static class PriorityComparator implements Comparator<HarrowPlugin> {

        @Override
        public int compare(HarrowPlugin plugin1, HarrowPlugin plugin2) {

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