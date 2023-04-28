package com.doubledeltas.minecollector.config.chapter;

public class DBChapter {
    private int     autosavePeriod;
    private boolean autosaveLogging;

    public DBChapter(int autosavePeriod, boolean autosaveLogging) {
        this.autosavePeriod = autosavePeriod;
        this.autosaveLogging = autosaveLogging;
    }
}
