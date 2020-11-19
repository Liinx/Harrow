package me.lynx.harrow.command.listener;

import me.lynx.harrow.command.CommandService;

public final class CommandListener {

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

}
