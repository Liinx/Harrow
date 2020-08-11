package com.github.liinx.command.template;

import com.github.liinx.command.ParentCommand;
import org.bukkit.command.CommandSender;

public interface IChildCommand extends IBaseCommand {

    /**
     * Gets the parent command of this child command.
     * @return the parent command
     */
    ParentCommand getParentCommand();

    /**
     * Trigger the command.
     */
    void run(CommandSender sender, String[] args);

}