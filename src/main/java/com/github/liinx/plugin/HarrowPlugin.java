package com.github.liinx.plugin;

import com.github.liinx.plugin.CallPriority;
import com.github.liinx.plugin.IHarrowPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class HarrowPlugin extends JavaPlugin implements IHarrowPlugin {

    private static int counter;
    private static Map<String,Integer> loadOrder;
    private CallPriority priority;
    private JavaPlugin instance;

    static {
        loadOrder = new HashMap<>();
        counter = 0;
    }

    @Override
    public void initialize(JavaPlugin plugin) {
        this.instance = plugin;

        loadOrder.put(instance.getName(), counter);
        counter++;
        priority = CallPriority.NORMAL;

        getLogger().info("STORED: " + instance.getName() + ": " + loadOrder.get(instance.getName()));
    }

    public void setPriority(CallPriority priority) {
        this.priority = priority;
    }

    public CallPriority getPriority() {
        return priority;
    }

    public void setLoadOrder(int position) {
        loadOrder.put(getName(), position);
    }

    public static Map<String,Integer> getLoadOrder() {
        return loadOrder;
    }

}