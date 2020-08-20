package com.github.liinx.command.template;

import com.github.liinx.command.ParentCommand;

import java.util.Set;

public interface IChildCommand extends IBaseCommand {

    /**
     * Gets all parent commands of this child command.
     * @return the parent commands set
     */
    Set<ParentCommand> getParentCommands();

    /**
     * Gets the parent command with the provided name
     * from this child command.
     * @param name name of the parent command
     * @return the parent command
     */
    ParentCommand getParentCommand(String name);

    /*
     * Attempts to add a child command to the parent command with
     * the given name, if successful returns true. If child command is
     * already added to specified parent or parent command doesn't exist
     * it will return false.
     * @param name name of the parent command
     * @return true if successfully registered
     */
    //boolean addParentCommand(String name);

    /*
     * Attempts to add a child command to the parent command,
     * if successful returns true. If child command is
     * already added to specified parent it will return false.
     * @param parentCommand the parent command
     * @return true if successfully registered
     */
    //boolean addParentCommand(ParentCommand parentCommand);

}