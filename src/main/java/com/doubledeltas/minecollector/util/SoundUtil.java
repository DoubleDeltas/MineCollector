package com.doubledeltas.minecollector.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
    public static void playHighRing(Player player) {
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
    }

    public static void playFail(Player player) {
        player.playSound(player, Sound.BLOCK_BEACON_DEACTIVATE, 1, 2);
    }
}
