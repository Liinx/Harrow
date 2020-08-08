package me.lynx.libs.command;

public interface SubCommand extends Command {

    Command getParentCommand();



}
