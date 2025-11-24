package com.doubledeltas.minecollector.crew;

import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;

public record CrewMember(
        OfflinePlayer player,
        LocalDateTime joinedTime,
        boolean leader
) implements Comparable<CrewMember> {
    @Override
    public int compareTo(CrewMember o) {
        return joinedTime.compareTo(o.joinedTime);
    }
}
