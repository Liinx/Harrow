package com.github.liinx.command;

import com.github.liinx.command.template.IBaseCommand;
import com.github.liinx.command.template.IParentCommand;

import java.util.ArrayList;
import java.util.List;

abstract class Command implements IBaseCommand {

    private String name;
    private List<String> aliases;

    private Command() { }

    protected Command(String name) {
        this.name = name;
        aliases = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isParentCommand() {
        return this instanceof IParentCommand;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

}