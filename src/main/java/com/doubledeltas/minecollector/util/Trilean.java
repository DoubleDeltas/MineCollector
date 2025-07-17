package com.doubledeltas.minecollector.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>Represents any balanced 3-valued logic(3VL) type.</p>
 * This does not imply any binary 3VL operation. It only implies {@link Trilean#not not()}.
 */
@RequiredArgsConstructor @Getter
public enum Trilean {
    FALSE(-1),
    UNKNOWN(0),
    TRUE(1),
    ;

    private final int signum;

    /**
     * Convert primitive {@code int} to {@code Trilean} with its signum.
     * <ul>
     *     <li>If the value > 0, returns {@link Trilean#TRUE}</li>
     *     <li>If the value < 0, returns {@link Trilean#FALSE}</li>
     *     <li>If the value = 0, returns {@link Trilean#UNKNOWN}</li>
     * </ul>
     * @param i primitive {@code int} variable to convert
     * @return converted Trilean value
     */
    public static Trilean of(int i) {
        return i == 0 ? UNKNOWN : i > 0 ? TRUE : FALSE;
    }

    /**
     * Convert wrapper {@link Integer} to {@code Trilean} with its signum.
     * <ul>
     *     <li>If the value > 0, returns {@link Trilean#TRUE}</li>
     *     <li>If the value < 0, returns {@link Trilean#FALSE}</li>
     *     <li>If the value = 0 or {@code null}, returns {@link Trilean#UNKNOWN}</li>
     * </ul>
     * @param i wrapper {@link Integer} variable to convert
     * @return converted Trilean value
     */
    public static Trilean of(Integer i) {
        if (i == null) return UNKNOWN;
        int val = i;
        return val == 0 ? UNKNOWN : val > 0 ? TRUE : FALSE;
    }

    /**
     * Convert primitive {@code long} to {@code Trilean} with its signum.
     * <ul>
     *     <li>If the value > 0, returns {@link Trilean#TRUE}</li>
     *     <li>If the value < 0, returns {@link Trilean#FALSE}</li>
     *     <li>If the value = 0, returns {@link Trilean#UNKNOWN}</li>
     * </ul>
     * @param i primitive {@code long} variable to convert
     * @return converted Trilean value
     */
    public static Trilean of(long i) {
        return i == 0 ? UNKNOWN : i > 0 ? TRUE : FALSE;
    }

    /**
     * Convert wrapper {@link Long} to {@code Trilean} with its signum.
     * <ul>
     *     <li>If the value > 0, returns {@link Trilean#TRUE}</li>
     *     <li>If the value < 0, returns {@link Trilean#FALSE}</li>
     *     <li>If the value = 0 or {@code null}, returns {@link Trilean#UNKNOWN}</li>
     * </ul>
     * @param i wrapper {@link Long} variable to convert
     * @return converted Trilean value
     */
    public static Trilean of(Long i) {
        if (i == null) return UNKNOWN;
        long val = i;
        return val == 0 ? UNKNOWN : val > 0 ? TRUE : FALSE;
    }

    /**
     * Convert primitive {@code boolean} to {@link Trilean}
     * <ul>
     *     <li>If the value = {@code true}, returns {@link Trilean#TRUE}</li>
     *     <li>If the value = {@code false}, returns {@link Trilean#FALSE}</li>
     * </ul>
     * @param b primitive {@code boolean} variable to convert
     * @return converted Trilean value
     */
    public static Trilean of(boolean b) {
        return b ? TRUE : FALSE;
    }

    /**
     * Convert wrapper {@link Boolean} to {@link Trilean}
     * <ul>
     *     <li>If the value = {@code true}, returns {@link Trilean#TRUE}</li>
     *     <li>If the value = {@code false}, returns {@link Trilean#FALSE}</li>
     *     <li>If the value is {@code null}, returns {@link Trilean#UNKNOWN}</li>
     * </ul>
     * @param b wrapper {@link Boolean} variable to convert
     * @return converted Trilean value
     */
    public static Trilean of(Boolean b) {
        return b == null ? UNKNOWN : b ? TRUE : FALSE;
    }

    /**
     * <p>Determine nullable {@link Trilean} variable to non-null constant.</p>
     * If the value is {@code null}, returns {@link Trilean#UNKNOWN}. otherwise, returns itself.
     * @param t {@link Trilean} to determine
     * @return determined Trilean value
     */
    public static Trilean of(Trilean t) {
        return t == null ? UNKNOWN : t;
    }

    public Trilean not() {
        return switch (this) {
            case UNKNOWN -> UNKNOWN;
            case TRUE -> FALSE;
            case FALSE -> TRUE;
        };
    }
}
