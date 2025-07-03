package com.doubledeltas.minecollector.crew;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.yaml.Yamls;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

import static com.doubledeltas.minecollector.lang.LangManager.translateToPlainText;
import static com.doubledeltas.minecollector.lang.LangManager.translateToText;

public class CrewManager implements McolInitializable {
    private MineCollector plugin;
    private File teamDataPath;

    private final NavigableMap<UUID, Crew> byUuid = new TreeMap<>();
    private final NavigableMap<String, Crew> byId = new TreeMap<>();

    @Override
    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.teamDataPath = new File(plugin.getDataFolder(), "crew");
    }

    @SneakyThrows(IOException.class)
    public void load() {
        if (!teamDataPath.isDirectory()) {
            teamDataPath.mkdirs();
        }

        File[] teamFiles = teamDataPath.listFiles();
        for (File file: teamFiles) {
            Crew crew = Yamls.getCrewYaml().loadAs(new FileReader(file), Crew.class);
            byUuid.put(crew.getUuid(), crew);
            byId.put(crew.getId(), crew);
        }

        MessageUtil.log("crew.loaded", teamFiles.length);
    }

    /**
     * 새로운 팀을 만들고 팀 데이터를 저장합니다.
     * @param player    팀을 만드는 플레이어
     * @param id        팀 ID
     * @param name      팀 이름 (선택)
     * @return          생성된 팀
     */
    public Crew createNewCrew(@Nullable Player player, @NonNull String id, @Nullable String name) {
        if (byId.containsKey(id))
            throw new DuplicatedIdException();
        String leaderName = player != null ? player.getName() : "???";
        String defaultName = translateToPlainText("crew.default_name", leaderName);
        Crew crew = new Crew(UUID.randomUUID(), id, LocalDateTime.now());
        crew.setName(name != null ? name : defaultName);
        if (player != null)
            crew.addMember(player, true);
        save(crew);
        return crew;
    }

    public boolean save(Crew crew) {
        File path = new File(teamDataPath, crew.getUuid() + ".yml");
        try (FileWriter fw = new FileWriter(path)) {
            Yamls.getDataYaml().dump(crew, fw);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean saveAll() {
        for (Crew crew : byUuid.values()) {
            boolean result = save(crew);
            if (!result) {
                MessageUtil.log(Level.SEVERE, "crew.save_failed");
                return false;
            }
        }
        return true;
    }

    public Crew getTeam(OfflinePlayer player) {
        for (Crew crew : byUuid.values()) {
            if (crew.isMember(player))
                return crew;
        }
        return null;
    }

    public boolean hasTeam(OfflinePlayer player) {
        return getTeam(player) != null;
    }
}
