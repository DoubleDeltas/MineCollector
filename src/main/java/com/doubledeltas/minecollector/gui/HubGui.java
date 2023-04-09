package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.stream.IntStream;

public class HubGui extends Gui {
    private static final int INDEX_COLLECTION = 20;
    private static final int INDEX_DUMP = 24;
    private static final int INDEX_RANKING = 46;
    private static final int INDEX_BACK = 52;

    public HubGui() {
        super(6, Gui.GUI_TITLE);

        ItemManager itemManager = MineCollector.getPlugin().getItemManager();

        IntStream.rangeClosed(0, 44).forEach(i -> {
            inventory.setItem(i, itemManager.getItem(GuiItem.GRAY));
        });
        IntStream.rangeClosed(45, 53).forEach(i -> {
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        });
        inventory.setItem(INDEX_COLLECTION, itemManager.getItem(GuiItem.COLLECTION));
        inventory.setItem(INDEX_DUMP, itemManager.getItem(GuiItem.DUMP));
        inventory.setItem(INDEX_RANKING, itemManager.getItem(GuiItem.RANKING));
        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getRawSlot() == INDEX_COLLECTION) {
            new CollectionGui(1).openGui(player);
            SoundUtil.playPage(player);
        }
        else if (e.getRawSlot() == INDEX_DUMP) {
            new DumpGui().openGui(player);
            SoundUtil.playPage(player);
        }
        else if (e.getRawSlot() == INDEX_RANKING) {
            player.closeInventory();
            player.performCommand("랭킹");
            SoundUtil.playPage(player);
        }
        else if (e.getRawSlot() == INDEX_BACK) {
            player.closeInventory();
            SoundUtil.playPage(player);
        }
    }
}
