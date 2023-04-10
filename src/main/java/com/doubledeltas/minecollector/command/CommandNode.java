package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CommandNode {
    protected List<CommandNode> subcommands = List.of();

    protected boolean resolveCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0)
            for (CommandNode subcommand: subcommands) {
                if (subcommand.getAliases().stream().anyMatch(args[0]::equals))
                    return subcommand.resolveCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        return onRawCommand(sender, command, label, args);
    }

    protected List<String> resolveTabCompletion(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0)
            for (CommandNode subcommand: subcommands) {
                if (subcommand.getAliases().stream().anyMatch(args[0]::equals))
                    return subcommand.resolveTabCompletion(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        return Stream.concat(
                subcommands.stream().map(CommandNode::getAliases).flatMap(List::stream),
                this.getTabRecommendation().stream()
        ).collect(Collectors.toList());
    }

    public abstract List<String> getAliases();

    public abstract boolean onRawCommand(CommandSender sender, Command command, String label, String[] args);

    public List<String> getTabRecommendation() {
        return List.of();
    }
}
