package com.doubledeltas.minecollector.item;

import lombok.AllArgsConstructor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.util.List;

@AllArgsConstructor
public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        meta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, 1);
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
        try {
            BannerMeta bannerMeta = (BannerMeta) meta;
            bannerMeta.addPattern(new Pattern(color, pattern));
        }
        catch (ClassCastException ex) {}
        return this;
    }

    public ItemBuilder itemFlags(ItemFlag... flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        item.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder storeEnchantment(Enchantment enchantment, int level) {
        try {
            EnchantmentStorageMeta eMeta = (EnchantmentStorageMeta) meta;
            eMeta.addStoredEnchant(enchantment, level, true);
        }
        catch (ClassCastException ex) {}
        return this;
    }

    public ItemBuilder potionData(PotionData potionData) {
        try {
            PotionMeta potionMeta = (PotionMeta) meta;
            potionMeta.setBasePotionData(potionData);
        }
        catch (ClassCastException ex) {}
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
