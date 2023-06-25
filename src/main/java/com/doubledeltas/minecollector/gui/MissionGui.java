package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MissionGui extends Gui {
    private static final int INDEX_BACK = 52;

    public MissionGui() {
        super(6, "§8[ §2마인§0콜렉터 §8]§0 - 미션");

        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);
    }
}
