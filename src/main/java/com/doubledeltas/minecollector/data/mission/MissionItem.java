package com.doubledeltas.minecollector.data.mission;

import org.bukkit.inventory.ItemStack;

public interface MissionItem {
    ItemStack getIcon();

    boolean validate(ItemStack item);
}
