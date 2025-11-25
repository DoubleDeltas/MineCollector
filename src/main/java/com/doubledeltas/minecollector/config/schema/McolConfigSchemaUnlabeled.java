package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.config.AnnouncementTarget;
import com.doubledeltas.minecollector.version.SchemaLoadingException;
import com.doubledeltas.minecollector.version.UnlabeledVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bukkit.advancement.AdvancementDisplayType;

import java.math.BigDecimal;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class McolConfigSchemaUnlabeled implements McolConfigSchema {
    @Builder.Default
    private boolean         enabled         = true;
    @Builder.Default
    private Scoring         scoring         = new Scoring();
    @Builder.Default
    private Announcement    announcement    = new Announcement();
    @Builder.Default
    private Game            game            = new Game();
    @Builder.Default
    private DB              db              = new DB();

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Scoring implements McolConfigSchema.Scoring {
        @Builder.Default
        private boolean     collectionEnabled   = true;
        @Builder.Default
        private BigDecimal  collectionScore     = new BigDecimal("1");

        @Builder.Default
        private boolean     stackEnabled        = true;
        @Builder.Default
        private int         stackMultiple       = 4;
        @Builder.Default
        private BigDecimal  stackScore          = new BigDecimal("0.1");

        @Builder.Default
        private boolean     advancementEnabled  = true;
        @Builder.Default
        private Map<AdvancementDisplayType, BigDecimal> advancementScores = Map.of(
                AdvancementDisplayType.TASK,        new BigDecimal("1"),
                AdvancementDisplayType.GOAL,        new BigDecimal("2"),
                AdvancementDisplayType.CHALLENGE,   new BigDecimal("3")
        );
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Announcement implements McolConfigSchema.Announcement {
        @Builder.Default
        private AnnouncementTarget  collection          = AnnouncementTarget.ALL_PLAYERS;
        @Builder.Default
        private AnnouncementTarget  highLevelReached    = AnnouncementTarget.ALL_PLAYERS;
        @Builder.Default
        private int                 highLevelMinimum    = 5;
        @Builder.Default
        private AnnouncementTarget  advancement         = AnnouncementTarget.SELF;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Game implements McolConfigSchema.Game {
        @Builder.Default
        private boolean hideUnknownCollection           = true;
        @Builder.Default
        private boolean respawnEnderegg                 = true;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DB implements McolConfigSchema.DB {
        @Builder.Default
        private int     autosavePeriod  = 10;
        @Builder.Default
        private boolean autosaveLogging = true;
    }

    @Override
    public void validate() throws SchemaLoadingException {
        if (scoring.getStackMultiple() < 2)
            throw new SchemaLoadingException("scoring-stack multiple은 2 이상의 정수여야 합니다!");

        if (announcement.getHighLevelMinimum() < 2)
            throw new SchemaLoadingException("announcement-high level minimum은 2 이상의 정수여야 합니다!");

        if (db.getAutosavePeriod() < 0)
            throw new SchemaLoadingException("db-autosave period는 0 또는 양의 정수여야 합니다!");
    }

    @Override
    public String getConfigVersion() {
        return UnlabeledVersion.INSTANCE.toString();
    }
}
