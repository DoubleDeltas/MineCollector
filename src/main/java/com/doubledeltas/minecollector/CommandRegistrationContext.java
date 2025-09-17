package com.doubledeltas.minecollector;

import org.bukkit.command.CommandMap;

public record CommandRegistrationContext(
        MineCollector plugin,
        CommandMap commandMap,
        String prefix
) {}
