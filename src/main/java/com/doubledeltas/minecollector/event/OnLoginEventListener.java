package com.doubledeltas.minecollector.event;

import com.doubledeltas.minecollector.data.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class OnLoginEventListener implements Listener {
    @EventHandler
    public void handleEvent(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        if (!DataManager.hasData(player))
            DataManager.addNewPlayerData(player);
    }
}
