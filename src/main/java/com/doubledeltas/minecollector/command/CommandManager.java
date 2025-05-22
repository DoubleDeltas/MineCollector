package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.impl.*;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.PluginCommand;

import java.util.List;

public class CommandManager implements McolInitializable {
    private MineCollector plugin;

    private final List<CommandRoot> CMD_ROOTS = List.of(
            new BookCommand(),
            new CheckCommand(),
            new CollectCommand(),
            new GuideCommand(),
            new McolCommand(),
            new RankingCommand()
    );

    @Override
    public void init(MineCollector plugin) {
        this.plugin = plugin;
    }

    public void loadCommands() {
        for (CommandRoot root: CMD_ROOTS) {
            PluginCommand pluginCommand = plugin.getCommand(root.getName());
            pluginCommand.setExecutor(root);
            pluginCommand.setTabCompleter(root);

            root.registerThis(plugin);
        }
        MessageUtil.log(CMD_ROOTS.size() + "개 커맨드 불러옴!");
    }
}
