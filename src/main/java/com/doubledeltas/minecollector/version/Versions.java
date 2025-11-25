package com.doubledeltas.minecollector.version;

import lombok.experimental.UtilityClass;

/**
 * Pre-defined versions
 */
@UtilityClass
public final class Versions {
    public static final UnlabeledVersion    UNLABELED   = UnlabeledVersion.INSTANCE;
    public static final SemanticVersion     V1_3_0      = new SemanticVersion(1, 3, 0);
    public static final SemanticVersion     V1_4_0      = new SemanticVersion(1, 4, 0);
}
