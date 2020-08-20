package com.github.liinx.command;

import com.github.liinx.HarrowLogger;
import com.github.liinx.HarrowPlugin;
import com.github.liinx.util.exception.ChildCommandAlreadyRegisteredException;
import com.github.liinx.util.exception.CommandAlreadyRegisteredException;
import com.github.liinx.command.listener.CommandListener;
import com.github.liinx.command.template.IChildCommand;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractCommandService {

    private final HarrowPlugin plugin;
    private final Set<ParentCommand> pluginCommands;

    /**
     * Creates command service and binds
     * it to the provided plugin.
     * @param plugin Plugin class
     */
    protected AbstractCommandService(HarrowPlugin plugin) {
        this.plugin = plugin;
        pluginCommands = new HashSet<>();

        new CommandListener(this);
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

        HarrowLogger.info("Using " + getPlugin().getName() + " command service to register "
                + command.getName() + " command.");
        command.setCommandService(this);
        command.setRegistered(true);
        command.prepareExecutor();
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
    public boolean registerChildCommand(ChildCommand childCommand) {
        AtomicBoolean registeredToAll = new AtomicBoolean(true);
        childCommand.setCommandService(this);

        if (childCommand.getParentCommands().size() < 1) return false;
        childCommand.getParentCommands().forEach(parentCommand -> {
            try {
                boolean doesAlreadyExists = parentCommand.getChildCommands().stream()
                    .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(childCommand.getName()));
                if (doesAlreadyExists) throw new ChildCommandAlreadyRegisteredException(
                    childCommand.getName() + "is already registered to " +
                    parentCommand.getName() + " parent command!");
            } catch (ChildCommandAlreadyRegisteredException e) {
                e.printStackTrace();
                registeredToAll.set(false);
            }

            //childCommand.process();
            parentCommand.addChildCommand(childCommand);
        });

        return registeredToAll.get();
    }

    public boolean addChildCommand(ParentCommand parentCommand, ChildCommand childCommand) {
        IChildCommand child = parentCommand.getChildCommand(childCommand.getName());
        if (child != null) return false;

        parentCommand.addChildCommand(childCommand);
        return true;
    }

    public Set<ParentCommand> getParentCommandsOfChild(ChildCommand childCommand) {
        return getCommands().stream()
            .filter(parent -> parent.getChildCommand(childCommand.getName()) != null)
            .collect(Collectors.toSet());
    }

    public ParentCommand getParentCommandOfChild(String parentName, ChildCommand caller) {
        ParentCommand parentCommand = getCommand(parentName, false);
        if (parentCommand.getChildCommand(caller.getName()) != null) return parentCommand;
        return null;
    }

    /**
     * Gets all parent commands.
     * @return all commands
     */
    public Set<ParentCommand> getCommands() {
        return pluginCommands;
    }

    /**
     * Gets command with given name if it exists otherwise it will return
     * null. If isRegistered is set to true and the command is not registered
     * it will return null even if it exists.
     * @param name name of the registered command
     * @param isRegistered whether the command should be registered
     * @return the command
     */
    public ParentCommand getCommand(String name, boolean isRegistered) {
        Supplier<Stream<ParentCommand>> supplier = () -> pluginCommands.stream()
            .filter(cmd -> !isRegistered || cmd.isRegistered())
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
    public HarrowPlugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCommandService)) return false;
        AbstractCommandService that = (AbstractCommandService) o;
        return getPlugin().getName().equalsIgnoreCase(that.getPlugin().getName());
    }

}