package com.doubledeltas.minecollector.event.listener;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAdvancementDontEventListener implements Listener {
    @EventHandler
    public void handleEvent(PlayerAdvancementDoneEvent e) {
        Advancement advancement = e.getAdvancement();
        if (advancement.getDisplay() == null) return; // 도전과제가 아니라 제작법...

        MineCollector.getInstance().getGameDirector().resolveAdvancement(e.getPlayer(), advancement);
    }
}
