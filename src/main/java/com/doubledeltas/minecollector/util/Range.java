package com.doubledeltas.minecollector.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents closed range.
 * @param min minimum of this range
 * @param max maximum of this range
 * @param <V> type of range value, must be {@link Comparable}.
 */
public record Range<V extends Comparable<V>>(@Nonnull V min, @Nonnull V max) {
    public boolean isValid() {
        return min.compareTo(max) <= 0;
    }

    public boolean contains(V value) {
        if (value == null) return false;
        return min.compareTo(value) <= 0 && value.compareTo(max) <= 0;
    }
}
