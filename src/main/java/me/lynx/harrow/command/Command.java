package me.lynx.harrow.command;

import me.lynx.harrow.plugin.AbstractHarrowPlugin;
import me.lynx.harrow.command.template.IBaseCommand;
import me.lynx.harrow.command.template.IParentCommand;
import me.lynx.harrow.plugin.HarrowFactory;
import me.lynx.harrow.util.exception.InvalidHarrowPluginException;

import java.util.*;

public abstract class Command implements IBaseCommand {

    private String name;
    private Set<String> aliases;
    private AbstractCommandService commandService;
    private boolean registered;

    protected Command() { }

    protected Command(String name) {
        this.name = name;
        aliases = new HashSet<>();
        registered = false;

        try {
            commandService = HarrowFactory.getHarrowInstance(this).getCommandService();
        } catch (InvalidHarrowPluginException e) {
            e.printStackTrace();
        }
    }

    protected void setCommandService(AbstractCommandService commandService) {
        this.commandService = commandService;
    }

    protected void setRegistered(boolean registered) {
        this.registered = registered;
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
    public boolean isRegistered() {
        return registered;
    }

    @Override
    public Set<String> getAliases() {
        return aliases;
    }

    @Override
    public void addAlias(String alias) {
        aliases.add(alias);
    }

    @Override
    public void addAlias(Collection<String> aliases) {
        this.aliases.addAll(aliases);
    }

    @Override
    public AbstractCommandService getCommandService() {
        return commandService;
    }

    @Override
    public AbstractHarrowPlugin getPlugin() {
        return commandService.getPlugin();
    }

}