package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.version.SemanticVersion;
import com.doubledeltas.minecollector.version.UnlabeledVersion;
import com.doubledeltas.minecollector.version.Version;

public class VersionSerializer implements YamlSerializer<Version<?>> {
    @Override
    public String serialize(Version<?> object) {
        return object.toString();
    }

    @Override
    public Version<?> deserialize(String string) {
        if (string == null || "null".equals(string) || string.isEmpty())
            return UnlabeledVersion.INSTANCE;
        else
            return SemanticVersion.parse(string);
    }

    @Override
    public UnlabeledVersion defaultValue() {
        return UnlabeledVersion.INSTANCE;
    }
}
