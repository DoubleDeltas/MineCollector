package com.doubledeltas.minecollector.collection.filter;

import com.doubledeltas.minecollector.collection.Piece;
import com.doubledeltas.minecollector.util.Range;
import lombok.experimental.UtilityClass;

import java.util.function.Predicate;

@UtilityClass
public final class PieceFilters {
    public static final TrileanFilter COLLECTED = new TrileanFilter() {
        @Override
        public Predicate<Piece> getTruePredicate(PieceFilterContext context) {
            return piece -> piece.isCollected(context.data());
        }
    };

    public static final PieceFilter<Range<Integer>> LEVEL = (context, stateValue) -> (piece) -> {
        int level = piece.getLevel(context.data());
        return stateValue.min() <= level && level <= stateValue.max();
    };
}
