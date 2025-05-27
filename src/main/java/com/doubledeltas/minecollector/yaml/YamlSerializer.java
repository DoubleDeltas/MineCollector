package com.doubledeltas.minecollector.yaml;

/**
 * YAML 스칼라 노드로의 직렬화/역직렬화 클래스
 */
public interface YamlSerializer<T> {
    String serialize(T object);
    T deserialize(String string);
    default T defaultValue() {
        return null;
    }
}
