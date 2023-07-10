package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.ConfigManager;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.mission.Mission;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MissionGui extends Gui {
    private static final int INDEX_BACK = 52;

    Player player;

    public MissionGui(Player player) {
        super(6, "§8[ §2마인§0콜렉터 §8]§0 - 미션");

        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        Mission[] missions = Mission.values();
        boolean hiding = MineCollector.getInstance().getMcolConfig().getGame().isHideUnknownCollection();
        for (int i=0; i < missions.length; i++) {
            boolean unknown = DataManager.getData(player).getMissionProgress().get(missions[i]).size() == 0;
            ItemStack item;
            if (hiding && unknown)
                item = MineCollector.getInstance().getItemManager().getItem(GuiItem.UNKNOWN);
            else
                item = missions[i].getIcon();
            inventory.setItem(i, item);
        }

        for (int i = missions.length; i < 45; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.GRAY));

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));

        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);

        int slot = e.getRawSlot();
        Mission[] missions = Mission.values();
        if (slot < missions.length) {
            boolean hiding = MineCollector.getInstance().getMcolConfig().getGame().isHideUnknownCollection();
            boolean unknown = DataManager.getData(player).getMissionProgress().get(missions[slot]).size() == 0;
            if (hiding && unknown)
                return;
            new MissionDetailGui(missions[slot]).openGui(player);
            SoundUtil.playPage(player);
        }
        else if (slot == INDEX_BACK) {
            new HubGui(player).openGui(player);
            SoundUtil.playPage(player);
        }
    }
}
