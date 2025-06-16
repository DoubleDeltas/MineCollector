package com.doubledeltas.minecollector.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public final class TimeUtil {
    static final long NANOS_PER_SECOND = 1_000_000_000L;

    static final long TICKS_PER_SECOND = 20L;
    static final long NANOS_PER_TICK = NANOS_PER_SECOND / TICKS_PER_SECOND;

    /**
     * Gets the number of MC ticks in the duration. (by 20 ticks = 1 second)
     *
     * @param duration duration to get the number of ticks.
     * @return the number of MC ticks in the duration. may be negative.
     */
    public static long toTicks(Duration duration) {
        long tempSeconds = duration.getSeconds();
        long tempNanos = duration.getNano();
        if (tempSeconds < 0) {
            tempSeconds = tempSeconds + 1;
            tempNanos = tempNanos - NANOS_PER_SECOND;
        }
        long ticks = Math.multiplyExact(tempSeconds, TICKS_PER_SECOND);
        ticks = Math.addExact(ticks, tempNanos / NANOS_PER_TICK);
        return ticks;
    }
}
