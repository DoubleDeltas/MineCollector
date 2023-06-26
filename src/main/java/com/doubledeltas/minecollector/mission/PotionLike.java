package com.doubledeltas.minecollector.mission;

import com.doubledeltas.minecollector.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;

import java.util.Objects;

public interface PotionLike extends MissionItem {

    // must be one of POTION, SPLASH_POTION, LINGERING_POTION or TIPPED_ARROW
    Material getMaterial();
    PotionData getPotionData();

    @Override
    default ItemStack getIcon() {
        return new ItemBuilder(this.getMaterial())
                .potionData(this.getPotionData())
                .build();
    }

    @Override
    default boolean validate(ItemStack item) {
        PotionMeta pMeta = (PotionMeta) item.getItemMeta();

        if (pMeta == null)
            return false;

        return item.getType() == this.getMaterial()
                && Objects.equals(pMeta.getBasePotionData(), this.getPotionData());
    }
}
