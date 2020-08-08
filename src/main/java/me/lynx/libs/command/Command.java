package me.lynx.libs.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Set;

public interface Command extends CommandExecutor {

    void run(CommandSender sender, String[] args);

    void setUsageMessage();

    Set<me.lynx.libs.command.SubCommand> getAllSubCommands();

    me.lynx.libs.command.SubCommand getSubCommand();

    void addSubCommand(me.lynx.libs.command.SubCommand subCommand);

    String getUsageMessage();

    String getName();

    List<String> getAliases();

}