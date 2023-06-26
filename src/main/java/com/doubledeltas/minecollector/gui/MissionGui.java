package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.mission.Mission;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MissionGui extends Gui {
    private static final int INDEX_BACK = 52;

    public MissionGui() {
        super(6, "§8[ §2마인§0콜렉터 §8]§0 - 미션");

        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        Mission[] missions = Mission.values();
        for (int i=0; i < missions.length; i++) {
            inventory.setItem(i, missions[i].getIcon());
        }

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);

        int slot = e.getRawSlot();
        if (slot < 45) {
            Mission[] missions = Mission.values();
            new MissionDetailGui(missions[slot]).openGui(player);
            SoundUtil.playPage(player);
        }
        else if (slot == INDEX_BACK) {
            new HubGui(player).openGui(player);
            SoundUtil.playPage(player);
        }
    }
}
