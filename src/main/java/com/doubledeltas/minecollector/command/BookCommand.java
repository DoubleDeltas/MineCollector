package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.SoundUtil;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class BookCommand extends MineCollectorCommand {

    @Override
    public String getCommandName() { return "도감"; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        ItemStack collectionBook = MineCollector.getPlugin().getItemManager().getItem(StaticItem.COLLECTION_BOOK);

        if (player.getInventory().contains(collectionBook))


        player.getInventory().addItem(collectionBook);
        SoundUtil.playHighRing(player);

        return false;
    }
}
