package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;

public interface CurrentMcolConfigSchema extends McolConfigSchema {
    McolConfig convert() throws InvalidConfigException;
}
