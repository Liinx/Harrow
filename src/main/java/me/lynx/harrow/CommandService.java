package me.lynx.harrow;

import me.lynx.harrow.command.AbstractCommandService;

/**
 * Represents a command service for handling everything that has to do with
 * commands.
 */
public class CommandService extends AbstractCommandService {

    /**
     * Creates command service and binds
     * it to the provided plugin.
     *
     * @param plugin Plugin class
     */
    protected CommandService(HarrowPlugin plugin) {
        super(plugin);
    }

}
