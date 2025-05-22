package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
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
public class McolConfigYaml implements ConfigSchema<McolConfig> {
    private boolean         enabled;
    private Scoring         scoring;
    private Announcement    announcement;
    private Game            game;
    private DB              db;
    private String          version;

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Scoring implements ConfigSchema<McolConfig.Scoring> {
        private boolean     collectionEnabled;
        private BigDecimal  collectionScore;

        private boolean     stackEnabled;
        private int         stackMultiple;
        private BigDecimal  stackScore;

        private boolean     advancementEnabled;
        private Map<AdvancementDisplayType, BigDecimal> advancementScores;

        @Override
        public McolConfig.Scoring convert() throws InvalidConfigException {
            if (getStackMultiple() < 2)
                throw new InvalidConfigException("scoring-stack multiple은 2 이상의 정수여야 합니다!");

            return new McolConfig.Scoring(
                    collectionEnabled, collectionScore, stackEnabled, stackMultiple, stackScore, advancementEnabled,
                    advancementScores
            );
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Announcement implements ConfigSchema<McolConfig.Announcement> {
        private AnnouncementTarget  collection;
        private AnnouncementTarget  highLevelReached;
        private int                 highLevelMinimum;
        private AnnouncementTarget  advancement;

        @Override
        public McolConfig.Announcement convert() throws InvalidConfigException {
            if (getHighLevelMinimum() < 2)
                throw new InvalidConfigException("announcement-high level minimum은 2 이상의 정수여야 합니다!");

            return new McolConfig.Announcement(collection, highLevelReached, highLevelMinimum, advancement);
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Game implements ConfigSchema<McolConfig.Game> {
        private boolean hideUnknownCollection;
        private boolean respawnEnderegg;

        @Override
        public McolConfig.Game convert() throws InvalidConfigException {
            return new McolConfig.Game(hideUnknownCollection, respawnEnderegg);
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DB implements ConfigSchema<McolConfig.DB> {
        private int     autosavePeriod;
        private boolean autosaveLogging;

        @Override
        public McolConfig.DB convert() throws InvalidConfigException {
            if (getAutosavePeriod() < 0)
                throw new InvalidConfigException("db-autosave period는 0 또는 양의 정수여야 합니다!");

            return new McolConfig.DB(
                    Duration.ofMinutes(autosavePeriod),
                    autosaveLogging
            );
        }
    }

    @Override
    public McolConfig convert() throws InvalidConfigException {
        Version<?> version;
        try {
            version = MineCollector.getInstance().getVersionSystemManager().parse(this.version);
        } catch (IllegalArgumentException ex) {
            throw new InvalidConfigException("version이 잘못되었습니다!", ex);
        }

        return new McolConfig(enabled, scoring.convert(), announcement.convert(), game.convert(), db.convert(), version);
    }
}
