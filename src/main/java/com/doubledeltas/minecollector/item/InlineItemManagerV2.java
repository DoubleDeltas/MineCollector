package com.doubledeltas.minecollector.item;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.item.itemCode.ItemCode;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Set;

import static com.doubledeltas.minecollector.lang.LangManager.translateToText;

/**
 * translation key와 item code NBT tag가 추가된 {@link InlineItemManager}
 */
public class InlineItemManagerV2 extends InlineItemManager {
    @Override
    public void init(MineCollector plugin) {
        super.init(plugin);
    }

    private static final Set<ItemCode> ITEM_CODES_TO_CHECK_WITH_V1 = Set.of(
            GuiItem.BLACK, GuiItem.GRAY, GuiItem.UNKNOWN
    );

    @Override
    public boolean isItemOf(ItemStack item, ItemCode itemCode) {
        boolean v1Check = super.isItemOf(item, itemCode);
        if (ITEM_CODES_TO_CHECK_WITH_V1.contains(itemCode))
            return v1Check;
        if (!item.hasItemMeta())
            return v1Check;
        if (StaticItem.COLLECTION_BOOK.equals(itemCode) && v1Check)
            return true;        // old version of collection book

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        ItemCode pdcItemCode = pdc.get(ItemCode.PERSISTENT_DATA_KEY, ItemCode.PERSISTENT_DATA_TYPE);

        return StaticItem.COLLECTION_BOOK.equals(pdcItemCode);
    }

    @Override
    protected ItemStack getCollectBook() {
        return new ItemBuilder(Material.KNOWLEDGE_BOOK)
                .displayName(translateToText("item.static.collection_book.display_name"))
                .lore(
                        translateToText("item.static.collection_book.lore_1"),
                        translateToText("item.static.collection_book.lore_2")
                )
                .itemCode(StaticItem.COLLECTION_BOOK)
                .build();
    }

    @Override
    protected ItemStack getGuiNoPrev() {
        return new ItemBuilder(Material.RED_BANNER)
                .displayName(translateToText("item.gui.no_prev.display_name"))
                .addBannerPattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.HALF_VERTICAL_MIRROR)
                .addBannerPattern(DyeColor.RED, PatternType.SQUARE_TOP_RIGHT)
                .addBannerPattern(DyeColor.RED, PatternType.SQUARE_BOTTOM_RIGHT)
                .addBannerPattern(DyeColor.RED, PatternType.TRIANGLES_TOP)
                .addBannerPattern(DyeColor.RED, PatternType.TRIANGLES_BOTTOM)
                .addBannerPattern(DyeColor.RED, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .itemCode(GuiItem.NO_PREV)
                .build();
    }

