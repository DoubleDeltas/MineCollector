package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.InvalidConfigException;
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

    @Data @NoArgsConstructor @AllArgsConstructor
    class PlaceholderContext {
        private MineCollector plugin;
        private McolConfigSchema schema;
    }

    static CurrentMcolConfigSchema getLatestDefault() {
        return new McolConfigSchema1_3_0();
    }
}
