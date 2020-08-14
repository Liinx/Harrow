package com.github.liinx.command.model;

import com.github.liinx.command.template.IChildCommand;

public abstract class ChildCommand extends Command implements IChildCommand {

    private ParentCommand parentCommand;

    /**
     * Creates a new instance of a child command.
     * @param name name of the command
     * @param parentCommand parent command
     */
    protected ChildCommand(String name, ParentCommand parentCommand) {
        super(name);
        this.parentCommand = parentCommand;
    }

    @Override
    public ParentCommand getParentCommand() {
        return parentCommand;
    }

}