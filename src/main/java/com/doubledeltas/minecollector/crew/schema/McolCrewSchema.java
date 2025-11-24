package com.doubledeltas.minecollector.crew.schema;

import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.version.Schema;

public interface McolCrewSchema extends Schema<Crew> {
    interface CrewMember {}

    String getDataVersion();

    static CurrentMcolCrewSchema deserialize(Crew crew) {
        return McolCrewSchema1_4_0.deserialize(crew);
    }
}
