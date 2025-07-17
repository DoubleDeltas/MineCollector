package com.doubledeltas.minecollector.ui.gui.display;

import com.doubledeltas.minecollector.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PlainItemDisplay extends GeneralDisplay {
    protected final ItemStack baseItem;

    public PlainItemDisplay(Material material, int amount, int level) {
        super(amount, level);
        this.baseItem = createBaseItem(material);
    }

    @Override
    public ItemStack getBaseItem() {
        return baseItem;
    }

    protected ItemStack createBaseItem(Material material) {
        return new ItemBuilder(material)
                .build();
    }
}
