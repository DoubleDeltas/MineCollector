package com.doubledeltas.minecollector.config.chapter;

public class GameChapter {
    private boolean hideUnknownCollection;
    private boolean respawnEnderegg;

    public GameChapter(boolean hideUnknownCollection, boolean respawnEnderegg) {
        this.hideUnknownCollection = hideUnknownCollection;
        this.respawnEnderegg = respawnEnderegg;
    }
}
