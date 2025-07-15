package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.gui.display.Display;
import com.doubledeltas.minecollector.gui.display.PlainItemDisplay;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;

@Getter
public class PlainItemPiece implements Piece {
    private final Material material;

    public PlainItemPiece(Material material) {
        this.material = material;
    }

    @Override
    public int getAmount(GameData data) {
        return data.getCollection(getMaterial());
    }

    @Override
    public int getLevel(GameData data) {
        return data.getLevel(getMaterial());
    }

    @Override
    public boolean isCollected(GameData data) {
        return getAmount(data) > 0;
    }

    @Override
    public void addAmount(GameData data, int delta) {
        data.addCollection(material, delta);
    }

    @Override
    public Display createDisplay(GameData data) {
        return new PlainItemDisplay(material, getAmount(data), getLevel(data));
    }

    @Override
    public BaseComponent toChatComponent() {
        return new TranslatableComponent(material.getItemTranslationKey());
    }

    @Override
    public String toPieceKey() {
        return material.getKey().toString();
    }
}
