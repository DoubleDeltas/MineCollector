package com.doubledeltas.minecollector.ui.chest;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.lang.LangManager;
import com.doubledeltas.minecollector.ui.Gui;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public abstract class AbstractChestGui implements ChestGui, Listener {
    protected MineCollector plugin = MineCollector.getInstance();

    @Getter
    protected Inventory inventory;

    public AbstractChestGui(int row, String titleKey) {
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

    @Override
    public void onOpen(Player player, InventoryOpenEvent e) {}

    @Override
    public void onClick(Player player, InventoryClickEvent e) {}

    @Override
    public void onClose(Player player, InventoryCloseEvent e) {}
}
