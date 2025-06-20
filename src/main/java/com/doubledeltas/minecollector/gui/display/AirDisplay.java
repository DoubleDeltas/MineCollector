package com.doubledeltas.minecollector.gui.display;

import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import org.bukkit.inventory.ItemStack;

public class AirDisplay extends BaseDisplay {
    public AirDisplay(boolean collected) {
        super(collected);
    }

    @Override
    public ItemStack getCollectedItem() {
        return plugin.getItemManager().getItem(GuiItem.AIR_PLACEHOLDER);
    }

    @Override
    public ItemStack getUncollectedItem() {
        return plugin.getItemManager().getItem(GuiItem.UNKNOWN_AIR_PLACEHOLDER);
    }
}
