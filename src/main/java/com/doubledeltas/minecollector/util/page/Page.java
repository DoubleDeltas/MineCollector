package com.doubledeltas.minecollector.util.page;

/**
 * unmodifiable sublist of a list of objects, partitioned with constant size.
 * This provides metadata about the location in the original list.
 * @param <E> data type of element
 */
public interface Page<E> extends Iterable<E> {
    E get(int index);

    int getSize();

    int getCapacity();

    boolean isEmpty();

    boolean isFirst();

    boolean isLast();

    int getCurrentPage();

    int getTotalPages();

    long getTotalSize();

    static <T> PageCollector<T, ? extends Page<T>> collector(int capacity, int page) {
        return new PageCollector<>(capacity, page, new ArrayPage.Builder<>());
    }

    interface Builder<E, P extends Page<E>> {
        void add(E element);
        P build(int totalSize, int start, int end);
    }
}
