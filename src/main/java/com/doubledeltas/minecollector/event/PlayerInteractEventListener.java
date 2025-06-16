package com.doubledeltas.minecollector.event;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteractEventListener implements Listener {
    @EventHandler
    public void handleEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;

        ItemStack mainHandItem = playerInventory.getItemInMainHand();
        ItemStack offHandItem = playerInventory.getItemInOffHand();
        if (!(isCollectionBook(mainHandItem) || isCollectionBook(offHandItem)))
            return;

        e.setCancelled(true);

        MineCollector.getInstance().getGameDirector().tryOpenHubGui(player);
    }

    private static boolean isCollectionBook(ItemStack item) {
        return MineCollector.getInstance().getItemManager().isItemOf(item, StaticItem.COLLECTION_BOOK);
    }
}
