package com.github.liinx.command;

import com.github.liinx.command.template.IBaseCommand;
import com.github.liinx.command.template.IChildCommand;
import com.github.liinx.command.template.IParentCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CommandService {

    private JavaPlugin plugin;
    private Set<IParentCommand> registeredCommands;

    /**
     * Creates command service and binds
     * it to the provided plugin.
     * @param plugin Plugin class
     */
    public CommandService(JavaPlugin plugin) {
        this.plugin = plugin;
        registeredCommands = new HashSet<>();
    }

    /**
     * Register command on the bukkit so it could be used
     * by console and players on the server. If the command is
     * already register it will return false;
     * @param command instance of command to register
     * @return true if registration was successful false otherwise
     */
    public boolean registerCommand(@NotNull IParentCommand command) {
        if (registeredCommands.size() > 0) {
            boolean doesAlreadyExists = registeredCommands.stream()
                    .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(command.getName()));
            if (doesAlreadyExists) return false;
        }

        plugin.getCommand(command.getName()).setExecutor(command);
        plugin.getCommand(command.getName()).setAliases(command.getAliases());
        registeredCommands.add(command);
        return true;
    }

    /**
     * Register a child command on the already existing(registered) command
     * so it can be used by console and players on the server. If the command is
     * already register to this parent command it will return false;
     * @param childCommand instance of child command to register
     * @param parentCommand instance of parent command it belongs to
     * @return true if registration was successful false otherwise
     */
    public boolean registerChildCommand(IChildCommand childCommand, ParentCommand parentCommand) {
        boolean doesAlreadyExists = parentCommand.getChildCommands().stream()
                .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(childCommand.getName()));
        if (doesAlreadyExists) return false;

        parentCommand.addChildCommand(childCommand);
        return true;
    }

    /**
     * Gets all registered parent commands.
     * @return all registered commands
     */
    public Set<IParentCommand> getAllRegisteredCommands() {
        return registeredCommands;
    }

    /**
     * Gets registered command with given name, if it does not
     * exists returns null.
     * @param name name of the registered command
     * @return registered command
     */
    public IParentCommand getRegisteredCommand(String name) {
        return registeredCommands.stream()
            .filter(cmd -> cmd.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElseGet(null);
    }

    /**
     * Attempts to unregistered the parent command and all of its child commands.
     * @param command command to unregister
     */
    public void unregisterCommand(Command command) {
        boolean doesAlreadyExists = registeredCommands.stream()
            .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(command.getName()));
        if (!doesAlreadyExists) return;

        unRegisterBukkitCommand(command);
        registeredCommands.remove(command);
    }

    /**
     * Gets the plugin its bound to.
     * @return the plugin
     */
    public JavaPlugin getPlugin() {
        return plugin;
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