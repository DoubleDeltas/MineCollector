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

    List<CrewMember> toList();

    Collection<? extends Player> getOnlinePlayers(Server server);
}
