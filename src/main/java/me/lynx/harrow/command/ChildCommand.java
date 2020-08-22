package me.lynx.harrow.command;

import me.lynx.harrow.command.template.IChildCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represent a child command that can be used as a sub command to
 * registered main command that is linked with. Avoid getting parent from this
 * before registration as it will return null until its registered.
 */
public abstract class ChildCommand extends Command implements IChildCommand {

    private Map<String,Boolean> parentQueue;

    /**
     * Creates a new instance of a child command.
     * @param name name of the command
     */
    protected ChildCommand(String name) {
        super(name);
        parentQueue = new HashMap<>();
    }

    protected Map<String,Boolean> getParentQueue() {
        return parentQueue;
    }

    public boolean isRegistered(String parentName) {
        if (parentQueue.get(parentName.toLowerCase()) == null) return false;

        return parentQueue.get(parentName.toLowerCase());
    }

    @Override
    public boolean isRegistered() {
        return parentQueue.values().stream()
            .anyMatch(val -> val);
    }

    @Override
    public Set<ParentCommand> getParentCommands() {
        if (isRegistered()) {
            return getCommandService().getCommands().stream()
                .filter(parent -> parent.getChildCommand(getName()) != null)
                .collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public ParentCommand getParentCommand(String name) {
        if (isRegistered()) {
            ParentCommand parentCommand = getCommandService().getCommand(name, false);
            if (parentCommand == null) return null;
            if (parentCommand.getChildCommand(getName()) != null) return parentCommand;
            return null;
        }
        return null;
    }

    @Override
    public void addParentCommand(String name) {
        if (parentQueue.containsKey(name.toLowerCase())) return;

        parentQueue.put(name.toLowerCase(), false);
    }

    @Override
    public void addParentCommand(ParentCommand parentCommand) {
        if (parentQueue.containsKey(parentCommand.getName().toLowerCase())) return;

        parentQueue.put(parentCommand.getName().toLowerCase(), false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildCommand)) return false;
        ChildCommand that = (ChildCommand) o;
        return this.getName().equalsIgnoreCase(that.getName());
    }

}