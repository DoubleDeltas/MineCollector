package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface McolConfigSchema {
    interface Scoring {}
    interface Announcement {}
    interface Game {}
    interface DB {}

    String getConfigVersion();

    void validate() throws InvalidConfigException;
    McolConfig convert() throws InvalidConfigException;

    @Data @NoArgsConstructor @AllArgsConstructor
    class PlaceholderContext {
        private MineCollector plugin;
        private McolConfigSchema schema;
    }

    static McolConfigSchema getLatestDefault() {
        return new McolConfigSchema1_3();
    }
}
