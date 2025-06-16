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

    public enum Parser implements com.doubledeltas.minecollector.util.Parser<UnlabeledVersion> {
        INSTANCE;

        @Override
        public boolean canParse(String string) {
            return string == null || "null".equals(string) || string.isBlank()
                    || "unlabeled".equalsIgnoreCase(string);
        }

        @Override
        public UnlabeledVersion parse(String string) {
            return UnlabeledVersion.INSTANCE;
        }
    }
}
