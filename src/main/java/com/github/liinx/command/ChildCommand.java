package com.github.liinx.command;

import com.github.liinx.command.template.IChildCommand;
import com.github.liinx.command.template.IParentCommand;

public abstract class ChildCommand extends Command implements IChildCommand {

    private IParentCommand parentCommand;

    protected ChildCommand(String name, IParentCommand parentCommand) {
        super(name);
        this.parentCommand = parentCommand;
    }

    @Override
    public IParentCommand getParentCommand() {
        return parentCommand;
    }

}