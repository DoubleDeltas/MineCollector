package com.doubledeltas.minecollector.crew.schema;

import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.version.SchemaLoadingException;

public interface CurrentMcolCrewSchema {
    Crew convert() throws SchemaLoadingException;
}
