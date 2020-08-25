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
import java.util.stream.Collectors;

public class CommandLoader {

    private static boolean collected;
    private static List<PluginContainer> containers;

    static {
        collected = false;
        containers = new ArrayList<>();
    }

    private CommandLoader() {}

    public static List<PluginContainer> getContainers() {
        return containers;
    }

    public static List<String> getConflictingNames(String command, AbstractHarrowPlugin instance) {
        return getConflictingContainers(command, instance).stream()
            .map(PluginContainer::getPluginName)
            .collect(Collectors.toList());
    }

    private static List<PluginContainer> getConflictingContainers(String command, AbstractHarrowPlugin instance) {
        HarrowLogger.info("Scanning for " + command + "...", instance.getName());

        List<PluginContainer> detected = new ArrayList<>();

        for (PluginContainer container : containers) {
            String pluginName = container.getPluginName();
            List<CommandContainer> commandContainers = container.getCommandContainer();

            if (pluginName.equalsIgnoreCase(instance.getName())) continue;

            //HarrowLogger.severe("Checking for " + pluginName + " plugin.", instance.getName());
            boolean foundHarrowAllias = false;
            boolean nameMatches = false;
            boolean aliasMatches = false;

            if (container.doesUseHarrow()) {
                HarrowPlugin harrowPlugin = (HarrowPlugin) container.getInstance();

                List<String> aliases = new ArrayList<>();
                harrowPlugin.getCommandService().getCommands()
                    .forEach(parentCommand -> aliases.addAll(parentCommand.getAliases()));

                for (String alias : aliases) {
                    if (alias.equalsIgnoreCase(command)) {
                        foundHarrowAllias = true;
                        break;
                    }
                }
            }

            for (CommandContainer commandContainer : commandContainers) {
                if (nameMatches || aliasMatches) break;

                String commandName = commandContainer.getCommandName();
                List<String> aliases = commandContainer.getAliases();

                if (command.equalsIgnoreCase(commandName)) nameMatches = true;
                aliasMatches = aliases.stream().anyMatch(alias -> alias.equalsIgnoreCase(command));
            }

            if (nameMatches || aliasMatches || foundHarrowAllias) detected.add(container);

        }

        return detected;
    }

    protected static void collect(JavaPlugin instance) {
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (!collected) {
                HarrowLogger.info("Gathering commands from other plugins...");
                Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();

                for (Plugin plugin : plugins) {
                    PluginContainer pluginContainer = new PluginContainer(plugin.getName(), instance);
                    pluginContainer.setUsesHarrow(plugin instanceof HarrowPlugin);

                    PluginCommandYamlParser.parse(plugin).forEach(cmd -> {
                        pluginContainer.addCommandContainer(new CommandContainer(cmd.getName(), cmd.getAliases()));
                    });
                    containers.add(pluginContainer);
                }
                collected = true;
            }
        }, 1);
    }

}