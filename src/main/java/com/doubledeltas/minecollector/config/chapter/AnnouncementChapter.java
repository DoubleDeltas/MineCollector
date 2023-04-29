package com.doubledeltas.minecollector.config.chapter;

import com.doubledeltas.minecollector.config.AnnouncementTarget;
import lombok.Data;

@Data
public class AnnouncementChapter {
    private AnnouncementTarget  collection;
    private AnnouncementTarget  highLevelReached;
    private int                 highLevelMinimum;
    private AnnouncementTarget  advancement;
}
