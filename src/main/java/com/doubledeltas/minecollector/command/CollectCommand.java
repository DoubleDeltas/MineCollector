package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class CollectCommand extends MineCollectorCommand {
    private static final CollectCommand instance = new CollectCommand();
    private CollectCommand() {
        super();
    }
    public static CollectCommand getInstance() {
        return instance;
    }

    @Override
    public String getCommandName() { return "수집"; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
