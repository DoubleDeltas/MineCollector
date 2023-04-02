package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class SoundUtil {
    public static void playHighRing(Player player) {
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
    }

    public static void playFail(Player player) {
        player.playSound(player, Sound.BLOCK_BEACON_DEACTIVATE, 1, 2);
    }

    public static void playPageAll(Player player) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        for (int i=0; i<6; i++) {
            scheduler.runTaskLater(MineCollector.getPlugin(), () -> {
                for (int j=0; j<2; j++)
                    player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1F, 2F);
            }, 2 + i);
        }
    }

    public static void playPage(Player player) {
        player.playSound(player, Sound.UI_LOOM_SELECT_PATTERN, 0.5F, 1F);
    }

    public static void playCollect(Player player) {
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
        player.playSound(player, Sound.BLOCK_BEACON_ACTIVATE, 1F, 2F);
    }
}
