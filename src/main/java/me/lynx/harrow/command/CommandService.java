package me.lynx.harrow.command;

import me.lynx.harrow.HarrowPlugin;
import me.lynx.harrow.command.listener.BaseParentCommand;
import me.lynx.harrow.command.listener.CommandListener;
import me.lynx.harrow.util.HarrowLogger;

import java.util.HashSet;
import java.util.Set;

public final class CommandService {

    private final HarrowPlugin plugin;
    private final Set<BaseParentCommand> pluginCommands;

    /**
     * Creates command service and binds
     * it to the provided plugin.
     * @param plugin Plugin class
     */
    public CommandService(HarrowPlugin plugin) {
        this.plugin = plugin;
        pluginCommands = new HashSet<>();

        new CommandListener(this);
        HarrowLogger.info("Command service has been initialized.", plugin.getName());
    }






    /**
     * Gets the plugin its bound to.
     * @return the plugin
     */
    public HarrowPlugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandService)) return false;
        CommandService that = (CommandService) o;
        return getPlugin().getName().equalsIgnoreCase(that.getPlugin().getName());
    }

}