package com.doubledeltas.minecollector.version;

public interface Schema<T> {
    void validate() throws SchemaLoadingException;
}
