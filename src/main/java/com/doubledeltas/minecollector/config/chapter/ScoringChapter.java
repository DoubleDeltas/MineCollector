package com.doubledeltas.minecollector.config.chapter;

import org.bukkit.advancement.AdvancementDisplayType;

import java.util.Map;

public class ScoringChapter {
    public static ScoringChapter DEFAULT = new ScoringChapter(
            true,
            1.0F,
            true,
            4,
            0.1F,
            true,
            Map.of(
                    AdvancementDisplayType.TASK, 1.0F,
                    AdvancementDisplayType.GOAL, 2.0F,
                    AdvancementDisplayType.CHALLENGE, 3.0F
            )
    );

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

    public static ScoringChapter convert(Map<String, Object> map) {

    }
}
