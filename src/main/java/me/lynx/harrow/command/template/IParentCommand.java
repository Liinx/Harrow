package me.lynx.harrow.command.template;

import me.lynx.harrow.command.ChildCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface IParentCommand extends IBaseCommand {

    /**
     * Gets all the child commands for this command.
     * @return all child commands
     */
    Set<ChildCommand> getChildCommands();

    /**
     * Gets child command with the given name for this
     * command, if it doesn't exists returns null.
     * @param name name of the child command
     * @return named child command
     */
    ChildCommand getChildCommand(@NotNull String name);

}