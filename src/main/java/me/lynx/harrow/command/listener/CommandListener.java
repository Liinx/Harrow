package me.lynx.harrow.command.listener;

import me.lynx.harrow.HarrowLogger;
import me.lynx.harrow.command.AbstractCommandService;
import me.lynx.harrow.command.ChildCommand;
import me.lynx.harrow.command.ParentCommand;
import me.lynx.harrow.command.PriorityHandler;
import me.lynx.harrow.command.template.IChildCommand;
import me.lynx.harrow.plugin.HarrowFactory;
import me.lynx.harrow.util.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CommandListener implements Listener {

    private final AbstractCommandService commandService;
    private final String PREFIX;

    public CommandListener(AbstractCommandService commandService) {
        this.commandService = commandService;
        PREFIX = commandService.getPlugin().getName().toLowerCase() + ":";
        commandService.getPlugin()
            .getServer()
            .getPluginManager()
            .registerEvents(this, commandService.getPlugin());
    }

    private String handle(String commandLine, boolean handleSlash) {
        String toReturn = null;

        PriorityHandler.setTotalCycles(HarrowFactory.getAmountOfInstances());
        PriorityHandler.nextCycle();

        String[] split = commandLine.split(" ");
        if (handleSlash) split[0] = split[0].substring(1);

        if (PriorityHandler.notFirstCycle()) split[0] = PriorityHandler.getOriginalInput();
        else {
            PriorityHandler.setOriginalInput(split[0]);
            PriorityHandler.setNotFirstCycle(true);
        }

        String command = split[0];
        boolean hasMatchingPrefix = command.startsWith(PREFIX);
        ParentCommand parentCommand = getByAlias(command, hasMatchingPrefix);
        if (parentCommand == null) parentCommand = getByName(command, hasMatchingPrefix);

        if (parentCommand != null) {
            split[0] = hasMatchingPrefix ? PREFIX + parentCommand.getName() : parentCommand.getName();

            if (split.length > 1 && !split[1].equalsIgnoreCase("")) {
                IChildCommand childCommand = getChildByAlias(parentCommand, split[1]);
                if (childCommand != null) split[1] = childCommand.getName();
            }

            Map<String,Boolean> instructions = PriorityHandler.getPriorityInstruction
                    (command, commandService.getPlugin());
            if (hasMatchingPrefix) instructions.put("bukkit-override", true);

            if (instructions.get("bukkit-override")) {
                if (instructions.get("has-harrow-conflicts")) {
                    if (instructions.get("harrow-override")) {
                        toReturn = Utils.processCommand(split, handleSlash, PREFIX);
                        HarrowLogger.severe("Command harrow overridden to: " + Utils.processCommand(split, handleSlash, PREFIX));
                    }
                } else {
                    toReturn = Utils.processCommand(split, handleSlash, null);
                    HarrowLogger.severe("Command bukkit overridden to: " + Utils.processCommand(split, handleSlash, null));
                }
            } else {
                if (instructions.get("only-harrow-plugins") && instructions.get("has-harrow-conflicts")
                        && instructions.get("harrow-override")) {

                    toReturn = Utils.processCommand(split, handleSlash, PREFIX);
                    HarrowLogger.severe("Command harrow overridden to: " + Utils.processCommand(split, handleSlash, PREFIX));
                }
            }
        }

        if (PriorityHandler.getCurrentCycle() > (PriorityHandler.getTotalCycles() - 1)) PriorityHandler.finish();
        return toReturn;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    private void aliasForCommandUsed(PlayerCommandPreprocessEvent e) {
        String formatted = handle(e.getMessage(), true);
        if (formatted != null) e.setMessage(formatted);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    private void aliasForCommandUsed(ServerCommandEvent e) {
        String formatted = handle(e.getCommand(), false);
        if (formatted != null) e.setCommand(formatted);
    }

    private ParentCommand getByAlias(String command, boolean hasMatchingPrefix) {
        Supplier<Stream<ParentCommand>> supplier = () -> commandService.getCommands().stream()
                .filter(ParentCommand::isRegistered)
                .filter(cmd -> Utils.containsIgnoreCase(hasMatchingPrefix ?
                        command.substring(PREFIX.length()) : command, cmd.getAliases()));

        if (supplier.get().count() < 1) return null;
        return supplier.get().findFirst().orElse(null);
    }

    private ParentCommand getByName(String command, boolean hasMatchingPrefix) {
        return commandService.getCommand(hasMatchingPrefix ?
                command.substring(PREFIX.length()) : command, true);
    }

    private ChildCommand getChildByAlias(ParentCommand parentCommand, String command) {
        Supplier<Stream<ChildCommand>> supplier = () -> parentCommand.getChildCommands().stream()
                .filter(cmd -> Utils.containsIgnoreCase(command, cmd.getAliases()));

        if (supplier.get().count() < 1) return null;
        return supplier.get().findFirst().orElseGet(null);
    }

}