package com.doubledeltas.minecollector.item.manager;

import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.item.itemCode.ItemCode;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InlineItemManager extends ItemManager {

    @Override
    protected ItemStack loadItem(ItemCode itemCode) {
        if (itemCode instanceof StaticItem staticItem)
            return switch (staticItem) {
                case COLLECTION_BOOK -> getCollectBook();
            };
        else if (itemCode instanceof GuiItem guiItem)
            return switch (guiItem) {
                case BLACK -> getGuiBlack();
                case GRAY -> getGuiGray();
                case NO_PREV -> getGuiNoPrev();
                case PREV -> getGuiPrev();
                case NO_NEXT -> getGuiNoNext();
                case NEXT -> getGuiNext();
                case BACK -> getGuiBack();
                case COLLECTION -> getGuiCollection();
                case DUMP -> getGuiDump();
                case RANKING -> getGuiRanking();
                case UNKNOWN -> getGuiUnknown();
                case OK -> getGuiOk();
                case HMM -> getGuiHmm();
                case NO -> getGuiNo();
            };
        return null;
    }

    private ItemStack getCollectBook() {
        return new ItemBuilder(Material.KNOWLEDGE_BOOK)
                .displayName("§a§l[ §r§f도감 §a§l]")
                .lore(
                        "§7수집한 아이템을 보거나",
                        "§7대량의 아이템을 수집할 수 있습니다."
                )
                .build();
    }

    private ItemStack getGuiBlack() {
        return new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .displayName("§0")
                .build();
    }

    private ItemStack getGuiGray() {
        return new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .displayName("§8")
                .build();
    }

    private ItemStack getGuiNoPrev() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiPrev() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiNoNext() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiNext() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiBack() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiCollection() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiDump() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiRanking() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiUnknown() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiOk() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiHmm() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }

    private ItemStack getGuiNo() {
        return new ItemBuilder(Material.RED_BANNER)
                .build();
    }


}
