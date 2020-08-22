package me.lynx.harrow.plugin;

import me.lynx.harrow.HarrowLogger;
import me.lynx.harrow.HarrowPlugin;
import me.lynx.harrow.util.command.CommandContainer;
import me.lynx.harrow.util.command.PluginContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CommandLoader {

    private static boolean collected;
    private static List<PluginContainer> containers;

    static {
        collected = false;
        containers = new ArrayList<>();
    }

    public static List<PluginContainer> getContainers() {
        return containers;
    }

    public static boolean containsCommand(String command, AbstractHarrowPlugin instance) {
        HarrowLogger.severe("Starting...", instance.getName());

        for (PluginContainer container : containers) {
            String pluginName = container.getPluginName();
            List<CommandContainer> commandContainers = container.getCommandContainer();

            HarrowLogger.severe("Checking for " + pluginName + " plugin.", instance.getName());

            for (CommandContainer commandContainer : commandContainers) {
                String commandName = commandContainer.getCommandName();
                List<String> aliases = commandContainer.getAliases();

                boolean nameMatches = false;
                boolean aliasMatches;

                if (command.equalsIgnoreCase(commandName)) nameMatches = true;
                aliasMatches = aliases.stream().anyMatch(alias -> alias.equalsIgnoreCase(command));


                if (nameMatches) {
                    HarrowLogger.warn("Found name match:" + commandName + " with " + command + " command"
                    + " belongs to " + pluginName, instance.getName());
                }

                if (aliasMatches) {
                    HarrowLogger.warn("Found alias match:" + aliases.toString() + " with " + command + " command"
                            + " belongs to " + pluginName, instance.getName());
                }
            }
        }

        return false;
    }

    protected static void collect(JavaPlugin instance) {
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (!collected) {
                HarrowLogger.info("Gathering the commands from other plugins...", instance.getName());
                Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();

                for (Plugin plugin : plugins) {
                    PluginContainer pluginContainer = new PluginContainer(plugin.getName());

                    PluginCommandYamlParser.parse(plugin).forEach(cmd -> {
                        pluginContainer.addCommandContainer(new CommandContainer(cmd.getName(), cmd.getAliases()));

                        //HarrowLogger.warn(cmd.getName() + ": " + cmd.getAliases().toString());
                    });
                    containers.add(pluginContainer);
                }
                collected = true;
            }
        }, 1);
    }

}