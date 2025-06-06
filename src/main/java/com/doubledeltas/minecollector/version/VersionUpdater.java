package com.doubledeltas.minecollector.version;

/**
 * 이전 버전의 스키마에서 다음 버전의 스키마로 업데이트합니다.
 * @param <S> 이전 버전의 타입 (source)
 * @param <D> 목표 버전의 타입 (destination)
 */
public interface VersionUpdater<S, D> {
    D update(S source);
}
