package com.doubledeltas.minecollector.util.page;

import javax.annotation.Nonnull;
import java.util.*;

public class ArrayPage<E> extends AbstractPage<E> implements Page<E>, RandomAccess {
    private final Object[] elements;

    public ArrayPage(int totalSize, int start, int end, @Nonnull E[] subarray) {
        super(totalSize, start, end);
        this.elements = new Object[capacity];
        this.size = Math.min(subarray.length, capacity);
        System.arraycopy(subarray, 0, elements, 0, size);
    }

    public ArrayPage(int totalSize, int start, int end, @Nonnull List<? extends E> sublist) {
        super(totalSize, start, end);
        this.elements = sublist.toArray();
        this.size = Math.min(sublist.size(), capacity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) elements[index];
    }

    @Override
    public @Nonnull Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;

        Itr() {
            this.cursor = start;
        }

        @Override
        public boolean hasNext() {
            return cursor < Math.min(end, totalSize);
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException("Page ended");
            return (E) elements[cursor];
        }
    }

    static class Builder<E> implements Page.Builder<E, ArrayPage<E>> {
        private final List<E> sublist = new ArrayList<>();

        @Override
        public void add(E element) {
            sublist.add(element);
        }

        @Override
        public ArrayPage<E> build(int totalSize, int start, int end) {
            return new ArrayPage<>(totalSize, start, end, sublist);
        }
    }
}
