package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CommandNode {
    protected List<CommandNode> subcommands = List.of();

    protected final boolean resolveCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0)
            for (CommandNode subcommand: subcommands) {
                if (subcommand.getAliases().contains(args[0]))
                    return subcommand.resolveCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        return onRawCommand(sender, command, label, args);
    }

    protected final List<String> resolveTabCompletion(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0)
            for (CommandNode subcommand: subcommands) {
                if (subcommand.getAliases().contains(args[0]))
                    return subcommand.resolveTabCompletion(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        return Stream.concat(
                subcommands.stream().map(CommandNode::getAliases).flatMap(List::stream),
                this.getTabRecommendation(sender, command, label, args).stream()
        ).collect(Collectors.toList());
    }

    public abstract List<String> getAliases();

    public abstract boolean onRawCommand(CommandSender sender, Command command, String label, String[] args);

    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }
}
