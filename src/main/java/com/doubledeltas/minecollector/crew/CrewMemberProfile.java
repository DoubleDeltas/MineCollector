package com.doubledeltas.minecollector.crew;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CrewMemberProfile implements Comparable<CrewMemberProfile> {
    private final UUID uuid;
    private final LocalDateTime joinedTime;
    private final boolean leader;

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public String getNickname() {
        return getOfflinePlayer().getName();
    }

    @Override
    public int compareTo(CrewMemberProfile o) {
        return joinedTime.compareTo(o.joinedTime);
    }
}
