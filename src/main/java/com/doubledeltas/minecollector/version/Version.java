package com.doubledeltas.minecollector.version;

import com.doubledeltas.minecollector.MineCollector;

public interface Version<V extends Version<V>> extends Comparable<V> {
    VersionSystem getVersionSystem();

    static int compare(Version<?> v1, Version<?> v2) {
        return MineCollector.getInstance().getVersionManager().compareVersions(v1, v2);
    }

    static Version<?> parse(String str) {
        return Parser.INSTANCE.parse(str);
    }

    /**
     * <p>Compare versions complying with the same version system.</p>
     * <p>c.f. To compare versions using different systems, Use {@link Version#compare Version.compare()} instead.</p>
     * @param other the object to be compared.
     * @return
     * @see Version#compare(Version, Version)
     */
    int compareTo(V other);

    enum Parser implements com.doubledeltas.minecollector.util.Parser<Version<?>> {
        INSTANCE;

        @Override
        public Version<?> parse(String string) {
            return MineCollector.getInstance().getVersionManager().parse(string);
        }
    }
}