    @Override
    protected ItemStack getGuiPrev() {
        return new ItemBuilder(Material.BLACK_BANNER)
                .displayName(translateToText("item.gui.prev.display_name"))
                .addBannerPattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.HALF_VERTICAL_MIRROR)
                .addBannerPattern(DyeColor.BLACK, PatternType.SQUARE_TOP_RIGHT)
                .addBannerPattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_RIGHT)
                .addBannerPattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP)
                .addBannerPattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM)
                .addBannerPattern(DyeColor.BLACK, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .itemCode(GuiItem.PREV)
                .build();
    }

    @Override
    protected ItemStack getGuiNoNext() {
        return new ItemBuilder(Material.RED_BANNER)
                .displayName(translateToText("item.gui.no_next.display_name"))
                .addBannerPattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.HALF_VERTICAL)
                .addBannerPattern(DyeColor.RED, PatternType.SQUARE_TOP_LEFT)
                .addBannerPattern(DyeColor.RED, PatternType.SQUARE_BOTTOM_LEFT)
                .addBannerPattern(DyeColor.RED, PatternType.TRIANGLES_TOP)
                .addBannerPattern(DyeColor.RED, PatternType.TRIANGLES_BOTTOM)
                .addBannerPattern(DyeColor.RED, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .itemCode(GuiItem.NO_NEXT)
                .build();
    }

    @Override
    protected ItemStack getGuiNext() {
        return new ItemBuilder(Material.BLACK_BANNER)
                .displayName(translateToText("item.gui.next.display_name"))
                .addBannerPattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE)
                .addBannerPattern(DyeColor.WHITE, PatternType.HALF_VERTICAL)
                .addBannerPattern(DyeColor.BLACK, PatternType.SQUARE_TOP_LEFT)
                .addBannerPattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_LEFT)
                .addBannerPattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP)
                .addBannerPattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM)
                .addBannerPattern(DyeColor.BLACK, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .itemCode(GuiItem.NEXT)
                .build();
    }

    @Override
    protected ItemStack getGuiBack() {
        return new ItemBuilder(Material.IRON_DOOR)
                .displayName(translateToText("item.gui.back.display_name"))
                .itemCode(GuiItem.BACK)
                .build();
    }

    @Override
    protected ItemStack getGuiCollection() {
        return new ItemBuilder(Material.KNOWLEDGE_BOOK)
                .displayName(translateToText("item.gui.collection.display_name"))
                .lore(translateToText("item.gui.collection.lore_1"))
                .itemCode(GuiItem.COLLECTION)
                .build();
    }

    @Override
    protected ItemStack getGuiDump() {
        return new ItemBuilder(Material.CHEST)
                .displayName(translateToText("item.gui.dump.display_name"))
                .lore(translateToText("item.gui.dump.lore_1"))
                .itemCode(GuiItem.DUMP)
                .build();
    }

    @Override
    protected ItemStack getGuiRanking() {
        return new ItemBuilder(Material.MOJANG_BANNER_PATTERN)
                .displayName(translateToText("item.gui.ranking.display_name"))
                .lore(translateToText("item.gui.dump.lore_1"))
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .itemCode(GuiItem.RANKING)
                .build();
    }

    @Override
    protected ItemStack getGuiOk() {
        return new ItemBuilder(Material.LIME_BANNER)
                .displayName(translateToText("item.gui.ok.display_name"))
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_TOP)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_LEFT)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT)
                .addBannerPattern(DyeColor.LIME, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    @Override
    protected ItemStack getGuiHmm() {
        return new ItemBuilder(Material.ORANGE_BANNER)
                .displayName(translateToText("item.gui.hmm.display_name"))
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE)
                .addBannerPattern(DyeColor.ORANGE, PatternType.STRIPE_SMALL)
                .addBannerPattern(DyeColor.ORANGE, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    @Override
    protected ItemStack getGuiNo() {
        return new ItemBuilder(Material.RED_BANNER)
                .displayName(translateToText("item.gui.no.display_name"))
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT)
                .addBannerPattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT)
                .addBannerPattern(DyeColor.RED, PatternType.BORDER)
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .build();
    }

    @Override
    protected ItemStack getGuiAirPlaceholder() {
        return new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .displayName(translateToText("item.gui.air_placeholder.display_name"))
                .lore(translateToText("gui.collection.collected"))
                .build();
    }

    @Override
    protected ItemStack getGuiUnknownAirPlaceholder() {
        return new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .displayName(translateToText("item.gui.air_placeholder.display_name"))
                .lore(translateToText("gui.collection.not_collected_yet"))
                .build();
    }

    @Override
    protected ItemStack getGuiRawCore() {
        return new ItemBuilder(Material.END_CRYSTAL)
                .displayName(translateToText("item.gui.raw_core.display_name", "[totalScore]"))
                .lore(
                        translateToText("item.gui.raw_core.lore_1", "[collectionScore]"),
                        translateToText("item.gui.raw_core.lore_2", "[stackScore]"),
                        translateToText("item.gui.raw_core.lore_3", "[advScore]")
                )
                .build();
    }
}
