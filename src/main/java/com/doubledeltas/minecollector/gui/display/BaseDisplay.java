package com.doubledeltas.minecollector.gui.display;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import org.bukkit.inventory.ItemStack;

public abstract class BaseDisplay implements Display  {
    protected final MineCollector plugin;
    protected boolean collected;

    public BaseDisplay(boolean collected) {
        this.plugin = MineCollector.getInstance();
        this.collected = collected;
    }

    @Override
    public ItemStack getItem() {
        boolean showUnknown = !plugin.getMcolConfig().getGame().isHideUnknownCollection();
        if (collected)
            return getCollectedItem();
        else if (showUnknown)
            return getUncollectedItem();
        else
            return getUnknownItem();
    }

    protected ItemStack getUnknownItem() {
        return plugin.getItemManager().getItem(GuiItem.UNKNOWN);
    }

    protected abstract ItemStack getCollectedItem();
    protected abstract ItemStack getUncollectedItem();
}
