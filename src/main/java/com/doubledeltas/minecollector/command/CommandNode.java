package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.CommandRegistrationContext;
import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandNode {
    void registerThis(CommandRegistrationContext context);

    boolean resolveCommand(CommandSender sender, Command command, String label, String[] args);

    List<String> resolveTabCompletion(CommandSender sender, Command command, String label, String[] args);

    List<String> getAliases();

    boolean onRawCommand(CommandSender sender, Command command, String label, String[] args);

    List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args);

    String getRequiredPermissionKey();

    boolean isExecutableBy(CommandSender sender);
}
