package com.github.liinx.command;

import com.github.liinx.command.template.IChildCommand;
import com.github.liinx.command.template.IParentCommand;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class ParentCommand extends Command implements IParentCommand {

    private Set<IChildCommand> childCommands;
    private boolean registered;

    /**
     * Creates a new instance of a planet command.
     * @param name name of the command
     */
    public ParentCommand(String name) {
        super(name);
        childCommands = new HashSet<>();
        registered = false;
    }

    @Override
    public Set<IChildCommand> getChildCommands() {
        return childCommands;
    }

    @Override
    public IChildCommand getChildCommand(@NotNull String name) {
        Supplier<Stream<IChildCommand>> supplier = () -> childCommands.stream()
            .filter(cmd -> cmd.getName().equalsIgnoreCase(name));
        if (supplier.get().count() > 0) return supplier.get().findFirst().orElseGet(null);
        else return null;
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

    protected void addChildCommand(ChildCommand childCommand) {
        childCommands.add(childCommand);
    }

    protected void prepareExecutor() {
        getPlugin().getCommand(getName()).setExecutor((sender, command, label, args) -> {
            if (registered) {
                run(sender, args);
                return true;
            } else {
                sender.sendMessage("Unknown command. Type \"/help\" for help.");
                return false;
            }
        });
    }

    protected void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentCommand)) return false;
        ParentCommand that = (ParentCommand) o;
        return this.getName().equalsIgnoreCase(that.getName());
    }

}