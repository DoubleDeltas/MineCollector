package com.doubledeltas.minecollector.util.page;

import lombok.Getter;

public abstract class AbstractPage<E> implements Page<E> {
    /**
     * Current page number in 1-index.
     */
    protected int page;
    /**
     * total size of the original source. Used to calculate last page.
     */
    @Getter
    protected long totalSize;
    /**
     * Start index, inclusive.
     */
    protected int start;
    /**
     * End index, exclusive.
     * This can be greater than {@code totalSize} when the page is last
     * and the number of rest elements is less than the size of the page.
     */
    protected int end;
    /**
     * The number of elements the page actually containing. must be less than or equal to {@code capacity}.
     */
    @Getter
    protected int size;
    /**
     * The maximum number that the page can contain elements
     */
    @Getter
    protected int capacity;

    protected AbstractPage(long totalSize, int start, int end) {
        if (start < 0)
            throw new IllegalArgumentException("Page start must be 0 or greater");
        if (start > end)
            throw new IllegalArgumentException("Page start must be prior to page end");
        this.totalSize = totalSize;
        this.start = start;
        this.end = end;
        this.capacity = end - start;
        this.page = (end - 1) / capacity + 1;
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public boolean isFirst() {
        return page == 1;
    }

    @Override
    public boolean isLast() {
        return page == getTotalPages();
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    @Override
    public int getTotalPages() {
        // = ceil(totalSize / size)
        return (int) ((totalSize + (size - 1)) / capacity);
    }
}
