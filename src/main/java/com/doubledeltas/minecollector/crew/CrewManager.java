package com.doubledeltas.minecollector.crew;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.crew.schema.McolCrewMeta;
import com.doubledeltas.minecollector.crew.schema.McolCrewSchema;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.version.*;
import com.doubledeltas.minecollector.yaml.Yamls;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.error.YAMLException;

import javax.annotation.Nullable;
import java.io.*;
import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

import static com.doubledeltas.minecollector.lang.LangManager.translateToPlainText;

public class CrewManager implements McolInitializable {
    private MineCollector plugin;
    private File teamDataPath;

    private final NavigableMap<UUID, Crew> byUuid = new TreeMap<>();
    private final NavigableMap<String, Crew> byId = new TreeMap<>();

    private VersionSchemaTable<McolCrewSchema> schemaTable;
    private VersionUpdaterChain<McolCrewSchema> updaterChain;

    @Override
    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.teamDataPath = new File(plugin.getDataFolder(), "crew");

        VersionManager versionManager = plugin.getVersionManager();
        this.schemaTable = new VersionSchemaTable<>(
                versionManager,
                McolCrewMeta.class,
                (table, schema) -> table.getVersionManager().parse(schema.getDataVersion())
        );
        schemaTable.registerSchema(Versions.V1_4_0, McolCrewSchema.class);

        this.updaterChain = new VersionUpdaterChain<>(versionManager, Versions.V1_4_0);
    }

    public void load() {
        if (!teamDataPath.isDirectory()) {
            teamDataPath.mkdirs();
        }

        File[] teamFiles = teamDataPath.listFiles();
        for (File file: teamFiles) {
            Crew crew = loadCrewData(file);

            byUuid.put(crew.getUuid(), crew);
            byId.put(crew.getId(), crew);
        }

        MessageUtil.log("crew.loaded", teamFiles.length);
    }

    public Crew loadCrewData(File file) {
        return new SchemaLoader<>(Yamls.GENERAL, schemaTable, updaterChain)
                .onNewerVersionDetected(ctx ->
                        MessageUtil.log(Level.WARNING, "config.higher_version_warning")
                )
                .onFinished(ctx ->
                        MessageUtil.log("config.loaded")
                )
                .load(file);
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
        save(crew);
        return crew;
    }

    public boolean save(Crew crew) {
        File path = new File(teamDataPath, crew.getUuid() + ".yml");
        try (FileWriter fw = new FileWriter(path)) {
            Yamls.DATA.dump(crew, fw);
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

    public Crew getCrew(OfflinePlayer player) {
        for (Crew crew : byUuid.values()) {
            if (crew.isMember(player))
                return crew;
        }
        return null;
    }

    /**
     * 플레이어가 가입된 크루가 있는지 확인합니다.
     * @param player Nullable, 크루를 확인할 플레이어
     * @return 크루의 가입 여부, {@code player == null}아면 {@code false}
     */
    public boolean hasCrew(OfflinePlayer player) {
        if (player == null)
            return false;
        return getCrew(player) != null;
    }
}
