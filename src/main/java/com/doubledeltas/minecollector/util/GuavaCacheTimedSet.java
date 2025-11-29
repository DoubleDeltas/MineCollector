package com.doubledeltas.minecollector.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.util.*;

/**
 * A {@link TimedSet} implementation backed by a Guava {@link com.google.common.cache.Cache}.
 * <p>
 * Each inserted element is stored with a time-to-live (TTL) defined at construction time.
 * Once the TTL has elapsed, the element is considered expired and is no longer visible to
 * set operations such as {@link #contains(Object)}, {@link #iterator()}, or {@link #size()}.
 * Expired elements may remain internally until Guava's cleanup process runs; therefore,
 * most operations trigger {@code cleanUp()} to ensure that stale entries are removed
 * before computing results.
 * <p>
 * This class behaves like a standard {@link Set} in terms of contract and semantics, but
 * automatically evicts elements after their TTL. Iteration and bulk operations
 * (e.g., {@link #retainAll(Collection)}, {@link #removeAll(Collection)}) operate on a
 * consistent snapshot of currently unexpired elements.
 *
 * <h2>Characteristics</h2>
 * <ul>
 *   <li>Elements expire after the specified TTL (expire-after-write policy).</li>
 *   <li>Iteration reflects only non-expired entries at the time of snapshot creation.</li>
 *   <li>Expired entries are removed lazily through Guava's cleanup mechanism.</li>
 *   <li>Null elements are not supported, following Guava Cache restrictions.</li>
 * </ul>
 *
 * @param <T> the type of elements maintained by this set
 */
public class GuavaCacheTimedSet<T> implements TimedSet<T> {
    private final Cache<T, Object> guavaCache;

    private static final Object PRESENT = new Object();

    public GuavaCacheTimedSet(Duration ttl) {
        guavaCache = CacheBuilder.newBuilder()
                .expireAfterWrite(ttl)
                .build();
    }

    private Set<T> snapshot() {
        guavaCache.cleanUp();
        return guavaCache.asMap().keySet();
    }

    @Override
    public int size() {
        guavaCache.cleanUp();
        return (int) guavaCache.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return guavaCache.getIfPresent(o) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return snapshot().iterator();
    }

    @Override
    public Object[] toArray() {
        return snapshot().toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return snapshot().toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (contains(t))
            return false;
        guavaCache.put(t, PRESENT);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        boolean existed = contains(o);
        guavaCache.invalidate(o);
        return existed;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c);
        guavaCache.cleanUp();
        for (Object e : c) {
            if (!contains(e))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        for (T e : c) {
            if (add(e))
                modified = true;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        for (T element : snapshot()) {
            if (!c.contains(element)) {
                guavaCache.invalidate(element);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        for (Object e : c) {
            if (remove(e))
                modified = true;
        }
        return modified;
    }

    @Override
    public void clear() {
        guavaCache.invalidateAll();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Set<?> other)) {
            return false;
        }

        Set<T> elements = snapshot();

        if (other.size() != elements.size()) {
            return false;
        }

        return other.containsAll(elements);
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (T element : snapshot()) {
            h += element.hashCode();
        }
        return h;
    }

    @Override
    public String toString() {
        return snapshot().toString();
    }
}
