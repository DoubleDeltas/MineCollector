package com.doubledeltas.minecollector.mission;

import org.bukkit.inventory.ItemStack;

public interface MissionItem {
    ItemStack getIcon();

    boolean validate(ItemStack item);

    MissionItem getByName(String name);

    String name();
}
