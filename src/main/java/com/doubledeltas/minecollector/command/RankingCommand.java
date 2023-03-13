package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class RankingCommand extends MineCollectorCommand {
    private static final RankingCommand instance = new RankingCommand();
    private RankingCommand() {
        super();
    }
    public static RankingCommand getInstance() {
        return instance;
    }

    @Override
    public String getCommandName() { return "랭킹"; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
