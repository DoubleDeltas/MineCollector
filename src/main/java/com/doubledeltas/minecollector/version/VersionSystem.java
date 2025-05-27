package com.doubledeltas.minecollector.version;

import com.doubledeltas.minecollector.util.Parser;
import lombok.Getter;

import java.util.Comparator;

@Getter
public class VersionSystem {
    public static final VersionSystem UNLABELED
            = new VersionSystem(0, UnlabeledVersion.class, UnlabeledVersion.Parser.INSTANCE);
    public static final VersionSystem SEMANTIC
            = new VersionSystem(1, SemanticVersion.class, SemanticVersion.Parser.INSTANCE);

    private final int order;
    private final Class<? extends Version<?>> versionClass;
    private final Comparator<? extends Version<?>> comparator;
    private final Parser<? extends Version<?>> parser;

    @SuppressWarnings("unchecked")
    public <V extends Version<V>> VersionSystem(int order, Class<V> versionClass, Parser<V> parser) {
        this.order = order;
        this.versionClass = versionClass;
        this.comparator = (Comparator<V>) Comparator.naturalOrder();
        this.parser = parser;
    }
}
