package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.config.chapter.AnnouncementChapter;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import com.doubledeltas.minecollector.config.chapter.GameChapter;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;
import com.doubledeltas.minecollector.version.Version;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class McolConfig {
    private boolean enabled;
    private ScoringChapter scoring;
    private AnnouncementChapter announcement;
    private GameChapter game;
    private DBChapter db;
    private Version<?> version;
}
