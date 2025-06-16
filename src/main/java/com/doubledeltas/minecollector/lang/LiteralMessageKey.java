package com.doubledeltas.minecollector.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class LiteralMessageKey implements MessageKey {
    private final String fullKey;
    private final int variableCount;
}
