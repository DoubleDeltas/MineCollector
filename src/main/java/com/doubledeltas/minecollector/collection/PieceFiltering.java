package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.data.GameData;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

public class PieceFiltering {
    public static final PieceFiltering DEFAULT = new PieceFiltering(Map.of());

    private final Map<PieceFilter, Boolean> filterMap = new EnumMap<>(PieceFilter.class);

    public PieceFiltering(Map<PieceFilter, Boolean> filterMap) {
        this.filterMap.putAll(filterMap);
    }

    public Predicate<Piece> generate(CollectionManager collectionManager, GameData data) {
        return filterMap.entrySet().stream()
                .map(entry -> {
                    Predicate<Piece> predicate = entry.getKey().getGenerator().apply(collectionManager, data);
                    return entry.getValue() ? predicate.negate() : predicate;
                })
                .reduce(piece -> true, Predicate::and);
    }
}
