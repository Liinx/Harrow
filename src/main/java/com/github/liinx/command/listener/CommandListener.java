package com.github.liinx.command.listener;

import com.github.liinx.command.model.CommandService;
import com.github.liinx.command.model.ParentCommand;
import com.github.liinx.command.template.IChildCommand;
import com.github.liinx.util.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class CommandListener implements Listener {

    private CommandService commandService;

    public CommandListener(CommandService commandService) {
        this.commandService = commandService;
        commandService.getPlugin()
            .getServer()
            .getPluginManager()
            .registerEvents(this, commandService.getPlugin());
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    private void preProcessCommand(PlayerCommandPreprocessEvent e) {
        String noSlash = e.getMessage().substring(1);
        String[] split = noSlash.split(" ");
        String command = split[0];

        Supplier<Stream<ParentCommand>> stream = () -> commandService.getCommands().stream()
            .filter(ParentCommand::isRegistered)
            .filter(cmd -> Utils.containsIgnoreCase(command, cmd.getAliases()));
        if (stream.get().count() < 1) return;

        ParentCommand pcm = stream.get().findFirst().orElseGet(null);
        if (pcm == null) return;

        split[0] = pcm.getName();

        if (split.length > 1 && !split[1].equalsIgnoreCase("")) {
            String child = noSlash.split(" ")[1];

            IChildCommand ccm = pcm.getChildCommands().stream()
                    .filter(cmd -> Utils.containsIgnoreCase(child, cmd.getAliases()))
                    .findFirst().orElseGet(null);
            if (ccm == null) return;

            split[1] = ccm.getName();
        }

        e.setMessage(Utils.processCommand(split));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    private void unregisteredCommand(PlayerCommandPreprocessEvent e) {
        String noSlash = e.getMessage().substring(1);
        String[] split = noSlash.split(" ");
        String command = split[0];

        boolean foundCommand = commandService.getCommands().stream()
                .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(command));
        if (!foundCommand) return;


        ParentCommand pcm = commandService.getCommands().stream()
                .filter(cmd -> cmd.getName().equalsIgnoreCase(command))
                .findFirst().orElseGet(null);
        if (pcm == null) return;



        if (!pcm.isRegistered()) e.setCancelled(true);

       /* split[0] = pcm.getName();

        if (split[1] != null && !split[1].equalsIgnoreCase("")) {
            String child = noSlash.split(" ")[1];
            Set<IChildCommand> childCommands = pcm.getChildCommands();

            IChildCommand ccm = childCommands.stream()
                    .filter(cmd -> containsIgnoreCase(child, cmd.getAliases()))
                    .findFirst().orElseGet(null);
            if (ccm == null) return;

            split[1] = ccm.getName();
        }

        e.setMessage(processCommand(split));*/
    }






    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    private void listComman(PlayerCommandSendEvent e) {
        //list of commands get


    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    private void onConsole(ServerCommandEvent e) {



    }







}