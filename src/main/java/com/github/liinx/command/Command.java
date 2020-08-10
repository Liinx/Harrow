package com.github.liinx.command;

import com.github.liinx.command.template.IBaseCommand;
import com.github.liinx.command.template.IParentCommand;

abstract class Command implements IBaseCommand {

    private String name;

    private Command() { }

    protected Command(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isParentCommand() {
        return this instanceof IParentCommand;
    }

}