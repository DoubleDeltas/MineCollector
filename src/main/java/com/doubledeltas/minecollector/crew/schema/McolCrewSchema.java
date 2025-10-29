package com.doubledeltas.minecollector.crew.schema;

import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.version.Schema;

public interface McolCrewSchema extends Schema<Crew> {
    String getDataVersion();
}
