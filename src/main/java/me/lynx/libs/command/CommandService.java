package me.lynx.libs.command;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class CommandService {

    private JavaPlugin plugin;
    private Set<Command> registeredCommands;

    public CommandService(JavaPlugin plugin) {
        this.plugin = plugin;
        registeredCommands = new HashSet<>();
    }

    public boolean registerCommand(Command command) {
        boolean doesAlreadyExists = registeredCommands.stream()
            .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(command.getName()));
        if (doesAlreadyExists) return false;

        plugin.getCommand(command.getName()).setExecutor(command);
        plugin.getCommand(command.getName()).setAliases(command.getAliases());
        registeredCommands.add(command);
        return true;
    }

    public boolean registerSubCommand(SubCommand subCommand, Command parentCommand) {
        boolean doesAlreadyExists = parentCommand.getAllSubCommands().stream()
                .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(subCommand.getName()));
        if (doesAlreadyExists) return false;

        parentCommand.addSubCommand(subCommand);
        return true;
    }

    public Set<Command> getAllRegisteredCommands() {
        return registeredCommands;
    }

    public Command getRegisteredCommand(String name) {
        return registeredCommands.stream()
            .filter(cmd -> cmd.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElseGet(null);
    }

    public void unregisterCommand() {

    }


}
