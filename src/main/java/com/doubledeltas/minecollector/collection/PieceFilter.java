package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.data.GameData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Predicate;

@RequiredArgsConstructor
public enum PieceFilter {
    ALL((cm, data) -> piece -> true),
    COLLECTED((cm, data) -> piece -> piece.isCollected(data)),
    ;

    @Getter(AccessLevel.PACKAGE)
    private final BiFunction<CollectionManager, GameData, Predicate<Piece>> generator;
}
