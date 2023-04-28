package com.doubledeltas.minecollector.config.chapter;

import java.util.Map;

public class GameChapter {
    public static GameChapter DEFAULT = new GameChapter(
            true,
            true
    );

    private boolean hideUnknownCollection;
    private boolean respawnEnderegg;

    public GameChapter(boolean hideUnknownCollection, boolean respawnEnderegg) {
        this.hideUnknownCollection = hideUnknownCollection;
        this.respawnEnderegg = respawnEnderegg;
    }

    public static GameChapter convert(Map<String, Object> map) {

    }


}
