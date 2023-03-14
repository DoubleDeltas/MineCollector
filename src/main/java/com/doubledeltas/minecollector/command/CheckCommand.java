package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class CheckCommand extends MineCollectorCommand {
    static {
        MineCollectorCommand.addCommand(new CheckCommand());
    }

    @Override
    public String getCommandName() { return "체크"; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
