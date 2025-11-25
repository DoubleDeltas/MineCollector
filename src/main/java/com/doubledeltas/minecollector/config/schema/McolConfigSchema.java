package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.version.Schema;
import com.doubledeltas.minecollector.version.SchemaLoadingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface McolConfigSchema extends Schema<McolConfig> {
    interface Scoring {}
    interface Announcement {}
    interface Game {}
    interface DB {}

    String getConfigVersion();

    @Data @NoArgsConstructor @AllArgsConstructor
    class PlaceholderContext {
        private MineCollector plugin;
        private McolConfigSchema schema;
    }

    static CurrentMcolConfigSchema getLatestDefault() {
        return new McolConfigSchema1_3_0();
    }
}
