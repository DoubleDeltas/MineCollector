package com.doubledeltas.minecollector.crew.schema;

import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.version.CurrentSchema;
import com.doubledeltas.minecollector.version.SchemaLoadingException;

public interface CurrentMcolCrewSchema extends McolCrewSchema, CurrentSchema<Crew> {
    Crew convert() throws SchemaLoadingException;
}
