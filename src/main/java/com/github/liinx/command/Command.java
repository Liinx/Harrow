package com.github.liinx.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public interface Command extends CommandExecutor {



    void setUsageMessage();

    Set<SubCommand> getAllSubCommands();

    SubCommand getSubCommand(String name);

    void addSubCommand(SubCommand subCommand);

    String getUsageMessage();

    String getName();

    List<String> getAliases();

    JavaPlugin getPlugin();

}