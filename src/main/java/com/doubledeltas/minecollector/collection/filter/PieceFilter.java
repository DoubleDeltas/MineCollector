package com.doubledeltas.minecollector.collection.filter;

import com.doubledeltas.minecollector.collection.Piece;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Piece를 필터링 하는 기준
 * @param <V> 필터에 적용될 수 있는 상태의 타입
 */
@FunctionalInterface
public interface PieceFilter<V> {
    Predicate<Piece> toPredicate(PieceFilterContext context, V stateValue);
}
