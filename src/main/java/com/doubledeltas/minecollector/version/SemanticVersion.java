package com.doubledeltas.minecollector.version;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.regex.Pattern;

@AllArgsConstructor @EqualsAndHashCode
public final class SemanticVersion implements Version<SemanticVersion> {
    private static final Pattern PATTERN = Pattern.compile("^\\d+(?:\\.\\d+){0,2}$");

    private byte major;
    private byte minor;
    private byte patch;

    public SemanticVersion(int major, int minor, int patch) {
        if (major < 0 || major > Byte.MAX_VALUE || minor < 0 || minor > Byte.MAX_VALUE || patch < 0 || patch > Byte.MAX_VALUE)
            throw new IllegalArgumentException("major, minor, patch must between 0~127");

        this.major = (byte) major;
        this.minor = (byte) minor;
        this.patch = (byte) patch;
    }

    public static SemanticVersion parse(String str) {
        if (str == null)
            return null;

        if (!PATTERN.matcher(str).matches())
            throw new IllegalArgumentException("Invalid semantic version string (" + str + "). Must be 'x', 'x.y' or 'x.y.z'");

        String[] words = str.split("\\.");
        byte major = Byte.parseByte(words[0]);
        byte minor = words.length >= 2 ? Byte.parseByte(words[1]) : 0;
        byte patch = words.length == 3 ? Byte.parseByte(words[2]) : 0;

        return new SemanticVersion(major, minor, patch);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

    @Override
    public int compareTo(SemanticVersion other) {
        if (this.major != other.major) return Byte.compare(this.major, other.major);
        if (this.minor != other.minor) return Byte.compare(this.minor, other.minor);
        if (this.patch != other.patch) return Byte.compare(this.patch, other.patch);
        return 0;
    }

    @Override
    public VersionSystem getVersionSystem() {
        return VersionSystem.SEMANTIC;
    }
}
