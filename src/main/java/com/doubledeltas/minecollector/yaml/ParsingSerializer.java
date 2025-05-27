package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.util.Parser;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public class ParsingSerializer<T> implements YamlSerializer<T> {
    private final Parser<T> parser;

    @Override
    public String serialize(T object) {
        return object.toString();
    }

    @Override
    public T deserialize(String string) {
        return parser.parse(string);
    }
}
