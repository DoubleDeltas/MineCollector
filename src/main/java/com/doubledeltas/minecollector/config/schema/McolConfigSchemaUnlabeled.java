package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.config.AnnouncementTarget;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.version.UnlabeledVersion;
import com.doubledeltas.minecollector.version.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bukkit.advancement.AdvancementDisplayType;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class McolConfigSchemaUnlabeled implements McolConfigSchema {
    private boolean         enabled                 = true;
    private Scoring         scoring                 = new Scoring();
    private Announcement    announcement            = new Announcement();
    private Game            game                    = new Game();
    private DB              db                      = new DB();

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Scoring implements McolConfigSchema.Scoring {
        private boolean     collectionEnabled       = true;
        private BigDecimal  collectionScore         = new BigDecimal("1");

        private boolean     stackEnabled            = true;
        private int         stackMultiple           = 4;
        private BigDecimal  stackScore              = new BigDecimal("0.1");

        private boolean     advancementEnabled  = true;
        private Map<AdvancementDisplayType, BigDecimal> advancementScores = Map.of(
                AdvancementDisplayType.TASK,        new BigDecimal("1.0"),
                AdvancementDisplayType.GOAL,        new BigDecimal("2.0"),
                AdvancementDisplayType.CHALLENGE,   new BigDecimal("3.0")
        );
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Announcement implements McolConfigSchema.Announcement {
        private AnnouncementTarget  collection          = AnnouncementTarget.ALL_PLAYERS;
        private AnnouncementTarget  highLevelReached    = AnnouncementTarget.ALL_PLAYERS;
        private int                 highLevelMinimum    = 5;
        private AnnouncementTarget  advancement         = AnnouncementTarget.SELF;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Game implements McolConfigSchema.Game {
        private boolean hideUnknownCollection           = true;
        private boolean respawnEnderegg                 = true;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DB implements McolConfigSchema.DB {
        private int     autosavePeriod;
        private boolean autosaveLogging;
    }

    @Override
    public void validate() throws InvalidConfigException {
        if (scoring.getStackMultiple() < 2)
            throw new InvalidConfigException("scoring-stack multiple은 2 이상의 정수여야 합니다!");

        if (announcement.getHighLevelMinimum() < 2)
            throw new InvalidConfigException("announcement-high level minimum은 2 이상의 정수여야 합니다!");

        if (db.getAutosavePeriod() < 0)
            throw new InvalidConfigException("db-autosave period는 0 또는 양의 정수여야 합니다!");
    }

    @Override
    public String getConfigVersion() {
        return UnlabeledVersion.INSTANCE.toString();
    }

    @Override
    public McolConfig convert() throws InvalidConfigException {
        validate();
        return getConfigBuilder().build();
    }

    protected McolConfig.McolConfigBuilder getConfigBuilder() {
        return McolConfig.builder()
                .enabled(isEnabled())
                .scoring(McolConfig.Scoring.builder()
                        .collectionEnabled(getScoring().isCollectionEnabled())
                        .collectionScore(getScoring().getCollectionScore())
                        .stackEnabled(getScoring().isStackEnabled())
                        .stackMultiple(getScoring().getStackMultiple())
                        .stackScore(getScoring().getStackScore())
                        .build())
                .announcement(McolConfig.Announcement.builder()
                        .collection(getAnnouncement().getCollection())
                        .highLevelReached(getAnnouncement().getHighLevelReached())
                        .highLevelMinimum(getAnnouncement().getHighLevelMinimum())
                        .advancement(getAnnouncement().getAdvancement())
                        .build())
                .game(McolConfig.Game.builder()
                        .hideUnknownCollection(getGame().isHideUnknownCollection())
                        .respawnEnderegg(getGame().isRespawnEnderegg())
                        .build())
                .db(McolConfig.DB.builder()
                        .autosavePeriod(Duration.ofMinutes(getDb().getAutosavePeriod()))
                        .autosaveLogging(getDb().isAutosaveLogging())
                        .build())
                .configVersion(Version.parse(null));
    }
}
