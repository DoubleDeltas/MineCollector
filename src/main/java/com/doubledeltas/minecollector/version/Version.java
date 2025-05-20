package com.doubledeltas.minecollector.version;

import java.util.Comparator;

public interface Version<V extends Version<V>> extends Comparable<V> {
    VersionSystem getVersionSystem();

    @SuppressWarnings("unchecked")
    static int compare(Version<?> v1, Version<?> v2) {
        VersionSystem v1System = v1.getVersionSystem();
        VersionSystem v2System = v2.getVersionSystem();
        if (!v1System.equals(v2System))
            return v1System.compareTo(v2System);

        return ((Comparator<Version<?>>) v1System.getComparator()).compare(v1, v2);
    }

    /**
     * <p>Compare versions complying with the same version system.</p>
     * <p>c.f. To compare versions using different systems, Use {@link Version#compare Version.compare()} instead.</p>
     * @param other the object to be compared.
     * @return
     * @see Version#compare(Version, Version)
     */
    int compareTo(V other);
}
