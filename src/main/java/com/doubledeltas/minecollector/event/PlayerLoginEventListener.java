package com.doubledeltas.minecollector.event;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginEventListener implements Listener {
    @EventHandler
    public void handleEvent(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        if (!DataManager.hasData(player)) {
            player.getInventory().addItem(MineCollector.getInstance().getItemManager().getItem(StaticItem.COLLECTION_BOOK));
            DataManager.addNewPlayerData(player);
        }

        // if player name is changed, the name in data is also changed.
        DataManager.getData(player).setName(player.getName());
    }
}
