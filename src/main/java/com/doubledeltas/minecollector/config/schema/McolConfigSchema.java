package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;

public interface McolConfigSchema {
    interface Scoring {}
    interface Announcement {}
    interface Game {}
    interface DB {}

    String getConfigVersion();

    void validate() throws InvalidConfigException;
    McolConfig convert() throws InvalidConfigException;
}
