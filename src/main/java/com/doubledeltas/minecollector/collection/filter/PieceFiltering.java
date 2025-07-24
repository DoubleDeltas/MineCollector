package com.doubledeltas.minecollector.collection.filter;

import com.doubledeltas.minecollector.collection.Piece;
import com.google.common.base.Predicates;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class PieceFiltering {
    public static final PieceFiltering PASS_ALL = new PieceFiltering();

    private final Set<PieceFilterAndValue<?>> filterValueSet = new HashSet<>();

    public PieceFiltering() {}

    public <V> void addFilter(PieceFilter<V> filter, V value) {
        filterValueSet.add(new PieceFilterAndValue<>(filter, value));
    }

    public Predicate<Piece> toPredicate(PieceFilterContext context) {
        return filterValueSet.stream()
                .map(filterAndValue -> filterAndValue.toPredicate(context))
                .reduce(Predicates.alwaysTrue(), Predicate::and);
    }
}
