package me.lynx.harrow.command;

import me.lynx.harrow.command.template.IChildCommand;
import me.lynx.harrow.command.template.IParentCommand;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class ParentCommand extends Command implements IParentCommand {

    private final Set<IChildCommand> childCommands;

    /**
     * Creates a new instance of a planet command.
     * @param name name of the command
     */
    public ParentCommand(String name) {
        super(name);
        childCommands = new HashSet<>();
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

    protected void addChildCommand(IChildCommand childCommand) {
        childCommands.add(childCommand);
    }

    protected void prepareExecutor() {
        getPlugin().getCommand(getName()).setExecutor((sender, command, label, args) -> {
            if (isRegistered()) {
                run(sender, args);

                if (args.length < 1) return true;
                String subCommand = args[0];

                Supplier<Stream<IChildCommand>> supplier = () -> getChildCommands().stream();
                if (supplier.get().noneMatch(cmd -> cmd.getName().equalsIgnoreCase(subCommand))) return true;

                IChildCommand childCommand = supplier.get()
                    .filter(cmd -> cmd.getName().equalsIgnoreCase(subCommand))
                    .findFirst().orElseGet(null);

                String[] subArgs = new String[args.length - 1];

                for (int i = 1; i < args.length; i++) {
                    subArgs[i - 1] = args[i];
                }

                childCommand.run(sender, subArgs);



                return true;
            } else {
                sender.sendMessage("Unknown command. Type \"/help\" for help.");
                return false;
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentCommand)) return false;
        ParentCommand that = (ParentCommand) o;
        return this.getName().equalsIgnoreCase(that.getName());
    }

}