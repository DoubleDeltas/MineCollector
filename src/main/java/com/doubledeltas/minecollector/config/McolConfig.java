package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.config.chapter.AnnouncementChapter;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import com.doubledeltas.minecollector.config.chapter.GameChapter;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;
import lombok.Data;

@Data
public class McolConfig {
    private boolean enabled;
    private ScoringChapter scoring;
    private AnnouncementChapter announcement;
    private GameChapter game;
    private DBChapter db;
}
