package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.entity.Player;

public class NoticeUtil {
    public static final String PREFIX = "§8[ §a마인§f콜렉터 §8]§f";

    public static void send(Player player, String msg) {
        player.sendMessage(PREFIX + " §r§f" + msg);
    }

    public static void broadcast(String msg) {
        for (Player player: MineCollector.getPlugin().getServer().getOnlinePlayers()) {
            send(player, msg);
        }
    }
}
