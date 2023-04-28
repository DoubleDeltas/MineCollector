package com.doubledeltas.minecollector.config.chapter;

import com.doubledeltas.minecollector.config.AnnouncementTarget;

import java.util.Map;

public class AnnouncementChapter {
    public static AnnouncementChapter DEFAULT = new AnnouncementChapter(
            AnnouncementTarget.ALL_PLAYERS,
            AnnouncementTarget.ALL_PLAYERS,
            5,
            AnnouncementTarget.ALL_PLAYERS
    );

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

    public static AnnouncementChapter convert(Map<String, Object> map) {
        return new AnnouncementChapter(
                AnnouncementTarget.get((String) map.get("collection")),
                AnnouncementTarget.get((String) map.get("high level reached")),
                (Integer) map.get("high level minimum"),
                AnnouncementTarget.get((String) map.get("advancement"))
        );
    }
}
