package com.doubledeltas.minecollector.version;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Semantic version.</p>
 * @since 1.3
 * @see <a href="https://semver.org/lang/ko/">유의적 버전 2.0</a>
 */
@AllArgsConstructor @EqualsAndHashCode
public final class SemanticVersion implements Version<SemanticVersion> {
    private static final Pattern PATTERN = Pattern.compile("^(?<major>0|[1-9]\\d*)\\.(?<minor>0|[1-9]\\d*)\\.(?<patch>0|[1-9]\\d*)(?:-(?<prerelease>(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+(?<buildmetadata>[0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$");

    private int major;
    private int minor;
    private int patch;
    private String preRelease;
    private String buildMetadata;

    public SemanticVersion(int major, int minor, int patch) {
        this(major, minor, patch, null);
    }

    public SemanticVersion(int major, int minor, int patch, String preRelease) {
        this(major, minor, patch, preRelease, null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('.').append(minor).append('.').append(patch);
        if (preRelease != null) {
            sb.append('-');
            sb.append(preRelease);
        }
        if (buildMetadata != null) {
            sb.append('+');
            sb.append(buildMetadata);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(SemanticVersion other) {
        if (this.major != other.major) return Integer.compare(this.major, other.major);
        if (this.minor != other.minor) return Integer.compare(this.minor, other.minor);
        if (this.patch != other.patch) return Integer.compare(this.patch, other.patch);

        boolean isThisStable = this.preRelease == null;
        boolean isOtherStable = other.preRelease == null;
        if ( isThisStable && !isOtherStable) return -1;     // this precedes.
        if (!isThisStable &&  isOtherStable) return 1;      // the other precedes.
        if (!isThisStable && !isOtherStable) return this.preRelease.compareTo(other.preRelease);

        // buildMetadata doesn't affect to the order.

        return 0;
    }

    @Override
    public VersionSystem getVersionSystem() {
        return VersionSystem.SEMANTIC;
    }

    public enum Parser implements com.doubledeltas.minecollector.util.Parser<SemanticVersion> {
        INSTANCE;
        
        @Override
        public boolean canParse(String string) {
            return string != null && SemanticVersion.PATTERN.matcher(string).matches();
        }

        @Override
        public SemanticVersion parse(String string) {
            Matcher matcher = SemanticVersion.PATTERN.matcher(string);
            matcher.matches();
            int major = Integer.parseInt(matcher.group("major"));
            int minor = Integer.parseInt(matcher.group("minor"));
            int patch = Integer.parseInt(matcher.group("patch"));
            String preRelease = matcher.group("prerelease");
            String buildMetadata = matcher.group("buildmetadata");

            return new SemanticVersion(major, minor, patch, preRelease, buildMetadata);
        }
    }
}
