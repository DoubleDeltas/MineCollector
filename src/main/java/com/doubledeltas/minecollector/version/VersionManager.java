package com.doubledeltas.minecollector.version;

import com.doubledeltas.minecollector.util.Parser;

import java.util.*;

public class VersionManager {
    private final NavigableSet<VersionSystem> vsRegistry = new TreeSet<>(Comparator.comparingInt(VersionSystem::getOrder));

    public void register(VersionSystem versionSystem) {
        vsRegistry.add(versionSystem);
    }

    public int compareVersions(Version<?> v1, Version<?> v2) {
        VersionSystem v1System = v1.getVersionSystem();
        VersionSystem v2System = v2.getVersionSystem();
        if (!vsRegistry.contains(v1System))
            throw new IllegalArgumentException("The system of version" + v1 + "is not registered in the manager.");
        if (!vsRegistry.contains(v2System))
            throw new IllegalArgumentException("The system of version" + v2 + "is not registered in the manager.");
        return Integer.compare(v1System.getOrder(), v2System.getOrder());
    }

    public Version<?> parse(String string) {
        for (Iterator<VersionSystem> it = vsRegistry.descendingIterator(); it.hasNext(); ) {
            VersionSystem vs = it.next();
            Parser<? extends Version<?>> parser = vs.getParser();
            if (parser.canParse(string))
                return parser.parse(string);
        }
        return null;
    }

    public Collection<VersionSystem> getVersionSystemRegistry() {
        return vsRegistry;
    }
}
