package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.data.GameStatistics;
import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class CollectionGui extends Gui {
    private static final int INDEX_PREV = 47;
    private static final int INDEX_CORE = 48;
    private static final int INDEX_NEXT = 49;
    private static final int INDEX_BACK = 52;

    private int page;

    public CollectionGui(Player player, int page) {
        super(6, "§8[ §2마인§0콜렉터 §8]§0 - 도감");
        this.page = page;

        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        for (int i=0; i <= 44; i++) {
            int idx = (page - 1) * 45 + i;
            if (idx >= Material.values().length) {
                inventory.setItem(i, itemManager.getItem(GuiItem.GRAY));
                continue;
            }
            GameData data = DataManager.getData(player);
            Material material = Material.values()[idx];

            int flags = (data.getCollection(material) == 0 ? 0 : 0b001)
                      | (MineCollector.getInstance().getMcolConfig().getGame().isHideUnknownCollection() ? 0b010 : 0)
                      | (material == Material.AIR ? 0b100 : 0);
            ItemStack item;
            switch (flags) {
                case 0b000          -> item = new ItemBuilder(material)
                        .lore("§c아직 수집되지 않았습니다")
                        .build();
                case 0b001, 0b011   -> {
                    int amount = data.getCollection(material);
                    int quo = amount / 64;
                    int rem = amount % 64;
                    int lv = data.getLevel(material);
                    ItemBuilder builder = new ItemBuilder(material, lv)
                            .lore((quo > 0) ?
                                    "§7수집된 개수: %d셋 %d개".formatted(quo, rem) :
                                    "§7수집된 개수: %d개".formatted(rem)
                            );
                    if (lv >= 5)
                        builder.glowing();
                    item = builder.build();
                }
                case 0b010, 0b110   -> item = itemManager.getItem(GuiItem.UNKNOWN);
                case 0b100          -> item = itemManager.getItem(GuiItem.UNKNOWN_AIR_PLACEHOLDER);
                case 0b101, 0b111   -> item = itemManager.getItem(GuiItem.AIR_PLACEHOLDER);
                default -> item = null;
            }
            inventory.setItem(i, item);
        }

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        inventory.setItem(INDEX_PREV, itemManager.getItem((page == 1) ? GuiItem.NO_PREV : GuiItem.PREV));

        ItemStack coreItem = itemManager.createItem(GuiItem.CORE, new GameStatistics(DataManager.getData(player)).toMap());
        coreItem.setAmount(page);
        inventory.setItem(INDEX_CORE, coreItem);

        inventory.setItem(INDEX_NEXT, itemManager.getItem(isLastPage() ? GuiItem.NO_NEXT : GuiItem.NEXT));
        inventory.setItem(INDEX_BACK, itemManager.getItem(GuiItem.BACK));
    }

    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);

        if (e.getRawSlot() == INDEX_PREV && page > 1) {
            new CollectionGui(player, page - 1).openGui(player);
            SoundUtil.playPage(player);
        }
        else if (e.getRawSlot() == INDEX_NEXT && !isLastPage()) {
            new CollectionGui(player, page + 1).openGui(player);
            SoundUtil.playPage(player);
        }
        else if (e.getRawSlot() == INDEX_BACK) {
            new HubGui(player).openGui(player);
            SoundUtil.playPage(player);
        }
    }

    private boolean isLastPage() {
        return (this.page + 1) * 45 >= Material.values().length;
    }
}
