package com.doubledeltas.minecollector.event.listener;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {
    @EventHandler
    public void handleEvent(PlayerJoinEvent e) {
        MineCollector plugin = MineCollector.getInstance();
        DataManager dataManager = plugin.getDataManager();

        Player player = e.getPlayer();
        if (!dataManager.hasData(player)) {
            player.getInventory().addItem(plugin.getItemManager().getItem(StaticItem.COLLECTION_BOOK));
            dataManager.addNewPlayerData(player);
        }

        // if player name is changed, the name in data is also changed.
        dataManager.getData(player).setName(player.getName());
    }
}
