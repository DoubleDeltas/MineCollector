package com.doubledeltas.minecollector.yaml;

import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public class ParsingSerializer<T> implements YamlSerializer<T> {
    private final Function<String, T> parser;

    @Override
    public String serialize(T object) {
        return object.toString();
    }

    @Override
    public T deserialize(String string) {
        return parser.apply(string);
    }
}
