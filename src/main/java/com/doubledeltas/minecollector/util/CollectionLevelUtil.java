package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.MineCollector;

public class CollectionLevelUtil {
    public static int getLevel(int amount) {
        int multiple = MineCollector.getInstance().getMcolConfig().getScoring().getStackMultiple();

        int level = 0;
        while (amount > 0) {
            amount /= multiple;
            level++;
        }
        return level;
    }

    public static int getMinimumAmount(int level) {
        int multiple = MineCollector.getInstance().getMcolConfig().getScoring().getStackMultiple();

        int amount = 1;
        for (int i=1; i<level; i++) {
            amount *= multiple;
        }
        return amount;
    }
}
