package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public abstract class Gui implements Listener{
    public static String GUI_TITLE = "§8[ §2마인§0콜렉터 §8]§0 ";
    public static String GUI_TITLE_COLLECTION = GUI_TITLE + "- 도감";
    public static String GUI_TITLE_DUMP = GUI_TITLE + "- 수집";

    protected Inventory inventory;

    public Gui(int row, String title) {
        inventory = Bukkit.createInventory(null, row * 9, title);
        MineCollector.getPlugin().getServer().getPluginManager().registerEvents(this, MineCollector.getPlugin());
    }

    public Inventory getInventory() { return inventory; }

    @EventHandler
    public final void handleEvent(InventoryOpenEvent e) {
        if (!e.getInventory().equals(inventory))
            return;
        onOpen((Player) e.getPlayer(), e);
    }

    @EventHandler
    public final void handleEvent(InventoryCloseEvent e) {
        if (!e.getInventory().equals(inventory))
            return;
        onClose((Player) e.getPlayer(), e);
    }

    @EventHandler
    public final void handleEvent(InventoryClickEvent e) {
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
