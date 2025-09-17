package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.CommandRegistrationContext;
import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.impl.*;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.ReflectionUtil;
import lombok.SneakyThrows;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;

import java.util.Collection;
import java.util.List;

public class CommandManager implements McolInitializable {
    private MineCollector plugin;
    private CommandRegistrationContext regContext;

    /**
     * public command roots, opened to users.
     */
    private final Collection<CommandRoot> CMD_ROOTS = List.of(
            new BookCommand(),
            new CheckCommand(),
            new CollectCommand(),
            new GuideCommand(),
            new McolCommand(),
            new RankingCommand()
    );

    /**
     * hidden command roots, opened to users.
     */
    private final Collection<CommandRoot> HIDDEN_CMD_ROOTS = List.of(
            new InternalCommand()
    );

    @Override
    @SneakyThrows({IllegalStateException.class, IllegalAccessException.class})
    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.regContext = new CommandRegistrationContext(
                plugin,
                (CommandMap) ReflectionUtil.traverseGetters(plugin.getServer(), "commandMap"),
                plugin.getDescription().getPrefix()
        );
    }

    public void loadCommands() {
        loadPublicCommands(CMD_ROOTS);
        loadInternalCommands(HIDDEN_CMD_ROOTS);
    }

    private void loadPublicCommands(Collection<CommandRoot> roots) {
        for (CommandRoot root: roots) {
            root.registerThis(regContext);
        }
        MessageUtil.log("command.loaded", CMD_ROOTS.size());
    }

    private void loadInternalCommands(Collection<CommandRoot> roots) {
        String prefix = plugin.getDescription().getPrefix();
        for (CommandRoot root: roots) {
            root.registerThis(regContext);
        }
    }
}
