package com.doubledeltas.minecollector.version;

import com.doubledeltas.minecollector.util.Parser;

import java.text.ParseException;

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

    public static class Parser implements com.doubledeltas.minecollector.util.Parser<UnlabeledVersion> {

        @Override
        public boolean canParse(String string) {
            return string == null || "null".equals(string) || string.isBlank();
        }

        @Override
        public UnlabeledVersion parse(String string) {
            return UnlabeledVersion.INSTANCE;
        }
    }
}
