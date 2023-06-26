package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class HubGui extends Gui {
    private static final int INDEX_COLLECTION = 20;
    private static final int INDEX_MISSION = 22;
    private static final int INDEX_DUMP = 24;
    private static final int INDEX_RANKING = 46;
    private static final int INDEX_BACK = 52;

    Player player;

    public HubGui(Player player) {
        super(6, "§8[ §2마인§0콜렉터 §8]§0");
        this.player = player;

        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        for (int i=0; i<=44; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.GRAY));
        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        inventory.setItem(INDEX_COLLECTION, itemManager.getItem(GuiItem.COLLECTION));
        inventory.setItem(INDEX_MISSION, itemManager.createItem(GuiItem.MISSION, Map.of(
                "missionCleared", 0,
                "missionMax", 6
        )));
        inventory.setItem(INDEX_DUMP, itemManager.getItem(GuiItem.DUMP));
        inventory.setItem(INDEX_RANKING, itemManager.getItem(GuiItem.RANKING));
        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);

        switch (e.getRawSlot()) {
            case INDEX_COLLECTION -> {
                new CollectionGui(player, 1).openGui(player);
                SoundUtil.playPage(player);
            }
            case INDEX_MISSION -> {
                new MissionGui().openGui(player);
                SoundUtil.playPage(player);
            }
            case INDEX_DUMP -> {
                new DumpGui().openGui(player);
                SoundUtil.playPage(player);
            }
            case INDEX_RANKING -> {
                player.closeInventory();
                player.performCommand("랭킹");
                SoundUtil.playPage(player);
            }
            case INDEX_BACK -> {
                player.closeInventory();
                SoundUtil.playPage(player);
            }
        }
    }
}
