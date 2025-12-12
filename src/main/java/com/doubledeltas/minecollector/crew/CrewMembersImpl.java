package com.doubledeltas.minecollector.crew;

import com.doubledeltas.minecollector.MineCollector;
import com.google.common.xml.XmlEscapers;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
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
    public boolean removeMember(OfflinePlayer offlinePlayer) {
        if (!isMember(offlinePlayer))
            return false;
        membership.remove(offlinePlayer.getUniqueId());
        return true;
    }

    @Override
    public List<CrewMember> toList() {
        return membership.values().stream().toList();
    }

    @Override
    public Collection<? extends Player> getOnlinePlayers(Server server) {
        return server.getOnlinePlayers().stream()
                .filter(player -> membership.containsKey(player.getUniqueId()))
                .toList();
    }

    @Override
    public Collection<? extends OfflinePlayer> getOfflinePlayers() {
        return membership.values().stream()
                .map(CrewMember::player)
                .toList();
    }
}
