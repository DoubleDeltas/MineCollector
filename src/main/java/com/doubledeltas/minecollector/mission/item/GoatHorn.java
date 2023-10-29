package com.doubledeltas.minecollector.mission.item;

import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.mission.MissionItem;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum GoatHorn implements MissionItem {

    ;

    // TODO: GoatHorn

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.GOAT_HORN)
                .build();
    }

    @Override
    public boolean validate(ItemStack item) {
        return false;
    }

    @Override
    public MissionItem getByName(String name) {
        return null;
    }
}
