package com.doubledeltas.minecollector.util;

public interface Parser<T> {
    T parse(String string);

    default boolean canParse(String string) {
        try {
            parse(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
