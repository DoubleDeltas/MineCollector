package com.doubledeltas.minecollector.util;

import java.time.Duration;
import java.util.Set;

/**
 * A {@link Set} implementation that automatically expires elements after a specified
 * time-to-live (TTL).
 * <p>
 * A {@code TimedSet} behaves like a regular set but associates a TTL with each
 * inserted element. Once an element's TTL has elapsed, the element is considered
 * expired and should no longer be treated as a member of this set. Implementations
 * may remove expired elements lazily (e.g., on access or mutation) or proactively
 * through background cleanup mechanisms.
 *
 * @param <T> the type of elements maintained by this set
 */
public interface TimedSet<T> extends Set<T> {
    static <T> TimedSet<T> of(Duration ttl) {
        return new GuavaCacheTimedSet<>(ttl);
    }
}
