package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.version.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.advancement.AdvancementDisplayType;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class McolConfig {
    private boolean         enabled;
    private Scoring         scoring;
    private Announcement    announcement;
    private Game            game;
    private DB              db;
    private Version<?>      version;

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Scoring {
        private boolean     collectionEnabled;
        private BigDecimal  collectionScore;

        private boolean     stackEnabled;
        private int         stackMultiple;
        private BigDecimal  stackScore;

        private boolean     advancementEnabled;
        private Map<AdvancementDisplayType, BigDecimal> advancementScores;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Announcement {
        private AnnouncementTarget  collection;
        private AnnouncementTarget  highLevelReached;
        private int                 highLevelMinimum;
        private AnnouncementTarget  advancement;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Game {
        private boolean hideUnknownCollection;
        private boolean respawnEnderegg;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DB {
        private Duration    autosavePeriod;
        private boolean     autosaveLogging;
    }
}
