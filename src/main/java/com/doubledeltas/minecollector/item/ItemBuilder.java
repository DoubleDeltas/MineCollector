package com.doubledeltas.minecollector.item;

import com.doubledeltas.minecollector.item.itemCode.ItemCode;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder displayName(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder lore(String... newLores) {
        List<String> lores = meta.getLore();
        if (lores == null)
            lores = List.of(newLores);
        else
            lores.addAll(List.of(newLores));
        meta.setLore(lores);
        return this;
    }

    public ItemBuilder glowing() {
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder addBannerPattern(DyeColor color, PatternType pattern) {
        BannerMeta bannerMeta = (BannerMeta) meta;
        bannerMeta.addPattern(new Pattern(color, pattern));
        return this;
    }

    public ItemBuilder itemFlags(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder itemCode(ItemCode itemCode) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(ItemCode.PERSISTENT_DATA_KEY, ItemCode.PERSISTENT_DATA_TYPE, itemCode);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
