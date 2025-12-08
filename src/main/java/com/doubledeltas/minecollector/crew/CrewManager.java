package com.doubledeltas.minecollector.crew;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.crew.schema.McolCrewMeta;
import com.doubledeltas.minecollector.crew.schema.McolCrewSchema;
import com.doubledeltas.minecollector.crew.schema.McolCrewSchema1_4_0;
import com.doubledeltas.minecollector.util.BukkitTaskChain;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import com.doubledeltas.minecollector.version.*;
import com.doubledeltas.minecollector.yaml.Yamls;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;

import static com.doubledeltas.minecollector.lang.LangManager.translateToPlainText;

public class CrewManager implements McolInitializable {
    private MineCollector plugin;
    private File teamDataPath;

    private final NavigableMap<String, Crew> byId = new TreeMap<>();

    private VersionSchemaTable<McolCrewSchema> schemaTable;
    private VersionUpdaterChain<McolCrewSchema> updaterChain;

    private final Multimap<Player, Crew> applications = HashMultimap.create();
    private final Multimap<Crew, Player> invitations = HashMultimap.create();

    private static final Duration APPLICATION_TTL = Duration.ofSeconds(60);
    private static final Duration INVITATION_TTL = Duration.ofSeconds(60);

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
        schemaTable.registerSchema(Versions.V1_4_0, McolCrewSchema1_4_0.class);

        this.updaterChain = new VersionUpdaterChain<>(versionManager, Versions.V1_4_0);
    }

    public void load() {
        if (!teamDataPath.isDirectory()) {
            teamDataPath.mkdirs();
        }

        File[] teamFiles = teamDataPath.listFiles();
        for (File file: teamFiles) {
            Crew crew = new SchemaLoader<>(Yamls.GENERAL, schemaTable, updaterChain) {
                @Override
                public void onNewerVersionDetected() {
                    MessageUtil.log(Level.WARNING, "crew.higher_version_warning");
                }
            }.load(file);

            byId.put(crew.getId(), crew);
        }

        MessageUtil.log("crew.loaded", teamFiles.length);
    }

    /**
     * 새로운 팀을 만들고 팀 데이터를 저장합니다.
     * @param creator    팀을 만드는 플레이어
     * @param id        팀 ID
     * @param name      팀 이름 (선택)
     * @return          생성된 팀
     */
    public Crew createNewCrew(@Nullable Player creator, @NonNull String id, @Nullable String name) {
        if (byId.containsKey(id))
            throw new DuplicatedIdException();

        String leaderName = creator != null ? creator.getName() : "???";
        String defaultName = translateToPlainText("crew.default_name", leaderName);
        Crew crew = new Crew(id, name != null ? name : defaultName, LocalDateTime.now());

        byId.put(crew.getId(), crew);
        return crew;
    }

    public boolean save(Crew crew) {
        File path = new File(teamDataPath, crew.getId() + ".yml");
        try (FileWriter fw = new FileWriter(path)) {
            Yamls.GENERAL.dump(McolCrewSchema.deserialize(crew), fw);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean saveAll() {
        for (Crew crew : byId.values()) {
            boolean result = save(crew);
            if (!result) {
                MessageUtil.log(Level.SEVERE, "crew.save_failed");
                return false;
            }
        }
        return true;
    }

    public Crew getCrew(OfflinePlayer player) {
        for (Crew crew : byId.values()) {
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

    public void apply(Player player, Crew crew) {
        BukkitTaskChain.create(plugin)
                .then(() -> {
                    applications.put(player, crew);
                })
                .waitForSeconds(APPLICATION_TTL.getSeconds())
                .then(() -> {
                    if (applications.containsEntry(player, crew)) {
                        applications.remove(player, crew);
                    }
                })
                .run();
    }

    public void acceptApplication(Player applicant, Crew crew) {

    }

    public void invite(Crew crew, Player inviter, Player invitee) {
        BukkitTaskChain.create(plugin)
                .then(() -> {
                    invitations.put(crew, invitee);
                })
                .waitForSeconds(INVITATION_TTL.getSeconds())
                .then(() -> {
                    if (invitations.containsEntry(crew, invitee)) {
                        invitations.remove(crew, invitee);
                    }
                })
                .run();
    }

    public void acceptInvitation(Crew crew, Player invitee) {
        invitations.remove(crew, invitee);
        crew.addMember(invitee, false);

        crew.getMembers().getOnlinePlayers(plugin.getServer()).forEach(player -> {
            MessageUtil.send(player, "crew.newcomer_joined");
            SoundUtil.playHighRing(player);
        });
    }

    public boolean isApplicationPending(Player player, Crew crew) {
        return applications.containsEntry(player, crew);
    }

    public boolean isInvitationPending(Crew crew, Player player) {
        return invitations.containsEntry(crew, player);
    }
}
