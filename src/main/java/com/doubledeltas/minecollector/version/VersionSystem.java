package com.doubledeltas.minecollector.version;

import lombok.Getter;

import java.util.Comparator;

@Getter
public class VersionSystem implements Comparable<VersionSystem> {
    public static final VersionSystem UNLABELED = new VersionSystem(0, UnlabeledVersion.class);
    public static final VersionSystem SEMANTIC = new VersionSystem(1, SemanticVersion.class);

    private final int order;
    private final Class<? extends Version<?>> implType;
    private final Comparator<? extends Version<?>> comparator;

    @SuppressWarnings("unchecked")
    public VersionSystem(int order, Class<? extends Version<?>> implType) {
        this.order = order;
        this.implType = implType;
        this.comparator = (Comparator<? extends Version<?>>) Comparator.naturalOrder();
    }

    @Override
    public int compareTo(VersionSystem o) {
        return Integer.compare(this.order, o.order);
    }
}
