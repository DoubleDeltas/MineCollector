package com.doubledeltas.minecollector.collection.filter;

import com.doubledeltas.minecollector.collection.Piece;

import java.util.function.Predicate;

public record PieceFilterAndValue<V>(PieceFilter<V> filter, V value) {
    public Predicate<Piece> toPredicate(PieceFilterContext context) {
        return filter.toPredicate(context, value);
    }
}
