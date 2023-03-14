package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class RankingCommand extends MineCollectorCommand {
    static {
        MineCollectorCommand.addCommand(new RankingCommand());
    }
    @Override
    public String getCommandName() { return "랭킹"; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
