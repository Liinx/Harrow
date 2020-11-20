package me.lynx.harrow.command.listener;

import me.lynx.harrow.command.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public final class CommandListener implements Listener {

    private final CommandService commandService;
    private final String PREFIX;

    public CommandListener(CommandService commandService) {
        this.commandService = commandService;
        PREFIX = commandService.getPlugin().getName().toLowerCase() + ":";
        commandService.getPlugin()
            .getServer()
            .getPluginManager()
            .registerEvents(this, commandService.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    private void aliasForCommandUsed(PlayerCommandPreprocessEvent e) {




    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    private void aliasForCommandUsed(ServerCommandEvent e) {




    }



}