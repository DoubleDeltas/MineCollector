package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public enum AnnouncementTarget {
    ALL_PLAYERS,
    SELF,
    NONE,
    ;

    public List<Player> resolve(Player subject) {
        return switch (this) {
            case ALL_PLAYERS ->
                    MineCollector.getInstance().getServer().getOnlinePlayers()
                    .stream()
                    .map(player -> (Player) player)
                    .collect(Collectors.toList());
            case SELF -> List.of(subject);
            case NONE -> List.of();
        };
    }

}
