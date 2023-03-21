package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.mcol.McolCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public abstract class GameCommand implements CommandExecutor {
    protected List<GameCommand> subcommands;

    public GameCommand() {
        this.subcommands = new ArrayList<>();
    }

    private static List<GameCommand> commands = Arrays.asList(
            new BookCommand(),
            new CheckCommand(),
            new CollectCommand(),
            new GuideCommand(),
            new RankingCommand(),
            new McolCommand(),
            new TestCommand()
    );

    public abstract String getCommandName();

    public static void loadCommands() {
        for (GameCommand command: commands) {
            MineCollector.getPlugin().getCommand(command.getCommandName()).setExecutor(command);
        }
        MineCollector.getPlugin().getLogger().log(Level.INFO, commands.size() + "개 기본 커맨드 로딩 완료!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0)
            for (GameCommand subcommand: subcommands) {
                if (args[0].equals(subcommand.getCommandName()))
                    return subcommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        return onRawCommand(sender, command, label, args);
    }

    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    };
}
