package com.doubledeltas.minecollector.item.manager;

import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.item.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InlineItemManager extends ItemManager {

    @Override
    protected ItemStack loadItem(String itemId) {
        return switch (itemId) {
            case "collect_book"     -> getCollectBook();
            case "gui_black"        -> getGuiBlack();
            case "gui_gray"         -> getGuiGray();
            case "gui_noPrev"       -> getGuiNoPrev();
            case "gui_prev"         -> getGuiPrev();
            case "gui_noNext"       -> getGuiNoNext();
            case "gui_next"         -> getGuiNext();
            case "gui_back"         -> getGuiBack();
            case "gui_collection"   -> getGuiCollection();
            case "gui_dump"         -> getGuiDump();
            case "gui_ranking"      -> getGuiRanking();
            case "gui_unknown"      -> getGuiUnknown();
            case "gui_ok"           -> getGuiOk();
            case "gui_hmm"          -> getGuiHmm();
            case "gui_no"           -> getGuiNo();
            default -> null;
        };
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
