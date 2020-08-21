package me.lynx.harrow.command;

import me.lynx.harrow.plugin.AbstractHarrowPlugin;
import me.lynx.harrow.util.exception.CommandAlreadyRegisteredException;
import me.lynx.harrow.util.exception.ChildCommandAlreadyRegisteredException;
import me.lynx.harrow.command.listener.CommandListener;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class AbstractCommandService {

    private final AbstractHarrowPlugin plugin;
    private final Set<ParentCommand> pluginCommands;

    /**
     * Creates command service and binds
     * it to the provided plugin.
     * @param plugin Plugin class
     */
    protected AbstractCommandService(AbstractHarrowPlugin plugin) {
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
                    .anyMatch(cmd -> cmd.equals(command));
                if (doesAlreadyExists) {
                    throw new CommandAlreadyRegisteredException(command.getName() + " is already registered!");
                }
            } catch (CommandAlreadyRegisteredException e) {
                e.printStackTrace();
                return false;
            }
        }

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
     * @return true if registered to at least one parent command
     */
    public boolean registerChildCommand(ChildCommand childCommand) {
        childCommand.setCommandService(this);

        if (childCommand.getParentQueue().size() < 1) return false;
        childCommand.getParentQueue().keySet().forEach(parentName -> {
            boolean registered = processChildToSingleParent(parentName, childCommand);
            childCommand.getParentQueue().put(parentName, registered);
        });

        childCommand.setRegistered(childCommand.isRegistered());
        return childCommand.isRegistered();
    }

    private boolean processChildToSingleParent(String parentName, ChildCommand childCommand) {
        ParentCommand parentCommand = getCommand(parentName, false);
        if (parentCommand == null) return false;

        try {
            boolean alreadyExists = parentCommand.getChildCommand(childCommand.getName()) != null;
            if (alreadyExists) throw new ChildCommandAlreadyRegisteredException(
                childCommand.getName() + "is already registered to " +
                parentCommand.getName() + " parent command!");
        } catch (ChildCommandAlreadyRegisteredException e) {
            e.printStackTrace();
            return false;
        }

        parentCommand.addChildCommand(childCommand);
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
            .anyMatch(cmd -> cmd.equals(command));
        if (!doesAlreadyExists) return;

        command.setRegistered(false);
    }

    /**
     * Gets the plugin its bound to.
     * @return the plugin
     */
    public AbstractHarrowPlugin getPlugin() {
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