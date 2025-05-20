package com.doubledeltas.minecollector.yaml;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingletonSerializer<T> implements YamlSerializer<T> {
    private final T singleton;
    private final String yamlString;

    @Override
    public String serialize(T object) {
        return yamlString;
    }

    @Override
    public T deserialize(String string) {
        return singleton;
    }
}
