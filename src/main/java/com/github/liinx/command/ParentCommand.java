package com.github.liinx.command;

import com.github.liinx.command.template.IChildCommand;
import com.github.liinx.command.template.IParentCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public abstract class ParentCommand extends Command implements IParentCommand {

    private Set<IChildCommand> childCommands;
    private JavaPlugin plugin;

    public ParentCommand(String name, JavaPlugin plugin) {
        super(name);
        this.plugin = plugin;
        childCommands = new HashSet<>();
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public Set<IChildCommand> getChildCommands() {
        return childCommands;
    }

    @Override
    public IChildCommand getChildCommand(@NotNull String name) {
        return childCommands.stream()
            .filter(cmd -> cmd.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElseGet(null);
    }

    public void addChildCommand(IChildCommand childCommand) {
        childCommands.add(childCommand);
    }

}