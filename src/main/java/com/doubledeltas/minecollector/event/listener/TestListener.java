package com.doubledeltas.minecollector.event.listener;

import com.doubledeltas.minecollector.event.event.ItemCollectEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TestListener implements Listener {
    @EventHandler
    public void handleEvent(ItemCollectEvent event) {
        event.setCancelled(true);
    }
}
