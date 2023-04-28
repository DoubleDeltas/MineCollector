package com.doubledeltas.minecollector.config.chapter;

import com.doubledeltas.minecollector.config.AnnouncementTarget;

public class AnnouncementChapter {
    private AnnouncementTarget  collection;
    private AnnouncementTarget  highLevelReached;
    private int                 highLevelMinimum;
    private AnnouncementTarget  advancement;

    public AnnouncementChapter(
            AnnouncementTarget  collection,
            AnnouncementTarget  highLevelReached,
            int                 highLevelMinimum,
            AnnouncementTarget  advancement
    ) {
        this.collection = collection;
        this.highLevelReached = highLevelReached;
        this.highLevelMinimum = highLevelMinimum;
        this.advancement = advancement;
    }
}
