package me.lynx.iktinos.command;

public interface SubCommand extends Command {

    Command getParentCommand();



}
