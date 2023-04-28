package com.doubledeltas.minecollector.config;

import java.util.Locale;

public enum AnnouncementTarget {
    ALL_PLAYERS("all players"),
    SELF("self"),
    NONE("none"),
    ;

    private String stirngRepr;
    AnnouncementTarget(String stringRepr) {
        this.stirngRepr = stringRepr;
    }

    public static AnnouncementTarget get(String stringRepr) {
        return AnnouncementTarget.valueOf(stringRepr
                .toLowerCase(Locale.ROOT)
                .replace('_', ' ')
        );
    }
}
