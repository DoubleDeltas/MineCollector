package com.doubledeltas.minecollector.ui.book;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public abstract class AbstractBookGui implements BookGui {
    protected ItemStack item;
    protected BookMeta meta;

    public AbstractBookGui() {
        this.item = new ItemStack(Material.WRITTEN_BOOK);
        this.meta = (BookMeta) item.getItemMeta();
    }

    @Override
    public void openGui(Player player) {
        player.openBook(item);
    }

}
