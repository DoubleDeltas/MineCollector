package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.RootCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class GuideCommand extends RootCommand {

    @Override
    public String getCommandName() { return "가이드"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
