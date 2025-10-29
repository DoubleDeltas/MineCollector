package com.doubledeltas.minecollector.version;

public interface CurrentSchema<T> extends Schema<T> {
    T convert();
}
