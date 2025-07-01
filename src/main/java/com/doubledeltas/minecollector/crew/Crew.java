package com.doubledeltas.minecollector.crew;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Getter @Setter
public class Crew implements Comparable<Crew> {
    private final UUID uuid;
    private final String id;
    private String name;
    private final LocalDateTime creationTime;
    private final Map<UUID, CrewMemberProfile> memberProfileMap = new HashMap<>();

    public boolean isMember(OfflinePlayer player) {
        return memberProfileMap.containsKey(player.getUniqueId());
    }

    public CrewMemberProfile getProfile(OfflinePlayer player) {
        if (!isMember(player))
            return null;
        return memberProfileMap.get(player.getUniqueId());
    }

    public boolean addMember(OfflinePlayer player, boolean leader) {
        if (isMember(player))
            return false;
        UUID uuid = player.getUniqueId();
        CrewMemberProfile profile = new CrewMemberProfile(uuid, LocalDateTime.now(), leader);
        memberProfileMap.put(uuid, profile);
        return true;
    }

    public boolean addMember(OfflinePlayer player) {
        return addMember(player, false);
    }

    @Override
    public int compareTo(Crew o) {
        return creationTime.compareTo(o.creationTime);
    }
}
