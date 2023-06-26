package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.mission.Mission;
import com.doubledeltas.minecollector.mission.MissionItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MissionDetailGui extends Gui {
    private static final int INDEX_BACK = 52;

    public MissionDetailGui(Mission mission) {
        super(6, "§8[ §2마인§0콜렉터 §8]§0 - 미션 \"%s\"".formatted(mission.getName()));

        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        MissionItem[] missionItems = mission.getItems();
        for (int i=0; i < missionItems.length; i++) {
            inventory.setItem(i, missionItems[i].getIcon());
        }

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getRawSlot() == INDEX_BACK) {
            new MissionGui().openGui(player);
            SoundUtil.playPage(player);
        }
    }
}
