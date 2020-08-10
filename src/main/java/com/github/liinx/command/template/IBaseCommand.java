package com.github.liinx.command.template;

import org.bukkit.command.CommandExecutor;

import java.util.List;

public interface IBaseCommand extends CommandExecutor {

    /**
     * Gets the name of the command.
     * @return command name
     */
    String getName();

    /**
     * Gets all aliases for this command.
     * @return all aliases
     */
    List<String> getAliases();

    /**
     * Checks if this command is a parent command.
     * @return true if its parent command false otherwise.
     */
    boolean isParentCommand();

}