package com.doubledeltas.minecollector.command.mcol;

import com.doubledeltas.minecollector.command.GameCommand;
import com.doubledeltas.minecollector.constant.Titles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class McolCommand extends GameCommand {

    public McolCommand() {
        this.subcommands.add(new ReloadCommand());
    }

    @Override
    public String getCommandName() {
        return "마인콜렉터";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(Titles.MSG_PREFIX + "명령어가 잘못되었습니다!");
        sender.sendMessage(Titles.MSG_PREFIX + "올바른 명령어: §e/mcol [reload] ...");
        return false;
    }
}
