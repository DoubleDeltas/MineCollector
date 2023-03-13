package com.doubledeltas.minecollector.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InlineItemManager extends ItemManager {

    @Override
    protected ItemStack loadItem(String itemId) {
        switch (itemId) {
            case "collect_book":
                return getCollectBook();
            default:
                return null;
        }
    }

    private ItemStack getCollectBook() {
        ItemStack item = new ItemStack(Material.KNOWLEDGE_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§a§l[ §r도감 §a§l]");
        meta.setLore(Arrays.asList(
                "§7Lore1",
                "§7Lore2"
        ));
        item.setItemMeta(meta);
        return item;
    }
}
