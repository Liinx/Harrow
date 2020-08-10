package com.github.liinx.command;

public interface SubCommand extends Command {

    Command getParentCommand();

}
