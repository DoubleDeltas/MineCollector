package com.doubledeltas.minecollector.team;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class TeamMemberProfile implements Comparable<TeamMemberProfile> {
    private final UUID uuid;
    private final LocalDateTime joinedTime;

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public String getNickname() {
        return getOfflinePlayer().getName();
    }

    @Override
    public int compareTo(TeamMemberProfile o) {
        return joinedTime.compareTo(o.joinedTime);
    }
}
