package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class MineCollectorCommand implements CommandExecutor {
    private static List<MineCollectorCommand> commands = new ArrayList<>();

    protected MineCollectorCommand() {
        commands.add(this);
    }

    public abstract String getCommandName();

    public static void loadCommands() {
        for (MineCollectorCommand command: commands) {
            MineCollector.getPlugin().getCommand(command.getCommandName()).setExecutor(command);
        }
        MineCollector.getPlugin().getLogger().log(Level.INFO, commands.size() + "개 커맨드 로딩 완료!");
    }
}
