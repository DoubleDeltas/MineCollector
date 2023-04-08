package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class GuideCommand extends CommandRoot {

    @Override
    public String getName() { return "가이드"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
