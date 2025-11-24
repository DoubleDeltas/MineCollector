package com.doubledeltas.minecollector.crew.schema;

import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.crew.CrewMembersImpl;
import com.doubledeltas.minecollector.version.SchemaLoadingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class McolCrewSchema1_4_0 implements CurrentMcolCrewSchema {
    private String              id;
    private String              name;
    private LocalDateTime       creationTime;
    private List<CrewMember>    members;
    private String              dataVersion;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CrewMember implements McolCrewSchema.CrewMember {
        private UUID            uuid;
        private String          nickname;
        private LocalDateTime   joinedTime;
        private boolean         leader;
    }

    public static McolCrewSchema1_4_0 deserialize(Crew crew) {
        return McolCrewSchema1_4_0.builder()
                .id(crew.getId())
                .name(crew.getName())
                .creationTime(crew.getCreationTime())
                .members(crew.getMembers().toList().stream()
                        .map(src -> new CrewMember(
                                src.player().getUniqueId(),
                                src.player().getName(),
                                src.joinedTime(),
                                src.leader()
                        ))
                        .toList()
                )
                .dataVersion("1.4.0")
                .build();
    }

    @Override
    public Crew convert() throws SchemaLoadingException {
        return Crew.builder()
                .id(getId())
                .name(getName())
                .creationTime(getCreationTime())
                .members(new CrewMembersImpl(
                        getMembers().stream().map(sch -> new com.doubledeltas.minecollector.crew.CrewMember(
                                Bukkit.getOfflinePlayer(sch.getUuid()),
                                sch.getJoinedTime(),
                                sch.isLeader()
                        ))
                        .toList()))
                .build();
    }

    @Override
    public void validate() throws SchemaLoadingException {
        // no-op... for now.
    }
}
