package com.doubledeltas.minecollector.item.itemCode;

public enum StaticItem implements ItemCode {
    COLLECT_BOOK("collect_book"),
    ;

    private final String pathName;

    StaticItem(String pathName) {
        this.pathName = pathName;
    }

    @Override
    public String getPathName() {
        return this.pathName;
    }
}
