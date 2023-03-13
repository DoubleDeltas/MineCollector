package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.command.CommandExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

public abstract class MineCollectorCommand implements CommandExecutor {
    public abstract String getCommandName();

    public static void loadCommands() {
        Class<?>[] commandClasses = MineCollectorCommand.class.getPermittedSubclasses();
        if (commandClasses == null) {
            MineCollector.getPlugin().getLogger().log(Level.SEVERE, "마인콜렉터 커맨드 로딩 실패!");
            return;
        }
        try {
            for (Class<?> clazz: commandClasses) {
                MineCollectorCommand command = (MineCollectorCommand) clazz.getConstructor().newInstance();
                MineCollector.getPlugin().getCommand(command.getCommandName()).setExecutor(command);
            }
        } catch ( NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException
                | NullPointerException e
        ) {
            e.printStackTrace();
        }
    }
}
