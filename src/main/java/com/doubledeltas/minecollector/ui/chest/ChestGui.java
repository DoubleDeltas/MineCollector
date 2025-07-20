package com.doubledeltas.minecollector.ui.chest;

import com.doubledeltas.minecollector.ui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public interface ChestGui extends Gui {
    void onOpen(Player player, InventoryOpenEvent e);
    void onClick(Player player, InventoryClickEvent e);
    void onClose(Player player, InventoryCloseEvent e);
}
