package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TestCommand extends CommandRoot {

    @Override
    public String getName() {
        return "테스트";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;

        for (Material material: Arrays.copyOfRange(Material.values(), 0, 10))
            MessageUtil.send(player, material.getItemTranslationKey());

        return true;
    }
}
