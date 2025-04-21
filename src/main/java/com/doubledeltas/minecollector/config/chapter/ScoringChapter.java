package com.doubledeltas.minecollector.config.chapter;

import lombok.Data;
import org.bukkit.advancement.AdvancementDisplayType;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ScoringChapter {
    private boolean     collectionEnabled;
    private BigDecimal  collectionScore;

    private boolean     stackEnabled;
    private int         stackMultiple;
    private float       stackScore;

    private boolean     advancementEnabled;
    private Map<AdvancementDisplayType, BigDecimal> advancementScores;
}
