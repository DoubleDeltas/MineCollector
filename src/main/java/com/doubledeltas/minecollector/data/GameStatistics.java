package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.McolConfig;
import lombok.Getter;
import org.bukkit.advancement.AdvancementDisplayType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

@Getter
public class GameStatistics {
    private BigDecimal totalScore;

    private BigDecimal collectionScore;
    private BigDecimal stackScore;
    private BigDecimal advScore;

    /**
     * 게임 데이터로부터 통계를 구합니다.
     * @param data 게임 데이터
     */
    public GameStatistics(GameData data) {
        McolConfig.Scoring scoringConfig = MineCollector.getInstance().getMcolConfig().getScoring();
        Map<AdvancementDisplayType, BigDecimal> advancementScores = scoringConfig.getAdvancementScores();

        Map<String, Integer> collectionMap = data.getCollectionMap();

        this.totalScore = BigDecimal.ZERO;

        if (scoringConfig.isCollectionEnabled()) {
            collectionScore = BigDecimal.valueOf(collectionMap.size());
            totalScore = totalScore.add(collectionScore);
        }
        else {
            collectionScore = null;
        }

        if (scoringConfig.isStackEnabled()) {
            BigDecimal stackScoreStep = MineCollector.getInstance().getMcolConfig().getScoring().getStackScore();
            stackScore = collectionMap.keySet().stream()
                    .map(key -> BigDecimal.valueOf(data.getLevel(key) - 1).multiply(stackScoreStep))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalScore = totalScore.add(stackScore);
        }
        else {
            stackScore = null;
        }

        if (scoringConfig.isAdvancementEnabled()) {
            advScore = Arrays.stream(AdvancementDisplayType.values())
                    .map(type -> BigDecimal.valueOf(data.getAdvCleared(type)).multiply(advancementScores.get(type)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalScore = totalScore.add(advScore);
        }
        else {
            advScore = null;
        }
    }

    /**
     * 게임 데이터 통계를 수정할 수 없는 Map으로 만듭니다.
     * @return Map
     */
    public Map<String, BigDecimal> toMap() {
        return Map.of(
                "collectionScore", collectionScore,
                "stackScore", stackScore,
                "advScore", advScore,
                "totalScore", totalScore
        );
    }
}
