package me.lynx.iktinos.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Set;

public interface Command extends CommandExecutor {

    void run(CommandSender sender, String[] args);

    void setUsageMessage();

    Set<SubCommand> getAllSubCommands();

    SubCommand getSubCommand();

    void addSubCommand(SubCommand subCommand);

    String getUsageMessage();

    String getName();

    List<String> getAliases();

}