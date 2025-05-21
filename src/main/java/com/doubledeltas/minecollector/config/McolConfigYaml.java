package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.chapter.AnnouncementChapter;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import com.doubledeltas.minecollector.config.chapter.GameChapter;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;
import com.doubledeltas.minecollector.version.Version;
import com.doubledeltas.minecollector.yaml.YamlSchema;
import lombok.Data;

@Data
public class McolConfigYaml implements YamlSchema<McolConfig, InvalidConfigException> {
    private boolean enabled;
    private ScoringChapter scoring;
    private AnnouncementChapter announcement;
    private GameChapter game;
    private DBChapter db;
    private String version;

    @Override
    public McolConfig convert() throws InvalidConfigException {
        if (getAnnouncement().getHighLevelMinimum() < 2)
            throw new InvalidConfigException("announcement-high level minimum은 2 이상의 정수여야 합니다!");

        if (getScoring().getStackMultiple() < 2)
            throw new InvalidConfigException("scoring-stack multiple은 2 이상의 정수여야 합니다!");

        if (getDb().getAutosavePeriod() < 0)
            throw new InvalidConfigException("db-autosave period는 0 또는 양의 정수여야 합니다!");

        Version<?> version;
        try {
            version = MineCollector.getInstance().getVersionSystemManager().parse(this.version);
        } catch (IllegalArgumentException ex) {
            throw new InvalidConfigException("version이 잘못되었습니다!", ex);
        }

        return new McolConfig(enabled, scoring, announcement, game, db, version);
    }
}
