package me.lynx.iktinos.command;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CommandService {

    private JavaPlugin plugin;
    private Set<Command> registeredCommands;

    /**
     * Creates command service and binds
     * it to the provided plugin.
     * @param plugin Plugin class
     */
    public CommandService(JavaPlugin plugin) {
        this.plugin = plugin;
        registeredCommands = new HashSet<>();
    }

    public boolean registerCommand(@NotNull Command command) {
        if (registeredCommands.size() > 0) {
            boolean doesAlreadyExists = registeredCommands.stream()
                    .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(command.getName()));
            if (doesAlreadyExists) return false;
        }
        command.setUsageMessage();

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

    public void unregisterCommand(Command command) {
        boolean doesAlreadyExists = registeredCommands.stream()
                .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(command.getName()));
        if (!doesAlreadyExists) return;

        unRegisterBukkitCommand(command);
        registeredCommands.remove(command);
    }

    private Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    private void unRegisterBukkitCommand(Command cmd) {
        try {
            Object result = getPrivateField(plugin.getServer().getPluginManager(), "commandMap");
            SimpleCommandMap commandMap = (SimpleCommandMap) result;
            Object map = getPrivateField(commandMap, "knownCommands");
            @SuppressWarnings("unchecked")
            HashMap<String, org.bukkit.command.Command> knownCommands = (HashMap<String, org.bukkit.command.Command>) map;
            knownCommands.remove(cmd.getName());
            for (String alias : cmd.getAliases()){
                if(knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(plugin.getName())){
                    knownCommands.remove(alias);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
