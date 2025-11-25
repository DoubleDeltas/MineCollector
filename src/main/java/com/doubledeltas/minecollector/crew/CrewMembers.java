package com.doubledeltas.minecollector.crew;

import org.bukkit.OfflinePlayer;

import javax.annotation.Nullable;
import java.util.List;

public interface CrewMembers {
    boolean isMember(OfflinePlayer offlinePlayer);

    @Nullable CrewMember getCrewMember(OfflinePlayer offlinePlayer);

    boolean addMember(OfflinePlayer offlinePlayer, boolean isLeader);

    List<CrewMember> toList();
}
