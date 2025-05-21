package com.doubledeltas.minecollector.yaml;

/**
 * 어떤 타입으로 변환할 수 있는 YAML 스키마
 * @param <T> 변환할 목표 타입
 * @param <X> 변환 실패 시 반환할 예외 타입
 */
public interface YamlSchema<T, X extends Throwable> {
    T convert() throws X;
}
