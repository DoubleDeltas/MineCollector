package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.RootCommand;
import com.doubledeltas.minecollector.command.impl.mcol.ReloadCommand;
import com.doubledeltas.minecollector.command.impl.mcol.SaveCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class McolCommand extends RootCommand {

    public McolCommand() {
        this.subcommands.add(new ReloadCommand());
        this.subcommands.add(new SaveCommand());
    }

    @Override
    public String getCommandName() {
        return "마인콜렉터";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MineCollector.send(sender, "명령어가 잘못되었습니다!");
        MineCollector.send(sender, "올바른 명령어: §e/mcol [reload|save] ...");
        return false;
    }
}