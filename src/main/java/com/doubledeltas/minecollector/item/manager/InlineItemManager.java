package com.doubledeltas.minecollector.item.manager;

import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.item.itemCode.ItemCode;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

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
                .displayName("§c이전 페이지가 없습니다")
                .addBannerPattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.HALF_VERTICAL_MIRROR)
                .addBannerPattern(DyeColor.RED, PatternType.SQUARE_TOP_RIGHT)
                .addBannerPattern(DyeColor.RED, PatternType.SQUARE_BOTTOM_RIGHT)
                .addBannerPattern(DyeColor.RED, PatternType.TRIANGLES_TOP)
                .addBannerPattern(DyeColor.RED, PatternType.TRIANGLES_BOTTOM)
                .addBannerPattern(DyeColor.RED, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack getGuiPrev() {
        return new ItemBuilder(Material.BLACK_BANNER)
                .displayName("§e이전 페이지로")
                .addBannerPattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.HALF_VERTICAL_MIRROR)
                .addBannerPattern(DyeColor.BLACK, PatternType.SQUARE_TOP_RIGHT)
                .addBannerPattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_RIGHT)
                .addBannerPattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP)
                .addBannerPattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM)
                .addBannerPattern(DyeColor.BLACK, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack getGuiNoNext() {
        return new ItemBuilder(Material.RED_BANNER)
                .displayName("§c이전 페이지가 없습니다")
                .addBannerPattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.HALF_VERTICAL)
                .addBannerPattern(DyeColor.RED, PatternType.SQUARE_TOP_LEFT)
                .addBannerPattern(DyeColor.RED, PatternType.SQUARE_BOTTOM_LEFT)
                .addBannerPattern(DyeColor.RED, PatternType.TRIANGLES_TOP)
                .addBannerPattern(DyeColor.RED, PatternType.TRIANGLES_BOTTOM)
                .addBannerPattern(DyeColor.RED, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack getGuiNext() {
        return new ItemBuilder(Material.BLACK_BANNER)
                .displayName("§e이전 페이지로")
                .addBannerPattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.HALF_VERTICAL)
                .addBannerPattern(DyeColor.BLACK, PatternType.SQUARE_TOP_LEFT)
                .addBannerPattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_LEFT)
                .addBannerPattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP)
                .addBannerPattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM)
                .addBannerPattern(DyeColor.BLACK, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack getGuiBack() {
        return new ItemBuilder(Material.IRON_DOOR)
                .displayName("§c§lBack")
                .build();
    }

    private ItemStack getGuiCollection() {
        return new ItemBuilder(Material.KNOWLEDGE_BOOK)
                .displayName("§a§l[ §f도감 §a§l]")
                .lore("§7수집한 아이템의 목록을 볼 수 있습니다.")
                .build();
    }

    private ItemStack getGuiDump() {
        return new ItemBuilder(Material.CHEST)
                .displayName("§6§l[ §f수집 §6§l]")
                .lore("§7많은 아이템을 손쉽게 수집할 수 있습니다.")
                .build();
    }

    private ItemStack getGuiRanking() {
        return new ItemBuilder(Material.MOJANG_BANNER_PATTERN)
                .displayName("§d§l[ §f랭킹 §d§l]")
                .lore("§7수집 점수가 가장 높은 TOP 10을 보여줍니다.")
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack getGuiUnknown() {
        return new ItemBuilder(Material.LIGHT_GRAY_BANNER)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT)
                .addBannerPattern(DyeColor.LIGHT_GRAY, PatternType.HALF_HORIZONTAL_MIRROR)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_TOP)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.SQUARE_BOTTOM_LEFT)
                .addBannerPattern(DyeColor.LIGHT_GRAY, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack getGuiOk() {
        return new ItemBuilder(Material.LIME_BANNER)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_TOP)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_LEFT)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT)
                .addBannerPattern(DyeColor.LIME, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack getGuiHmm() {
        return new ItemBuilder(Material.ORANGE_BANNER)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE)
                .addBannerPattern(DyeColor.ORANGE, PatternType.STRIPE_SMALL)
                .addBannerPattern(DyeColor.ORANGE, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    private ItemStack getGuiNo() {
        return new ItemBuilder(Material.RED_BANNER)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT)
                .addBannerPattern(DyeColor.RED, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }


}
