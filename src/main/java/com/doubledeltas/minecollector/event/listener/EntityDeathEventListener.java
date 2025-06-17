package com.doubledeltas.minecollector.event.listener;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import java.time.Duration;

public class EntityDeathEventListener implements Listener {
    private static final Duration ENDER_DRAGON_DEATH_ANIMATION_PLAYTIME = Duration.ofSeconds(10);

    @EventHandler
    public void handleEvent(EntityDeathEvent e) {
        if (e.getEntityType() != EntityType.ENDER_DRAGON)
            return;
        EnderDragon enderDragon = (EnderDragon) e.getEntity();
        DragonBattle battle = enderDragon.getDragonBattle();
        if (battle == null || !battle.hasBeenPreviouslyKilled())   // first kill
            return;
        McolConfig.Game gameConfig = MineCollector.getInstance().getMcolConfig().getGame();
        if (!gameConfig.isRespawnEnderegg())
            return;

        Location location = battle.getEndPortalLocation().add(new Vector(0, 5, 0));

        Bukkit.getScheduler().runTaskLater(MineCollector.getInstance(), () -> {
            location.getBlock().setType(Material.DRAGON_EGG, true);
        }, TimeUtil.toTicks(ENDER_DRAGON_DEATH_ANIMATION_PLAYTIME));
    }
}
