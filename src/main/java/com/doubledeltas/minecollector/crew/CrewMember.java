package com.doubledeltas.minecollector.crew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

@AllArgsConstructor @Getter
public class CrewMember implements Comparable<CrewMember> {
    private @Nonnull OfflinePlayer player;
    private @Nonnull LocalDateTime joinedTime;
    @Setter
    private boolean leader;

    @Override
    public int compareTo(@Nonnull CrewMember o) {
        return joinedTime.compareTo(o.joinedTime);
    }
}