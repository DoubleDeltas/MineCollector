package com.doubledeltas.minecollector.gui;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.data.GameStatistics;
import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static com.doubledeltas.minecollector.lang.LangManager.translateToComponents;
import static com.doubledeltas.minecollector.lang.LangManager.translateToText;

public class CollectionGui extends Gui {
    private static final int INDEX_PREV = 47;
    private static final int INDEX_CORE = 48;
    private static final int INDEX_NEXT = 49;
    private static final int INDEX_BACK = 52;

    private final int page;

    public CollectionGui(Player player, int page) {
        super(6, "gui.collection.title");
        this.page = page;

        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        for (int i=0; i <= 44; i++) {
            int idx = (page - 1) * 45 + i;
            if (idx >= Material.values().length) {
                inventory.setItem(i, itemManager.getItem(GuiItem.GRAY));
                continue;
            }
            GameData data = plugin.getDataManager().getData(player);
            Material material = Material.values()[idx];

            ItemStack item;
            boolean showUnknown = !plugin.getMcolConfig().getGame().isHideUnknownCollection();
            boolean collected = data.getCollection(material) > 0;
            if (collected)
                item = getCollectedItem(data, material);
            else if (showUnknown)
                item = getUncollectedItem(material);
            else
                item = itemManager.getItem(GuiItem.UNKNOWN);
            inventory.setItem(i, item);
        }

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));
        inventory.setItem(INDEX_PREV, itemManager.getItem((page == 1) ? GuiItem.NO_PREV : GuiItem.PREV));

        ItemStack coreItem = itemManager.createItem(
                GuiItem.CORE,
                new GameStatistics(plugin.getDataManager().getData(player)).toMap()
        );
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
            new HubGui().openGui(player);
            SoundUtil.playPage(player);
        }
    }

    private ItemStack getUncollectedItem(Material material) {
        if (material == Material.AIR)
            return plugin.getItemManager().getItem(GuiItem.UNKNOWN_AIR_PLACEHOLDER);

        return new ItemBuilder(material)
                .lore(translateToText("gui.collection.not_collected_yet"))
                .build();
    }

    private ItemStack getCollectedItem(GameData data, Material material) {
        if (material == Material.AIR)
            return plugin.getItemManager().getItem(GuiItem.AIR_PLACEHOLDER);

        int amount = data.getCollection(material);
        int quo = amount / 64;
        int rem = amount % 64;
        int lv = data.getLevel(material);

        BaseComponent[] countDisplay = (quo > 0)
                ? translateToComponents("game.stack_and_pcs", quo, rem)
                : translateToComponents("game.pcs", rem);

        BaseComponent[] components = new ComponentBuilder()
                .append(translateToComponents("gui.collection.count"))
                .append(" ")
                .append(countDisplay)
                .create();

        ItemBuilder builder = new ItemBuilder(material, lv)
                .lore(BaseComponent.toLegacyText(components));

        if (lv >= 5)
            builder.glowing();

        return builder.build();
    }

    private boolean isLastPage() {
        return (this.page + 1) * 45 >= Material.values().length;
    }
}
