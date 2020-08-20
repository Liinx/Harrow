package com.github.liinx.command;

import com.github.liinx.HarrowPlugin;
import com.github.liinx.command.template.IBaseCommand;
import com.github.liinx.command.template.IParentCommand;
import com.github.liinx.HarrowFactory;

import java.util.*;

public abstract class Command implements IBaseCommand {

    private String name;
    private Set<String> aliases;
    private AbstractCommandService commandService;

    protected Command() { }

    protected Command(String name) {
        this.name = name;
        aliases = new HashSet<>();

        commandService = HarrowFactory.getHarrowInstance(this).getCommandService();
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
    public Set<String> getAliases() {
        return aliases;
    }

    public void addAlias(String alias) {
        aliases.add(alias);
    }

    public void addAlias(Collection<String> aliases) {
        this.aliases.addAll(aliases);
    }

    @Override
    public AbstractCommandService getCommandService() {
        return commandService;
    }

    protected void setCommandService(AbstractCommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public HarrowPlugin getPlugin() {
        return commandService.getPlugin();
    }

}