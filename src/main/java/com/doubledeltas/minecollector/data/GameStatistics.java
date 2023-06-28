package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;
import lombok.Getter;
import org.bukkit.advancement.AdvancementDisplayType;

import java.util.Arrays;
import java.util.Map;

@Getter
public class GameStatistics {

    private float totalScore;

    private float collectionScore;
    private float stackScore;
    private float advScore;

    /**
     * 게임 데이터로부터 통계를 구합니다.
     * @param data 게임 데이터
     */
    public GameStatistics(GameData data) {
        ScoringChapter scoringConfig = MineCollector.getInstance().getMcolConfig().getScoring();
        Map<AdvancementDisplayType, Float> advancementScores = scoringConfig.getAdvancementScores();

        Map<String, Integer> collectionMap = data.getCollection();

        this.totalScore = 0.0F;

        if (scoringConfig.isCollectionEnabled()) {
            this.collectionScore = collectionMap.size();
            this.totalScore += collectionScore;
        }
        else {
            this.collectionScore = Float.NaN;
        }

        if (scoringConfig.isStackEnabled()) {
            this.stackScore = 0.0F;
            for (String key: collectionMap.keySet()) {
                this.stackScore += (data.getLevel(key) - 1) * 0.1F;
            }
            this.totalScore += stackScore;
        }
        else {
            this.stackScore = Float.NaN;
        }

        if (scoringConfig.isAdvancementEnabled()) {
            this.advScore = (float) Arrays.stream(AdvancementDisplayType.values())
                    .mapToDouble(type -> data.getAdvCleared(type) * advancementScores.get(type))
                    .sum();
            this.totalScore += advScore;
        }
        else {
            this.advScore = Float.NaN;
        }
    }

    /**
     * 게임 데이터 통계를 수정할 수 없는 Map으로 만듭니다.
     * @return Map
     */
    public Map<String, Float> toMap() {
        return Map.of(
                "collectionScore", collectionScore,
                "stackScore", stackScore,
                "advScore", advScore,
                "totalScore", totalScore
        );
    }
}
