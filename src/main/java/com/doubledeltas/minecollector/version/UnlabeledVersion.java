package com.doubledeltas.minecollector.version;

/**
 * v1.3 미만 버전에서 config version (unlabeled)
 */
public enum UnlabeledVersion implements Version<UnlabeledVersion> {
    INSTANCE;

    @Override
    public String toString() {
        return "unlabeled";
    }

    @Override
    public VersionSystem getVersionSystem() {
        return VersionSystem.UNLABELED;
    }
}
