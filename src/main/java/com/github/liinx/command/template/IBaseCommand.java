package com.github.liinx.command.template;

import com.github.liinx.CommandService;
import com.github.liinx.HarrowPlugin;
import com.github.liinx.command.AbstractCommandService;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Set;

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
    Set<String> getAliases();

    /**
     * Adds a alias to the command.
     * @param alias the alias to add
     */
    void addAlias(String alias);

    /**
     * Adds a colection of aliases to the command.
     * @param aliases collection containing the aliases
     */
    void addAlias(Collection<String> aliases);

    /**
     * Checks if this command is a parent command.
     * @return true if its parent command false otherwise.
     */
    boolean isParentCommand();

    /**
     * Checks if the command is registered.
     * @return true if it is false otherwise
     */
    boolean isRegistered();

    /** Gets the instance of a {@link CommandService}.
     * @return the command service instance
     */
    AbstractCommandService getCommandService();

    /**
     *  Gets the instance of a {@link JavaPlugin} class
     *  representing the current plugin if the command is registered.
     *  If its not registered Harrow will attempt to find the plugin this command
     *  belongs too in most cases it will succeed, but be warned if more plugins share
     *  exact same class it will get the first plugin that loads that class (Command).
     * @return plugin instance
     */
    HarrowPlugin getPlugin();

}