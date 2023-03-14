package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.constant.Titles;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.stream.IntStream;

public class HubGui extends Gui {
    public HubGui() {
        super(6, Titles.GUI_TITLE);

        ItemManager itemManager = MineCollector.getPlugin().getItemManager();

        IntStream.rangeClosed(0, 44).forEach(i -> {
            inventory.setItem(i, itemManager.getItem(GuiItem.GRAY));
        });
        IntStream.rangeClosed(45, 53).forEach(i -> {
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        });
        inventory.setItem(20, itemManager.getItem(GuiItem.COLLECTION));
        inventory.setItem(24, itemManager.getItem(GuiItem.DUMP));
        inventory.setItem(46, itemManager.getItem(GuiItem.RANKING));
        inventory.setItem(52, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);
    }
}
