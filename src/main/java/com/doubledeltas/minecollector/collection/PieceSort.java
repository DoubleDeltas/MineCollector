package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.data.GameData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public enum PieceSort {
    VANILLA     ((cm, data) -> getMojangComparator()),
    ALPHABETICAL((cm, data) -> Comparator.comparing(Piece::toPieceKey)),
    AMOUNT      ((cm, data) -> Comparator.comparingInt(piece -> piece.getAmount(data))),
    ;

    @Getter(AccessLevel.PACKAGE)
    private final @Nonnull BiFunction<CollectionManager, GameData, Comparator<Piece>> generator;

    private static Comparator<Piece> MOJANG_COMPARATOR;

    private static Comparator<Piece> getMojangComparator() {
        if (MOJANG_COMPARATOR != null)
            return MOJANG_COMPARATOR;

        ItemMojangOrderProvider provider = new ItemMojangOrderProvider();
        provider.load();
        return MOJANG_COMPARATOR = (p1, p2) -> {
            boolean plain1 = p1 instanceof PlainItemPiece;
            boolean plain2 = p2 instanceof PlainItemPiece;
            if ( plain1 && !plain2) return -1;
            if (!plain1 &&  plain2) return 1;
            if (plain1) {
                Material m1 = ((PlainItemPiece) p1).getMaterial();
                Material m2 = ((PlainItemPiece) p2).getMaterial();
                return Integer.compare(provider.getOrder(m1), provider.getOrder(m2));
            }
            return p1.toPieceKey().compareTo(p2.toPieceKey());
        };
    }
}
