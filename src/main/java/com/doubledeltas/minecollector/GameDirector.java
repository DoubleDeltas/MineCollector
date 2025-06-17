package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.config.AnnouncementTarget;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.data.GameStatistics;
import com.doubledeltas.minecollector.event.event.CollectionLevelUpEvent;
import com.doubledeltas.minecollector.event.event.ItemCollectEvent;
import com.doubledeltas.minecollector.gui.HubGui;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import com.doubledeltas.minecollector.lang.LangManager;
import com.doubledeltas.minecollector.lang.MessageKey;
import com.doubledeltas.minecollector.util.CollectionLevelUtil;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementDisplayType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class GameDirector implements McolInitializable {
    private MineCollector plugin;

    @Override
    public void init(MineCollector plugin) {
        this.plugin = MineCollector.getInstance();
    }

    /**
     * 아이템을 수집합니다.
     * @param player 플레이어
     * @param items 수집할 아이템들
     * @return 아이템 수집 성공 여부. 공기의 중복 수집도 성공으로 간주합니다.
     */
    public boolean collect(Player player, Collection<ItemStack> items, ItemCollectEvent.Route route) {
        ItemCollectEvent collectEvent = new ItemCollectEvent(player, items, route);
        Bukkit.getPluginManager().callEvent(collectEvent);
        if (collectEvent.isCancelled())
            return false;

        GameData data = plugin.getDataManager().getData(player);
        for (ItemStack item: items) {
            if (item.getType() == Material.AIR && data.getCollection(Material.AIR) > 0)
                return true;

            if (data.getCollection(item.getType()) == 0)
                noticeFirstCollection(player, item.getType());

            int oldLevel = data.getLevel(item.getType());

            // Spigot은 AIR를 1개, Paper는 0개로 처리하므로 따로 처리
            if (item.getType() == Material.AIR)
                data.addCollection(Material.AIR, 1);
            else
                data.addCollection(item);

            int newLevel = data.getLevel(item.getType());
            for (int i = oldLevel + 1; i <= newLevel; i++) {
                CollectionLevelUpEvent levelUpEvent = new CollectionLevelUpEvent(player, item.getType(), newLevel);
                Bukkit.getPluginManager().callEvent(levelUpEvent);

                noticeLevelUp(player, item.getType(), i);
            }
        }
        return true;
    }

    /**
     * 아이템을 수집합니다.
     * @param player 플레이어
     * @param item 수집할 아이템
     * @param route 수집 루트(커맨드, 수집 GUI 등)
     * @return 수집 성공 여부 (취소되지 않음)
     */
    public boolean collect(Player player, ItemStack item, ItemCollectEvent.Route route) {
        return collect(player, List.of(item), route);
    }

    /**
     * 아이템이 수집 가능한지 확인합니다.
     * @param item 아이템
     * @return 수집 가능 여부
     */
    public boolean isCollectable(ItemStack item) {
        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        if (itemManager.isItemOf(item, StaticItem.COLLECTION_BOOK))
            return true;
        if (!item.hasItemMeta())
            return true;
        return !item.getItemMeta().hasDisplayName();
    }

    /**
     * 발전과제를 달성했을 때의 처리입니다.
     * @param player 달성한 플레이어
     * @param advancement 달성한 발전과제
     */
    public void resolveAdvancement(Player player, Advancement advancement) {
        McolConfig config = MineCollector.getInstance().getMcolConfig();
        McolConfig.Announcement announcementConfig = config.getAnnouncement();
        McolConfig.Scoring scoringConfig = config.getScoring();

        AdvancementDisplayType type = advancement.getDisplay().getType();

        GameData data = plugin.getDataManager().getData(player);
        data.addAdvCleared(type);

        GameStatistics stats = new GameStatistics(data);
        MessageUtil.send(
                announcementConfig.getAdvancement(), player, "game.got_advancement_score",
                player.getName(), scoringConfig.getAdvancementScores().get(type), stats.getTotalScore()
        );
        for (Player p: announcementConfig.getAdvancement().resolve(player))
            SoundUtil.playFirework(p);
    }

    /**
     * Hub GUI를 열려고 시도한다.
     * @param player
     */
    public void tryOpenHubGui(Player player) {
        if (!MineCollector.getInstance().getMcolConfig().isEnabled()) {
            MessageUtil.send(player, "game.cant_open_book_now");
            SoundUtil.playFail(player);
            return;
        }

        new HubGui().openGui(player);
        SoundUtil.playPageAll(player);
    }

    private static BaseComponent getItemNameComponent(Material material) {
        try {
            return new TranslatableComponent(material.getItemTranslationKey());
        }
        catch (NoSuchMethodError e) {
            return new TextComponent(material.name());
        }
    }

    /**
     * 첫 수집 공지를 띄웁니다.
     * @param player 수집한 플레이어
     * @param material 수집한 아이템 종류
     */
    private static void noticeFirstCollection(Player player, Material material) {
        McolConfig.Announcement announcementConfig = MineCollector.getInstance().getMcolConfig().getAnnouncement();
        AnnouncementTarget target = announcementConfig.getCollection();

        BaseComponent itemComponent = GameDirector.getItemNameComponent(material);

        MessageUtil.send(target, player, "game.first_collection", player.getName(), itemComponent);
        for (Player p: announcementConfig.getCollection().resolve(player))
           SoundUtil.playHighRing(p);
    }

    /**
     * 단계 도달 축하 공지를 띄웁니다.
     * @param player 수집한 플레이어
     * @param material 수집한 아이템 종류
     * @param level 도달한 단계 수
     */
    private static void noticeLevelUp(Player player, Material material, int level) {
        McolConfig.Announcement announcementConfig = MineCollector.getInstance().getMcolConfig().getAnnouncement();

        if (level < announcementConfig.getHighLevelMinimum()) return;

        int amount = CollectionLevelUtil.getMinimumAmount(level);
        int quo = amount / 64;
        int rem = amount % 64;

        String format = switch (level) {
            case 2, 3, 4, 5     -> "§e";                // yellow
            case 6              -> "§b";                // aqua
            case 7              -> "§3";                // dark aqua
            case 8              -> "§9";                // blue
            case 9              -> "§5";                // purple
            case 10             -> "§d§l";              // pink, bold
            default /* 11+ */   -> "§x§f§f§4§4§8§8§l";  // fuchsia(#FF4488), bold
        };
        AnnouncementTarget target = announcementConfig.getHighLevelReached();
        BaseComponent itemComponent = new TranslatableComponent(material.getItemTranslationKey());

        LangManager langManager = MineCollector.getInstance().getLangManager();
        BaseComponent[] amountComponents;
        if (quo > 0 && rem > 0)
            amountComponents = langManager.translate(MessageKey.of("game.stack_and_pcs", 2), quo, rem);
        else if (quo > 0)
            amountComponents = langManager.translate(MessageKey.of("game.stack", 1), quo);
        else
            amountComponents = langManager.translate(MessageKey.of("game.pcs", 1), rem);

        MessageUtil.send(target, player, "game.level_up", format, player.getName(), itemComponent, level, amountComponents);

        if (level <= 8) {
            for (Player p: announcementConfig.getHighLevelReached().resolve(player))
                SoundUtil.playHighFirework(p);
        }
        else {
            for (Player p: announcementConfig.getHighLevelReached().resolve(player))
                SoundUtil.playLegend(p);
        }
    }
}
