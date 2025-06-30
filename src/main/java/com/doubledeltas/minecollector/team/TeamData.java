package com.doubledeltas.minecollector.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class TeamData {
    private final UUID uuid;
    private final String name;
    private final List<TeamMemberProfile> memberProfiles = new ArrayList<>();
}
