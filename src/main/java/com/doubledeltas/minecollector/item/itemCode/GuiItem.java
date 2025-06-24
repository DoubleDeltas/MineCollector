package com.doubledeltas.minecollector.item.itemCode;

public enum GuiItem implements ItemCode {
    BLACK("black"),
    GRAY("gray"),
    NO_PREV("noPrev"),
    PREV("prev"),
    NO_NEXT("noNext"),
    NEXT("next"),
    BACK("back"),
    COLLECTION("collection"),
    DUMP("dump"),
    RANKING("ranking"),
    UNKNOWN("unknown"),
    OK("ok"),
    HMM("hmm"),
    NO("no"),
    AIR_PLACEHOLDER("air_placeholder"),
    UNKNOWN_AIR_PLACEHOLDER("unknown_air_placeholder"),
    CORE("core"),
    ;

    private final String subpathName;

    GuiItem(String subpathName) {
        this.subpathName = subpathName;
    }

    @Override
    public String getPathName() {
        return "gui/" + subpathName;
    }
}
