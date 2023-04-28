package com.doubledeltas.minecollector.config.chapter;

import java.util.Map;

public class DBChapter {
    public static DBChapter DEFAULT = new DBChapter(
            10,
            true
    );

    private int     autosavePeriod;
    private boolean autosaveLogging;

    public DBChapter(int autosavePeriod, boolean autosaveLogging) {
        this.autosavePeriod = autosavePeriod;
        this.autosaveLogging = autosaveLogging;
    }

    public static DBChapter convert(Map<String, Object> map) {
        int autosavePeriod;
        int autosaveLogging;

        try {

        }
        catch (NullPointerException e) {

        }
    }
}
