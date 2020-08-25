package me.lynx.harrow.util.command;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PluginContainer {

    private String pluginName;
    private List<CommandContainer> commandContainer;
    private boolean usesHarrow;
    private JavaPlugin instance;

    public PluginContainer(String pluginName, JavaPlugin instance) {
        this.pluginName = pluginName;
        commandContainer = new ArrayList<>();
        usesHarrow = false;
        this.instance = instance;
    }

    public JavaPlugin getInstance() {
        return instance;
    }

    public boolean doesUseHarrow() {
        return usesHarrow;
    }

    public void setUsesHarrow(boolean usesHarrow) {
        this.usesHarrow = usesHarrow;
    }

    public String getPluginName() {
        return pluginName;
    }

    public List<CommandContainer> getCommandContainer() {
        return commandContainer;
    }

    public void addCommandContainer(CommandContainer commandContainer) {
        this.commandContainer.add(commandContainer);
    }

}