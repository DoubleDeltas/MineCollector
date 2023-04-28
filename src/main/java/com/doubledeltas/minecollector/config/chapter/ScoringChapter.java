package com.doubledeltas.minecollector.config.chapter;

import org.bukkit.advancement.AdvancementDisplayType;

import java.util.Map;

public class ScoringChapter {
    private boolean collectionEnabled;
    private float   collectionScore;

    private boolean stackEnabled;
    private int     stackMultiple;
    private float   stackScore;

    private boolean advancementEnabled;
    private Map<AdvancementDisplayType, Float> advancementScores;

    public ScoringChapter(
            boolean collectionEnabled,
            float   collectionScore,
            boolean stackEnabled,
            int     stackMultiple,
            float   stackScore,
            boolean advancementEnabled,
            Map<AdvancementDisplayType, Float> advancementScores
    ) {
        this.collectionEnabled = collectionEnabled;
        this.collectionScore = collectionScore;
        this.stackEnabled = stackEnabled;
        this.stackMultiple = stackMultiple;
        this.stackScore = stackScore;
        this.advancementEnabled = advancementEnabled;
        this.advancementScores = advancementScores;
    }
}
