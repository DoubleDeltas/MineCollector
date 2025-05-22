package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.yaml.YamlSchema;

public interface ConfigSchema<T> extends YamlSchema<T, InvalidConfigException> {
}
