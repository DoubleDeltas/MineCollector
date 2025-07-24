package com.doubledeltas.minecollector.collection.filter;

import com.doubledeltas.minecollector.collection.Piece;
import com.doubledeltas.minecollector.util.Trilean;
import com.google.common.base.Predicates;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@Getter @AllArgsConstructor
public abstract class TrileanFilter implements PieceFilter<Trilean> {
    @Override
    public Predicate<Piece> toPredicate(PieceFilterContext context, Trilean stateValue) {
        return switch (stateValue) {
            case TRUE -> getTruePredicate(context);
            case FALSE -> getTruePredicate(context).negate();
            case UNKNOWN -> Predicates.alwaysTrue();
        };
    }

    public abstract Predicate<Piece> getTruePredicate(PieceFilterContext context);
}
