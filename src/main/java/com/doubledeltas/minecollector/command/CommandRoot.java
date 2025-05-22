package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.impl.*;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import java.util.List;

public abstract class CommandRoot extends CommandNode implements TabExecutor {
    public abstract String getName();

    @Override
    public final List<String> getAliases() {
        return MineCollector.getInstance().getCommand(this.getName()).getAliases();
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return resolveCommand(sender, command, label, args);
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return resolveTabCompletion(sender, command, label, args);
    }
}
