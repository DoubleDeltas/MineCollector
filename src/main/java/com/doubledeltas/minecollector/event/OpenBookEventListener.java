package com.doubledeltas.minecollector.event;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.gui.HubGui;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class OpenBookEventListener implements Listener {
    @EventHandler
    public void handleEvent(PlayerInteractEvent e) {
        ItemStack collectionBook = MineCollector.getPlugin().getItemManager().getItem(StaticItem.COLLECTION_BOOK);
        Player player = e.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        if (playerInventory.getItemInMainHand().equals(collectionBook)
                || playerInventory.getItemInOffHand().equals(collectionBook))
        {
            e.setCancelled(true);
            new HubGui().openGui(player);
            SoundUtil.playPageAll(player);
        }
    }
}
