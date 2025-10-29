package com.doubledeltas.minecollector.version;

import java.util.*;

public class VersionUpdaterChain<T> {
    protected final VersionManager versionManager;
    protected LinkedList<Version<?>> registeredVersions;
    protected Map<Version<?>, VersionUpdater<? extends T, ? extends T>> updaterMap;

    public VersionUpdaterChain(VersionManager versionManager, Version<?> initialVersion) {
        this.versionManager = versionManager;
        this.registeredVersions = new LinkedList<>();
        this.updaterMap = new HashMap<>();

        chain(initialVersion, null);
    }

    public VersionUpdaterChain<T> chain(Version<?> targetVersion, VersionUpdater<? extends T, ? extends T> updater) {
        assertValidVersion(targetVersion);
        if (!registeredVersions.isEmpty()) {
            Version<?> latestVersion = registeredVersions.getLast();
            if (versionManager.compareVersions(latestVersion, targetVersion) >= 0)
                throw new IllegalArgumentException("target version(%s) must be later than current latest version(%s)".formatted(targetVersion, latestVersion));
        }
        registeredVersions.push(targetVersion);
        if (updater != null)
            updaterMap.put(targetVersion, updater);
        return this;
    }

    @SuppressWarnings("unchecked")
    public T update(T obj, Version<?> currentVersion, Version<?> targetMinVersion) {
        assertValidVersion(currentVersion);
        assertValidVersion(targetMinVersion);

        T cur = obj;
        for (Version<?> ver : registeredVersions) {
            if (versionManager.compareVersions(ver, currentVersion) < 0)
                continue;   // skip until currentVersion
            VersionUpdater<T, T> updater = (VersionUpdater<T, T>) updaterMap.get(ver);
            cur = updater.update(cur);
            if (versionManager.compareVersions(ver, targetMinVersion) >= 0)
                break;
        }
        return cur;
    }

    public T updateToLatest(T obj, Version<?> currentVersion) {
        return update(obj, currentVersion, registeredVersions.getLast());
    }

    private void assertValidVersion(Version<?> version) {
        if (!versionManager.isRecognizable(version))
            throw new IllegalArgumentException(version + " must be recognizable to the version manager");
    }
}
