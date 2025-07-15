package com.doubledeltas.minecollector.util.page;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PageCollector<E, P extends Page<E>> implements Collector<E, Page.Builder<E, P>, P> {
    private final Page.Builder<E, P> builder;
    private int count = 0;

    private final int start;
    private final int end;

    public PageCollector(PageRange range, Page.Builder<E, P> builder) {
        int cap = range.capacity();
        int page = range.page();
        this.builder = builder;
        this.start = cap * (page - 1);
        this.end = start + cap;
    }

    @Override
    public Supplier<Page.Builder<E, P>> supplier() {
        return () -> builder;
    }

    @Override
    public BiConsumer<Page.Builder<E, P>, E> accumulator() {
        BiConsumer<Page.Builder<E, P>, E> accumulate = (pb, element) -> {
            if (start <= count && count < end)
                pb.add(element);
        };
        count++;
        return accumulate;
    }

    @Override
    public BinaryOperator<Page.Builder<E, P>> combiner() {
        throw new UnsupportedOperationException("parallel streams not supported");
    }

    @Override
    public Function<Page.Builder<E, P>, P> finisher() {
        return builder -> builder.build(count, start, end);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
