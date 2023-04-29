package com.doubledeltas.minecollector.config.chapter;

import lombok.Data;
import org.bukkit.advancement.AdvancementDisplayType;

import java.util.Map;

@Data
public class ScoringChapter {
    private boolean collectionEnabled;
    private float   collectionScore;

    private boolean stackEnabled;
    private int     stackMultiple;
    private float   stackScore;

    private boolean advancementEnabled;
    private Map<AdvancementDisplayType, Float> advancementScores;
}
