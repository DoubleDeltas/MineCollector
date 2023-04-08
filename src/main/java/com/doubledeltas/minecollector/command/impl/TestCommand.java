package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandRoot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends CommandRoot {

    @Override
    public String getName() {
        return "테스트";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;

//        GameData data = DataManager.getData(player);
//        MineCollector.send(player, data.toMap().toString());

        MineCollector.send(player, MineCollector.getPlugin().getConfig().getString("language"));

        return true;
    }
}
