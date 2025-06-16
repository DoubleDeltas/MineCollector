package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.lang.LangManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public abstract class Gui implements Listener {
    protected MineCollector plugin = MineCollector.getInstance();

    @Getter
    protected Inventory inventory;

    public Gui(int row, String titleKey) {
        String title = LangManager.translateToText(titleKey);
        inventory = Bukkit.createInventory(null, row * 9, title);
        MineCollector.getInstance().getServer().getPluginManager().registerEvents(this, MineCollector.getInstance());
    }

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
