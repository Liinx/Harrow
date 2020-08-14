package com.github.liinx.command.model;

import com.github.liinx.HarrowPlugin;
import com.github.liinx.command.template.IChildCommand;
import com.github.liinx.command.template.IParentCommand;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class ParentCommand extends Command implements IParentCommand {

    private Set<IChildCommand> childCommands;
    private HarrowPlugin plugin;
    private boolean registered;

    /**
     * Creates a new instance of a planet command.
     * @param name name of the command
     * @param plugin owning plugin
     */
    public ParentCommand(String name, HarrowPlugin plugin) {
        super(name);
        this.plugin = plugin;
        childCommands = new HashSet<>();
        registered = false;
    }

    @Override
    public HarrowPlugin getPlugin() {
        return plugin;
    }

    @Override
    public Set<IChildCommand> getChildCommands() {
        return childCommands;
    }

    @Override
    public IChildCommand getChildCommand(@NotNull String name) {
        Supplier<Stream<IChildCommand>> suplier = () -> childCommands.stream()
            .filter(cmd -> cmd.getName().equalsIgnoreCase(name));
        if (suplier.get().count() > 0) return suplier.get().findFirst().orElseGet(null);
        else return null;
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

    protected void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public void addChildCommand(IChildCommand childCommand) {
        childCommands.add(childCommand);
    }

}