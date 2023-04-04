package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class RootCommand extends GameCommand implements CommandExecutor {
    public abstract String getCommandName();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return resolve(sender, command, label, args);
    }
}
