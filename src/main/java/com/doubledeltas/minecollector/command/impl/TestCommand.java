package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends CommandRoot {

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;

        player.sendMessage(player.getInventory().getItemInMainHand().getItemMeta().getAsString());
        return true;
    }

    @Override
    public String getName() {
        return "test";
    }
}
