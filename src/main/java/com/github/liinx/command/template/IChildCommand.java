package com.github.liinx.command.template;

import com.github.liinx.command.model.ParentCommand;

public interface IChildCommand extends IBaseCommand {

    /**
     * Gets the parent command of this child command.
     *
     * @return the parent command
     */
    ParentCommand getParentCommand();

}