package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.ui.chest.display.Display;
import net.md_5.bungee.api.chat.BaseComponent;

public interface Piece {
    int getAmount(GameData data);
    int getLevel(GameData data);
    boolean isCollected(GameData data);

    void addAmount(GameData data, int delta);

    Display createDisplay(GameData data);

    BaseComponent toChatComponent();
    String toPieceKey();
}
