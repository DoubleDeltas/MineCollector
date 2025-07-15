package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.data.GameData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class PieceFilter {
    public static final PieceFilter ALL = new PieceFilter((cm, data) -> piece -> true);
    public static final PieceFilter COLLECTED = new PieceFilter((cm, data) -> piece -> piece.isCollected(data));

    @Getter(AccessLevel.PACKAGE)
    private final BiFunction<CollectionManager, GameData, Predicate<Piece>> generator;

    public static PieceFilter minLevel(int minLevel) {
        return new PieceFilter((cm, data) -> piece -> piece.getLevel(data) >= minLevel);
    }
}
