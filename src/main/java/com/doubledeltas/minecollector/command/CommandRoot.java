package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.impl.*;
import org.bukkit.command.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandRoot extends CommandNode implements TabExecutor {

    public static void loadCommands() {
        List<CommandRoot> roots = List.of(
                new BookCommand(),
                new CheckCommand(),
                new CollectCommand(),
                new GuideCommand(),
                new McolCommand(),
                new RankingCommand(),
                new TestCommand()
        );
        for (CommandRoot root: roots) {
            PluginCommand pluginCommand = MineCollector.getPlugin().getCommand(root.getName());
            pluginCommand.setExecutor(root);
            pluginCommand.setTabCompleter(root);
        }
        MineCollector.log(roots.size() + "개 커맨드 불러옴!");
    }

    public abstract String getName();

    @Override
    public final List<String> getAliases() {
        return MineCollector.getPlugin().getCommand(this.getName()).getAliases();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return resolveCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return resolveTabCompletion(sender, command, label, args);
    }
}
