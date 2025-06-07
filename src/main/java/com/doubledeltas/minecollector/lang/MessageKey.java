package com.doubledeltas.minecollector.lang;


public interface MessageKey {
    String getFullKey();
    String[] getPlaceholders();

    static MessageKey of(String fullKey, String... placeholders) {
        return new LiteralMessageKey(fullKey, placeholders);
    }
}
