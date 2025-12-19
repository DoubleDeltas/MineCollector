package com.doubledeltas.minecollector.crew;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public interface CrewMembers {
    boolean isMember(OfflinePlayer offlinePlayer);

    @Nullable CrewMember getCrewMember(OfflinePlayer offlinePlayer);

    boolean addMember(OfflinePlayer offlinePlayer, boolean isLeader);

    boolean removeMember(OfflinePlayer offlinePlayer);

    List<CrewMember> toList();

    Collection<? extends Player> getOnlinePlayers(Server server);

    Collection<? extends OfflinePlayer> getOfflinePlayers();

    /**
     * 크루 멤버의 리더 상태를 변경합니다.
     * @param offlinePlayer 플레이어
     * @param leader 리더 여부
     * @return 변경 여부
     */
    boolean setLeader(OfflinePlayer offlinePlayer, boolean leader);

}
