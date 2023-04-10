package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.GameDirector;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class DumpGui extends Gui {
    private static final int INDEX_COLLECT = 49;
    private static final int INDEX_BACK = 52;

    private enum ProcessState { OK, HMM, NO }
    private ProcessState state = ProcessState.OK;

    public DumpGui() {
        super(6, "§8[ §2마인§0콜렉터 §8]§0 - 수집");

        ItemManager itemManager = MineCollector.getPlugin().getItemManager();

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        inventory.setItem(INDEX_COLLECT, itemManager.getItem(GuiItem.OK));
        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        final ItemStack AIR_ITEM = new ItemStack(Material.AIR);

        if (45 <= e.getRawSlot() && e.getRawSlot() < 53)
            e.setCancelled(true);

        if (e.getRawSlot() == INDEX_COLLECT && state == ProcessState.OK) {
            setState(ProcessState.HMM);

            for (int i=0; i<=44; i++) {
                ItemStack item = Objects.requireNonNullElse(inventory.getItem(i), AIR_ITEM);

                if (!GameDirector.isCollectable(item)) {
                    MessageUtil.send(player,
                            "§e수집할 수 없는 아이템(§7%s§e)은 수집되지 않았습니다.".formatted(item.getItemMeta().getDisplayName())
                    );
                    continue;
                }
                GameDirector.collect(player, item);
                inventory.setItem(i, AIR_ITEM);
            }

            setState(ProcessState.OK);
            SoundUtil.playCollect(player);
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 1, 1, 1);
        }

        else if (e.getRawSlot() == INDEX_BACK) {
            new HubGui().openGui(player);
            SoundUtil.playPage(player);
        }
    }

    private void setState(ProcessState to) {
        ItemManager itemManager = MineCollector.getPlugin().getItemManager();

        this.state = to;
        inventory.setItem(INDEX_COLLECT, switch (to) {
            case OK -> itemManager.getItem(GuiItem.OK);
            case HMM -> itemManager.getItem(GuiItem.HMM);
            case NO -> itemManager.getItem(GuiItem.NO);
        });
    }
}
