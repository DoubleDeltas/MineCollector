package com.doubledeltas.minecollector.crew.schema;

import com.doubledeltas.minecollector.version.SchemaLoadingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class McolCrewMeta implements McolCrewSchema {
    private String dataVersion;

    @Override
    public void validate() throws SchemaLoadingException {
    }
}
