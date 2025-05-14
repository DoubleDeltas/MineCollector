package com.doubledeltas.minecollector.version;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.regex.Pattern;

@AllArgsConstructor
public class SemanticVersion implements Version<SemanticVersion> {
    private int major;
    private int minor;
    private int patch;

    public static SemanticVersion parse(String str) {
        final Pattern PATTERN = Pattern.compile("^\\d*\\.\\d*\\.\\d*$");

        if (str == null)
            return null;

        if (!PATTERN.matcher(str).matches())
            throw new IllegalArgumentException("String to parse must be 3 digit-words separated with period ('a.b.c').");

        String[] words = str.split("\\.");
        int major = Integer.parseInt(words[0]);
        int minor = Integer.parseInt(words[1]);
        int patch = Integer.parseInt(words[2]);

        return new SemanticVersion(major, minor, patch);
    }

    @Override
    public int compareTo(@NonNull SemanticVersion other) {
        if (this.major != other.major) return Integer.compare(this.major, other.major);
        if (this.minor != other.minor) return Integer.compare(this.minor, other.minor);
        if (this.patch != other.patch) return Integer.compare(this.patch, other.patch);
        return 0;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}
