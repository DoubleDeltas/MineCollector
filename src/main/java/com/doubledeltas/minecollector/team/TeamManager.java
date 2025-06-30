package com.doubledeltas.minecollector.team;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import static com.doubledeltas.minecollector.lang.LangManager.translateToText;

import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.yaml.Yamls;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

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

    public void createNewTeam(Player player) {
        TeamData teamData = new TeamData(
                UUID.randomUUID(),
                translateToText("team.default_name", player.getName())
        );
        save(teamData);
    }

    public boolean save(TeamData teamData) {
        File path = new File(teamDataPath, teamData.getUuid() + ".yml");
        try (FileWriter fw = new FileWriter(path)) {
            Yamls.getDataYaml().dump(teamData, fw);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean saveAll() {
        for (TeamData data: teamDataMap.values()) {
            boolean result = save(data);
            if (!result) {
                MessageUtil.log(Level.SEVERE, "team.save_failed");
                return false;
            }
        }
        return true;

    }
}
