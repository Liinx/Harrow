package com.github.liinx.command.template;

public interface IChildCommand extends IBaseCommand {

    /**
     * Gets the parent command of this child command.
     * @return the parent command
     */
    IParentCommand getParentCommand();

    /**
     * Trigger the command.
     */
    void run();

}