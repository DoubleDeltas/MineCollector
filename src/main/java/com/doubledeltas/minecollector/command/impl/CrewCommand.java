package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.command.impl.crew.*;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CrewCommand extends CommandRoot {

    public CrewCommand() {
        this.subcommands = List.of(
                new CrewListCommand(),
                new CrewCreateCommand(),
                new CrewRemoveCommand(),
                new CrewJoinCommand(),
                new CrewLeaveCommand(),
                new CrewInviteCommand(),
                new CrewPromoteCommand(),
                new CrewRevokeCommand(),
                new CrewRenameCommand()
        );
    }

    @Override
    public String getName() {
        return "크루";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageUtil.send(sender, "command.generic.invalid");
        MessageUtil.send(sender, "command.generic.correct_command", "/crew [] ...");
        return false;
    }
}
