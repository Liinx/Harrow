package com.github.liinx.command.model;

import com.github.liinx.command.exception.ChildCommandAlreadyRegisteredException;
import com.github.liinx.command.exception.CommandAlreadyRegisteredException;
import com.github.liinx.command.listener.CommandCaller;
import com.github.liinx.command.listener.CommandListener;
import com.github.liinx.command.template.IChildCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CommandService {

    private JavaPlugin plugin;
    private Set<ParentCommand> pluginCommands;

    private final String COMMAND_PREFIX;
    private static Map<String,ParentCommand> knownCommands;

    static {
        knownCommands = new HashMap<>();
    }

    /**
     * Creates command service and binds
     * it to the provided plugin.
     * @param plugin Plugin class
     */
    public CommandService(JavaPlugin plugin) {
        this.plugin = plugin;
        pluginCommands = new HashSet<>();
        COMMAND_PREFIX = plugin.getName().toLowerCase();

        new CommandListener(this);
        new CommandCaller(this);
    }

    /**
     * Register command on the bukkit so it could be used
     * by console and players on the server. If the command is
     * already registered it will return false;
     * @param command instance of command to register
     * @return true if registration was successful false otherwise
     */
    public boolean registerCommand(@NotNull ParentCommand command) {
        if (pluginCommands.size() > 0) {
            try {
                boolean doesAlreadyExists = pluginCommands.stream()
                    .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(command.getName()));
                if (doesAlreadyExists) {
                    throw new CommandAlreadyRegisteredException(command.getName() + " is already registered!");
                }
            } catch (CommandAlreadyRegisteredException e) {
                e.printStackTrace();
                return false;
            }
        }


        command.setRegistered(true);
        // plugin.getCommand(command.getName()).setExecutor(command);
        knownCommands.put(getPlugin().getName(), command);
        pluginCommands.add(command);
        return true;
    }

    /**
     * Register a child command on the already existing(registered) command
     * so it can be used by console and players on the server. If the command is
     * already registered to this parent command it will return false;
     * @param childCommand instance of child command to register
     * @return true if registration was successful false otherwise
     */
    public boolean registerChildCommand(IChildCommand childCommand) {
        try {
            boolean doesAlreadyExists = childCommand.getParentCommand().getChildCommands().stream()
                    .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(childCommand.getName()));
            if (doesAlreadyExists) throw new ChildCommandAlreadyRegisteredException(
                    childCommand.getName() + "is already registered to " +
                    childCommand.getParentCommand().getName() + " parent command");
        } catch (ChildCommandAlreadyRegisteredException e) {
            e.printStackTrace();
            return false;
        }

        childCommand.getParentCommand().addChildCommand(childCommand);
        return true;
    }

    /**
     * Gets all parent commands.
     * @return all commands
     */
    public Set<ParentCommand> getCommands() {
        return pluginCommands;
    }

    /**
     * Gets registered command with given name, if it does not
     * exists returns null.
     * @param name name of the registered command
     * @return registered command
     */
    public ParentCommand getRegisteredCommand(String name) {
         Supplier<Stream<ParentCommand>> supplier = () -> pluginCommands.stream()
            .filter(ParentCommand::isRegistered)
            .filter(cmd -> cmd.getName().equalsIgnoreCase(name));
         if (supplier.get().count() > 0) return supplier.get().findFirst().orElseGet(null);
         else return null;
    }

    /**
     * Attempts to unregistered the parent command and all of its child commands.
     * @param command command to unregister
     */
    public void unregisterCommand(ParentCommand command) {
        boolean doesAlreadyExists = pluginCommands.stream()
            .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(command.getName()));
        if (!doesAlreadyExists) return;

        command.setRegistered(false);
    }

    /**
     * Gets the plugin its bound to.
     * @return the plugin
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Gets map of all known commands that wore registered
     * with Harrow.
     * @return map key: owningPlugin, value: command
     */
    public static Map<String,ParentCommand> getKnownCommands() {
        return knownCommands;
    }

    public String getPrefix() {
        return COMMAND_PREFIX;
    }


}