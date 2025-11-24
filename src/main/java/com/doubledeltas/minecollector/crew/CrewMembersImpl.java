package com.doubledeltas.minecollector.crew;

import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
public class CrewMembersImpl implements CrewMembers {
    private final Map<UUID, CrewMember> membership = new HashMap<>();

    public CrewMembersImpl(Collection<CrewMember> members) {
        for (CrewMember member : members) {
            membership.put(member.player().getUniqueId(), member);
        }
    }

    @Override
    public boolean isMember(OfflinePlayer offlinePlayer) {
        return membership.containsKey(offlinePlayer.getUniqueId());
    }

    @Nullable
    @Override
    public CrewMember getCrewMember(OfflinePlayer offlinePlayer) {
        return membership.get(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean addMember(OfflinePlayer offlinePlayer, boolean isLeader) {
        if (isMember(offlinePlayer))
            return false;
        CrewMember profile = new CrewMember(offlinePlayer, LocalDateTime.now(), isLeader);
        membership.put(offlinePlayer.getUniqueId(), profile);
        return true;
    }

    @Override
    public List<CrewMember> toList() {
        return membership.values().stream().toList();
    }
}
