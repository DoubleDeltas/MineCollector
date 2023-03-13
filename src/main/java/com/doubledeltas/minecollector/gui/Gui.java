package com.doubledeltas.minecollector.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Gui implements Listener{
    private Inventory inventory;

    public Gui(JavaPlugin plugin, int size, String title) {
        inventory = Bukkit.createInventory(null, size, title);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Inventory getInventory() { return inventory; }

    @EventHandler
    private final void handleEvent(InventoryOpenEvent e) {
        if (!e.getInventory().equals(inventory))
            return;
        onOpen((Player) e.getPlayer(), e);
    }

    @EventHandler
    private final void handleEvent(InventoryCloseEvent e) {
        if (!e.getInventory().equals(inventory))
            return;
        onClose((Player) e.getPlayer(), e);
    }

    @EventHandler
    private final void handleEvent(InventoryClickEvent e) {
        if (!e.getInventory().equals(inventory))
            return;
        onClick((Player) e.getWhoClicked(), e);
    }

    public final void openGui(Player player) {
        player.openInventory(this.inventory);
    }

    public void onOpen(Player player, InventoryOpenEvent e) {}

    public void onClick(Player player, InventoryClickEvent e) {}

    public void onClose(Player player, InventoryCloseEvent e) {}
}
