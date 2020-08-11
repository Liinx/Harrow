package com.github.liinx.command;

import com.github.liinx.command.template.IChildCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
         org.bukkit.command.@NotNull Command command,
         @NotNull String label, @NotNull String[] args) {
        return false;
    }

}