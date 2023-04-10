package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;

public class TestCommand extends CommandRoot {

    @Override
    public String getName() {
        return "테스트";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;

        ItemStack item = MineCollector.getPlugin().getItemManager().createItem(
                GuiItem.CORE,
                Map.of("score", new Random().nextDouble(100.0))
        );

        player.getInventory().addItem(item);
        SoundUtil.playHighRing(player);

        return true;
    }
}
