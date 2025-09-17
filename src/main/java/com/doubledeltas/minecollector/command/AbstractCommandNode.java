package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.CommandRegistrationContext;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCommandNode implements CommandNode {
    protected MineCollector plugin;
    protected List<CommandNode> subcommands = List.of();

    @Override
    public void registerThis(CommandRegistrationContext context) {
        this.plugin = context.plugin();
        for (CommandNode subcommand : subcommands)
            subcommand.registerThis(context);
    }

    @Override
    public final boolean resolveCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (CommandNode subcommand : subcommands) {
                if (subcommand.getAliases().contains(args[0]))
                    return subcommand.resolveCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        if (!isExecutableBy(sender)) {
            MessageUtil.send(sender, "command.generic.no_permission");
            return false;
        }
        return onRawCommand(sender, command, label, args);
    }

    @Override
    public List<String> resolveTabCompletion(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (CommandNode subcommand : subcommands) {
                if (subcommand.getAliases().contains(args[0]) && args.length > 1)
                    return subcommand.resolveTabCompletion(sender, command, label,
                            Arrays.copyOfRange(args, 1, args.length));
            }
        }
        List<String> recommendations = new LinkedList<>(this.getTabRecommendation(sender, command, label, args));
        if (args.length == 1) {
            recommendations.addAll(
                    subcommands.stream()
                            .filter(subcommand -> subcommand.isExecutableBy(sender))
                            .map(CommandNode::getAliases)
                            .flatMap(List::stream)
                            .toList()
            );
        }
        return recommendations;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }

    @Override
    public boolean isExecutableBy(CommandSender sender) {
        String permissionKey = getRequiredPermissionKey();
        if (permissionKey == null)
            return true;
        return sender.hasPermission(permissionKey);
    }
}
