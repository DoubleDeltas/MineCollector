package com.doubledeltas.minecollector.lang;


public interface MessageKey {
    String getFullKey();
    int getVariableCount();

    static MessageKey of(String fullKey, int variableCount) {
        return new LiteralMessageKey(fullKey, variableCount);
    }

    static MessageKey of(String fullKey) {
        return of(fullKey, 0);
    }
}
