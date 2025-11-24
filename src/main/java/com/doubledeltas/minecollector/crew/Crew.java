package com.doubledeltas.minecollector.crew;

import lombok.*;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;


@Getter @Setter @RequiredArgsConstructor @AllArgsConstructor @Builder
public class Crew implements Comparable<Crew> {
    private final String id;
    private String name;
    private final LocalDateTime creationTime;
    private final CrewMembers members;

    public Crew(String id, String name, LocalDateTime creationTime) {
        this.id = id;
        this.name = name;
        this.creationTime = creationTime;
        this.members = new CrewMembersImpl();
    }

    public boolean isMember(OfflinePlayer player) {
        return members.isMember(player);
    }

    public CrewMember getCrewMember(OfflinePlayer player) {
        return members.getCrewMember(player);
    }

    public boolean addMember(OfflinePlayer player, boolean leader) {
        return members.addMember(player, leader);
    }

    @Override
    public int compareTo(Crew o) {
        return creationTime.compareTo(o.creationTime);
    }
}
