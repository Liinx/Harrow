package com.github.liinx.command.template;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface IBaseCommand {

    /**
     * Trigger the command.
     * @param sender command sender
     * @param args command arguments
     */
    void run(CommandSender sender, String[] args);

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