package me.lynx.harrow.command;

import java.util.Set;

public interface BaseChildCommand extends BaseCommand {

    Set<BaseParentCommand> getParentCommands();

    BaseParentCommand getParentCommand(String name);

    void addParentCommand(String name);

    void addParentCommand(BaseParentCommand parentCommand);

}