package com.doubledeltas.minecollector.team;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.yaml.Yamls;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TeamManager implements McolInitializable {
    private MineCollector plugin;
    private File teamDataPath;

    private final NavigableMap<UUID, TeamData> teamDataMap = new TreeMap<>();

    @Override
    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.teamDataPath = new File(plugin.getDataFolder(), "team");
    }

    @SneakyThrows(IOException.class)
    public void load() {
        if (!teamDataPath.isDirectory()) {
            teamDataPath.mkdirs();
        }

        File[] teamFiles = teamDataPath.listFiles();
        for (File file: teamFiles) {
            TeamData teamData = Yamls.getDataYaml().load(new FileReader(file));
            UUID uuid = teamData.getUuid();
            teamDataMap.put(uuid, teamData);
        }

        MessageUtil.log("team.loaded", teamFiles.length);
    }
}
