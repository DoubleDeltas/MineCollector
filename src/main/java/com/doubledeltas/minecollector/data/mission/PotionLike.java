package com.doubledeltas.minecollector.data.mission;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;

import java.util.Objects;

public interface PotionLike extends MissionItem {
    @AllArgsConstructor
    enum Type {
        NORMAL(Material.POTION),
        SPLASH(Material.SPLASH_POTION),
        LINGERING(Material.LINGERING_POTION),
        ARROW(Material.TIPPED_ARROW),
        ;

        private Material material;
        public Material getMaterial() {
            return material;
        }
    }

    Type getType();
    PotionData getPotionData();

    @Override
    default ItemStack getIcon() {
        ItemStack icon = new ItemStack(this.getType().getMaterial());
        PotionMeta meta = (PotionMeta) icon.getItemMeta();
        meta.setBasePotionData(this.getPotionData());
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    default boolean validate(ItemStack item) {
        PotionMeta pMeta = (PotionMeta) item.getItemMeta();

        if (pMeta == null)
            return false;

        return item.getType() == this.getType().getMaterial()
                && Objects.equals(pMeta.getBasePotionData(), this.getPotionData());
    }
}
