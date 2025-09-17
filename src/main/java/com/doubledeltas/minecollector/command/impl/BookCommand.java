package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.StaticCommandRoot;
import com.doubledeltas.minecollector.command.impl.book.BookOpenCommand;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class BookCommand extends StaticCommandRoot {

    public BookCommand() {
        this.subcommands = List.of(new BookOpenCommand());
    }

    @Override
    public String getName() { return "도감"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "command.generic.player_only");
            return false;
        }

        ItemStack collectionBook = plugin.getItemManager().getItem(StaticItem.COLLECTION_BOOK);

        if (hasCollectionBook(player)) {
            MessageUtil.send(player, "command.book.already_have");
            SoundUtil.playFail(player);
        }
        else {
            player.getInventory().addItem(collectionBook);
            MessageUtil.send(player, "command.book.received");
            SoundUtil.playHighRing(player);
        }

        return false;
    }

    private boolean hasCollectionBook(Player player) {
        ItemManager itemManager = plugin.getItemManager();
        for (ItemStack item : player.getInventory()) {
            if (itemManager.isItemOf(item, StaticItem.COLLECTION_BOOK))
                return true;
        }
        return false;
    }

    @Override
    public String getRequiredPermissionKey() {
        return "minecollector.book.obtain";
    }
}
