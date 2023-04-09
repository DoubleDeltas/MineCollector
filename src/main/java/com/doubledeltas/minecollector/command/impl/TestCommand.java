package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.util.MessageUtil;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
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

        MessageUtil.sendRaw(player, new TranslatableComponent(Material.POTATO.getItemTranslationKey()));

        return true;
    }
}
