package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class RankingCommand extends CommandRoot {

    @Override
    public String getName() { return "랭킹"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
