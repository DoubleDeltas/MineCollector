package com.doubledeltas.minecollector.item.itemCode;

public enum StaticItem implements ItemCode {
    COLLECTION_BOOK("collection_book"),
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
