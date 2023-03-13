package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class CheckCommand extends MineCollectorCommand {
    private static final CheckCommand instance = new CheckCommand();
    private CheckCommand() {
        super();
    }
    public static CheckCommand getInstance() {
        return instance;
    }

    @Override
    public String getCommandName() { return "체크"; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
