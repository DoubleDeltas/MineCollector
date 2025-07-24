package com.doubledeltas.minecollector.collection.filter;

import com.doubledeltas.minecollector.collection.CollectionManager;
import com.doubledeltas.minecollector.data.GameData;

public record PieceFilterContext(
   CollectionManager manager,
   GameData data
) {}
