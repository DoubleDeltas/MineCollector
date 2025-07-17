package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.ui.gui.display.AirDisplay;
import com.doubledeltas.minecollector.ui.gui.display.Display;
import org.bukkit.Material;

public class AirPiece extends PlainItemPiece {
    public static final AirPiece INSTANCE = new AirPiece();

    private AirPiece() {
        super(Material.AIR);
    }

    @Override
    public int getAmount(GameData data) {
        return super.getAmount(data) > 0 ? 1 : 0;   // temp bugfix
    }

    @Override
    public void addAmount(GameData data, int delta) {
        if (data.getCollection(Material.AIR) == 0)
            data.addCollection(Material.AIR, 1);    // amount 1로 고정
    }

    @Override
    public Display createDisplay(GameData data) {
        return new AirDisplay(getAmount(data) > 0);
    }
}
