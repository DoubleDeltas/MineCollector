package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class CheckCommand extends CommandRoot {

    @Override
    public String getName() { return "체크"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
