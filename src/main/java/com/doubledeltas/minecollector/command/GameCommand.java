package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.impl.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public abstract class GameCommand {
    protected List<Subcommand> subcommands;

    public GameCommand() {
        this.subcommands = new ArrayList<>();
    }

    private static final List<RootCommand> ROOT_COMMANDS = Arrays.asList(
            new BookCommand(),
            new CheckCommand(),
            new CollectCommand(),
            new GuideCommand(),
            new RankingCommand(),
            new McolCommand(),
            new TestCommand()
    );

    protected boolean resolve(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0)
            for (Subcommand subcommand: subcommands) {
                if (subcommand.getAliases().stream().anyMatch(args[0]::equals))
                    return subcommand.resolve(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        return onRawCommand(sender, command, label, args);
    }

    /**
     * 게임 커맨드를 로딩합니다.
     */
    public static void loadCommands() {
        for (RootCommand command: ROOT_COMMANDS) {
            MineCollector.getPlugin().getCommand(command.getCommandName()).setExecutor(command);
        }
        MineCollector.getPlugin().getLogger().log(Level.INFO, ROOT_COMMANDS.size() + "개 기본 커맨드 로딩 완료!");
    }

    public abstract boolean onRawCommand(CommandSender sender, Command command, String label, String[] args);
}
