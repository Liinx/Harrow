package com.github.liinx.command.listener;

import com.github.liinx.HarrowPlugin;
import com.github.liinx.command.model.CommandService;
import com.github.liinx.command.model.ParentCommand;
import com.github.liinx.util.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandCaller implements Listener {

    private CommandService commandService;

    public CommandCaller(CommandService commandService) {
        this.commandService = commandService;
        commandService.getPlugin()
            .getServer()
            .getPluginManager()
            .registerEvents(this, commandService.getPlugin());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    private void callCommand(PlayerCommandPreprocessEvent e) {
        String noSlash = e.getMessage().substring(1);
        String[] split = noSlash.split(" ");
        String command = split[0];

        int sameNameAmount = 0;
        Map<String,ParentCommand> isolated = new HashMap<>();
        for (String pluginName : CommandService.getKnownCommands().keySet()) {
            if (CommandService.getKnownCommands().get(pluginName).getName().equalsIgnoreCase(command)) {
                sameNameAmount++;
                isolated.put(pluginName, CommandService.getKnownCommands().get(pluginName));
            }
        }

        if (sameNameAmount > 1) {
            Map<String,Integer> isolatedPlugins = new HashMap<>();
            for (String pluginName : isolated.keySet()) {
                isolatedPlugins.put(pluginName,HarrowPlugin.getLoadOrder().get(pluginName));
            }

            isolatedPlugins = isolatedPlugins.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1,
                    LinkedHashMap::new));

            boolean isFromThisPlugin = isolated.values().stream()
                .findFirst().orElseGet(null)
                .getPlugin().getName().equalsIgnoreCase(commandService.getPlugin().getName());

            if (isFromThisPlugin) {
                isolated.get(isolatedPlugins.entrySet().iterator().next().getKey()).run(e.getPlayer(), split);
            }

        } else if (sameNameAmount == 1) {

            boolean isFromThisPlugin = CommandService.getKnownCommands().values().stream()
                    .anyMatch(cmd -> cmd.getPlugin().getName().equalsIgnoreCase(commandService.getPlugin().getName()));

            if (isFromThisPlugin) {
                commandService.getRegisteredCommand(command).run(e.getPlayer(), split);
            }
        }

    }

}
