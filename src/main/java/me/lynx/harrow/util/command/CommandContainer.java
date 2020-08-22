package me.lynx.harrow.util.command;

import java.util.List;

public class CommandContainer {

    private String commandName;
    private List<String> aliases;

    public CommandContainer(String commandName, List<String> aliases) {
        this.commandName = commandName;
        this.aliases = aliases;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getAliases() {
        return aliases;
    }

}