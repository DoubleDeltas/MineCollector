package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.event.event.ItemCollectEvent;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Objects;

public class DumpGui extends Gui {
    private static final int INDEX_COLLECT = 49;
    private static final int INDEX_BACK = 52;

    private static final ItemStack AIR_ITEM = new ItemStack(Material.AIR);

    private enum ProcessState { OK, HMM, NO }
    private ProcessState state = ProcessState.OK;

    public DumpGui() {
        super(6, "gui.dump.title");

        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        inventory.setItem(INDEX_COLLECT, itemManager.getItem(GuiItem.OK));
        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        int rawSlot = e.getRawSlot();
        if (45 <= rawSlot && rawSlot <= 53)
            e.setCancelled(true);

        if (rawSlot == INDEX_COLLECT && state == ProcessState.OK) {
            boolean blocked = false;
            if (!MineCollector.getInstance().getMcolConfig().isEnabled()) {
                MessageUtil.send(player, "game.cant_collect_now");
                blocked = true;
            }
            if (!player.hasPermission("minecollector.collect.dump")) {
                MessageUtil.send(player, "game.cant_collect_no_permission");
                blocked = true;
            }

            if (blocked) {
                player.closeInventory();
                SoundUtil.playFail(player);
                return;
            }

            setState(ProcessState.HMM);

            Collection<ItemStack> items = new ArrayList<>();
            BitSet bitSet = new BitSet(45);
            for (int i=0; i<=44; i++) {
                ItemStack item = Objects.requireNonNullElse(inventory.getItem(i), AIR_ITEM);

                if (!plugin.getGameDirector().isCollectable(item)) {
                    MessageUtil.send(player, "gui.dump.uncollectable", item.getItemMeta().getDisplayName());
                    continue;
                }
                items.add(item);
                bitSet.set(i);
            }

            boolean collected = plugin.getGameDirector().collect(player, items, ItemCollectEvent.Route.DUMP);
            setState(ProcessState.OK);
            if (!collected) {
                return;
            }

            for (int i=0; i<=44; i++) {
                if (bitSet.get(i))
                    inventory.setItem(i, AIR_ITEM);
            }
            SoundUtil.playCollect(player);
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 1, 1, 1);
        }

        else if (rawSlot == INDEX_BACK) {
            new HubGui().openGui(player);
            SoundUtil.playPage(player);
        }
    }

    @Override
    public void onClose(Player player, InventoryCloseEvent e) {
        super.onClose(player, e);
        for (int i=0; i<45; i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null)
                continue;
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    private void setState(ProcessState to) {
        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        this.state = to;
        inventory.setItem(INDEX_COLLECT, switch (to) {
            case OK -> itemManager.getItem(GuiItem.OK);
            case HMM -> itemManager.getItem(GuiItem.HMM);
            case NO -> itemManager.getItem(GuiItem.NO);
        });
    }
}
