package me.lynx.harrow.command.template;

import me.lynx.harrow.command.ParentCommand;

import java.util.Set;

public interface IChildCommand extends IBaseCommand {


    Set<ParentCommand> getParentCommands();


    ParentCommand getParentCommand(String name);


    void addParentCommand(String name);


    void addParentCommand(ParentCommand parentCommand);

}