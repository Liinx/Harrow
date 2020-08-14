package com.github.liinx.command.template;

import com.github.liinx.plugin.HarrowPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface IParentCommand extends IBaseCommand {

    /**
     * Gets the plugin this command belongs to
     * @return owning plugin
     */
    HarrowPlugin getPlugin();

    /**
     * Gets all the child commands for this command.
     * @return all child commands
     */
    Set<IChildCommand> getChildCommands();

    /**
     * Gets child command with the given name for this
     * command, if it doesn't exists returns null.
     * @param name name of the child command
     * @return named child command
     */
    IChildCommand getChildCommand(@NotNull String name);

    /**
     * Checks if the command is registered.
     * @return true if it is false otherwise
     */
    boolean isRegistered();

}