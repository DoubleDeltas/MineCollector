package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.StaticCommandRoot;
import com.doubledeltas.minecollector.command.impl.mcol.ReloadCommand;
import com.doubledeltas.minecollector.command.impl.mcol.SaveCommand;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class McolCommand extends StaticCommandRoot {

    public McolCommand() {
        this.subcommands = List.of(new ReloadCommand(), new SaveCommand());
    }

    @Override
    public String getName() {
        return "마인콜렉터";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageUtil.send(sender, "command.generic.invalid");
        MessageUtil.send(sender, "command.generic.correct_command", "/mcol [reload|save] ...");
        return false;
    }
}
