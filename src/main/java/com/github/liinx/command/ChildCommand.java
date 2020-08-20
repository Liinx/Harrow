package com.github.liinx.command;

import com.github.liinx.command.template.IChildCommand;

import java.util.HashSet;
import java.util.Set;

public abstract class ChildCommand extends Command implements IChildCommand {

    //private Map<ParentCommand,Boolean> parentCommands;

    //private Set<String> tempQueueForParent;


    /**
     * Creates a new instance of a child command.
     * @param name name of the command
     */
    protected ChildCommand(String name) {
        super(name);
        //parentCommands = new HashMap<>();
        //tempQueueForParent = new HashSet<>();
    }

    @Override
    public Set<ParentCommand> getParentCommands() {
        return new HashSet<>(); //parentCommands.keySet();
    }

    @Override
    public ParentCommand getParentCommand(String name) {
        /*Supplier<Stream<ParentCommand>> supplier = () -> parentCommands.keySet().stream()
                .filter(cmd -> cmd.getName().equalsIgnoreCase(name));
        if (supplier.get().count() > 0) return supplier.get().findFirst().orElseGet(null);
        else*/ return null;
    }

    protected void prepare() {
        /*tempQueueForParent.forEach(tmp -> {
            ParentCommand parentCommand = commandService.getCommand(tmp, false);
            if (parentCommand == null) return;

            addParentCommand(parentCommand);
        });*/
    }

   /* @Override
    public boolean addParentCommand(String name) {
        //tempQueueForParent.add(name);

        return true;
    }

    @Override
    public boolean addParentCommand(ParentCommand parentCommand) {
        if (parentCommand.getChildCommand(getName()) != null) return false;

        if (doesChildContainParent(parentCommand))
            //if (parentCommands.get(parentCommand)) return false;

        //parentCommands.put(parentCommand, true);
        return true;
    }*/

    private boolean doesChildContainParent(ParentCommand parentCommand) {
       /* Supplier<Stream<Map.Entry<ParentCommand,Boolean>>> supplier =
                () -> parentCommands.entrySet().stream()
            .filter(entry -> entry.getKey().getName().equalsIgnoreCase(parentCommand.getName()));
        if (supplier.get().count() < 1) return false;
        else*/ return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildCommand)) return false;
        ChildCommand that = (ChildCommand) o;
        return this.getName().equalsIgnoreCase(that.getName());
    }

}