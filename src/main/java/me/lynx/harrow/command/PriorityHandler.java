package me.lynx.harrow.command;

import me.lynx.harrow.HarrowPlugin;
import me.lynx.harrow.plugin.AbstractHarrowPlugin;
import me.lynx.harrow.plugin.CommandLoader;
import me.lynx.harrow.util.CallPriority;
import me.lynx.harrow.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriorityHandler {

    private static boolean notFirstCycle;
    private static String originalInput;
    private static int totalCycles, currentCycle;
    private static AbstractHarrowPlugin highestPriorityPlugin;

    static {
        notFirstCycle = false;
        originalInput = null;
        totalCycles = 0;
        currentCycle = 0;
        highestPriorityPlugin = null;
    }

    private PriorityHandler() {}

    public static Map<String,Boolean> getPriorityInstruction(String command, AbstractHarrowPlugin instance) {
        Plugin[] allPl = Bukkit.getPluginManager().getPlugins();
        List<String> justConflictNames = CommandLoader.getConflictingNames(command, instance);
        justConflictNames.add(instance.getName());

        boolean harrowPluginsOnly = true;
        boolean hasHarrowConflict = false;
        boolean harrowOverride = false;
        boolean bukkitOverride = false;

        for (Plugin plugin : allPl) {
            if (Utils.containsIgnoreCase(plugin.getName(), justConflictNames)) {
                if (!(plugin instanceof HarrowPlugin)) harrowPluginsOnly = false;

                if (plugin instanceof HarrowPlugin) {
                    hasHarrowConflict = true;
                    HarrowPlugin harrowPlugin = (HarrowPlugin) plugin;

                    AbstractHarrowPlugin.PriorityComparator comparator = new
                            AbstractHarrowPlugin.PriorityComparator();

                    AbstractHarrowPlugin highestPriority = PriorityHandler.getHighestPriorityPlugin();
                    if (highestPriority == null || comparator.compare(highestPriority, harrowPlugin) > 0) {
                        PriorityHandler.setHighestPriorityPlugin(harrowPlugin);
                    }

                    if (PriorityHandler.getHighestPriorityPlugin().getName().equalsIgnoreCase
                            (instance.getName())) harrowOverride = true;
                }

                bukkitOverride = instance.getPriority().getNumValue() >
                        CallPriority.NORMAL.getNumValue();
            }
        }

        Map<String,Boolean> instructions = new HashMap<>();
        instructions.put("bukkit-override", bukkitOverride);
        instructions.put("harrow-override", harrowOverride);
        instructions.put("has-harrow-conflicts", hasHarrowConflict);
        instructions.put("only-harrow-plugins", harrowPluginsOnly);
        return instructions;
    }

    public static void finish() {
        notFirstCycle = false;
        originalInput = null;
        currentCycle = 0;
        totalCycles = 0;
        highestPriorityPlugin = null;
    }

    public static void nextCycle(){
        currentCycle++;
    }

    public static void setNotFirstCycle(boolean notFirstCycle) {
        PriorityHandler.notFirstCycle = notFirstCycle;
    }

    public static void setOriginalInput(String originalInput) {
        PriorityHandler.originalInput = originalInput;
    }

    public static void setTotalCycles(int totalCycles) {
        PriorityHandler.totalCycles = totalCycles;
    }

    public static void setHighestPriorityPlugin(AbstractHarrowPlugin highestPriorityPlugin) {
        PriorityHandler.highestPriorityPlugin = highestPriorityPlugin;
    }

    public static boolean notFirstCycle() {
        return notFirstCycle;
    }

    public static String getOriginalInput() {
        return originalInput;
    }

    public static int getTotalCycles() {
        return totalCycles;
    }

    public static int getCurrentCycle() {
        return currentCycle;
    }

    public static AbstractHarrowPlugin getHighestPriorityPlugin() {
        return highestPriorityPlugin;
    }

}