package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 초기 로딩을 위한 메타데이터만 포함된 구조체
 */
@NoArgsConstructor @AllArgsConstructor @Data
public class McolConfigMeta implements McolConfigSchema
{
    private String configVersion;

    @Override
    public void validate() throws InvalidConfigException {
    }
}
