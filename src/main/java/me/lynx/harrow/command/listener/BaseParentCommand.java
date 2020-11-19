package me.lynx.harrow.command.listener;

import me.lynx.harrow.command.BaseChildCommand;
import me.lynx.harrow.command.BaseCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface BaseParentCommand extends BaseCommand {

    /**
     * Gets all the child commands for this command.
     * @return all child commands
     */
    Set<BaseChildCommand> getChildCommands();

    /**
     * Gets child command with the given name for this
     * command, if it doesn't exists returns null.
     * @param name name of the child command
     * @return named child command
     */
    BaseChildCommand getChildCommand(@NotNull String name);

}