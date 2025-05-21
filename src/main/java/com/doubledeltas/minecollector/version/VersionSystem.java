package com.doubledeltas.minecollector.version;

import com.doubledeltas.minecollector.util.Parser;
import lombok.Getter;

import java.util.Comparator;

@Getter
public class VersionSystem {
    public static final VersionSystem UNLABELED = new VersionSystem(0, new UnlabeledVersion.Parser());
    public static final VersionSystem SEMANTIC = new VersionSystem(1, new SemanticVersion.Parser());

    private final int order;
    private final Comparator<? extends Version<?>> comparator;
    private final Parser<? extends Version<?>> parser;

    @SuppressWarnings("unchecked")
    public <V extends Version<V>> VersionSystem(int order, Parser<V> parser) {
        this.order = order;
        this.comparator = (Comparator<V>) Comparator.naturalOrder();
        this.parser = parser;
    }
}
