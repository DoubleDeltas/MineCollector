package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.command.impl.mcol.ReloadCommand;
import com.doubledeltas.minecollector.command.impl.mcol.SaveCommand;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class McolCommand extends CommandRoot {

    public McolCommand() {
        this.subcommands = List.of(new ReloadCommand(), new SaveCommand());
    }

    @Override
    public String getName() {
        return "마인콜렉터";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageUtil.sendRaw(sender, "명령어가 잘못되었습니다!");
        MessageUtil.sendRaw(sender, "올바른 명령어: §e/mcol [reload|save] ...");
        return false;
    }
}
