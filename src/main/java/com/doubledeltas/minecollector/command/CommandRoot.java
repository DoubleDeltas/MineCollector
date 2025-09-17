package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;

public interface CommandRoot extends CommandNode {
    String getName();

    Command getCommand();
}
