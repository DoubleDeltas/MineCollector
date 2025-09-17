package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.DynamicCommandRoot;
import com.doubledeltas.minecollector.command.StaticCommandRoot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class InternalCommand extends DynamicCommandRoot {

    @Override
    public String getName() {
        return "internal";
    }

    public InternalCommand() {
        super("internal");
        getCommand().setLabel(getName());
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(args);
        return false;
    }
}
