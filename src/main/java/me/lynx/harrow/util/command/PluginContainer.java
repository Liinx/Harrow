package me.lynx.harrow.util.command;

import java.util.ArrayList;
import java.util.List;

public class PluginContainer {

    private String pluginName;
    private List<CommandContainer> commandContainer;

    public PluginContainer(String pluginName) {
        this.pluginName = pluginName;
        commandContainer = new ArrayList<>();
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