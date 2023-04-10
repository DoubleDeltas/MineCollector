package com.doubledeltas.minecollector.data;

import java.util.Map;

public class GameStatistics {
    private float totalScore;

    private float collectScore;
    private float stackScore;
    private float advScore;

    /**
     * 게임 데이터로부터 통계를 구합니다.
     * @param data 게임 데이터
     */
    public GameStatistics(GameData data) {
        Map<String, Integer> collectionMap = data.getCollectionMap();

        this.collectScore = collectionMap.size();

        this.stackScore = 0.0F;
        for (String key: collectionMap.keySet()) {
            this.stackScore += (data.getLevel(key) - 1) * 0.1F;
        }

        this.advScore = 0.0F;

        this.totalScore = collectScore + stackScore + advScore;
    }

    public float getTotalScore() { return totalScore; }
    public float getCollectScore() { return collectScore; }
    public float getStackScore() { return stackScore; }
    public float getAdvScore() { return advScore; }
}
