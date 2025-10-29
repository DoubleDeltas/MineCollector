package com.doubledeltas.minecollector.util;

import lombok.AllArgsConstructor;

/**
 * Single object holder
 * @param <T> type of the object to hold
 */
@AllArgsConstructor
public class Holder<T> {
    private T content;

    public T get() {
        return content;
    }

    public void set(T content) {
        this.content = content;
    }
}
