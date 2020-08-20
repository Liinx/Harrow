package com.github.liinx;

import com.github.liinx.command.AbstractCommandService;

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
