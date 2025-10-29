package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.version.CurrentSchema;
import com.doubledeltas.minecollector.version.SchemaLoadingException;
import com.doubledeltas.minecollector.config.McolConfig;

public interface CurrentMcolConfigSchema extends McolConfigSchema, CurrentSchema<McolConfig> {
    McolConfig convert() throws SchemaLoadingException;
}
