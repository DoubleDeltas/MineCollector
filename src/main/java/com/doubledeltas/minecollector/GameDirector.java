package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.config.chapter.AnnouncementChapter;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.data.GameStatistics;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementDisplayType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class GameDirector {
    public static ChatColor[] LEVEL_UP_MSG_COLORS = new ChatColor[] {
            null,
            null,
            ChatColor.YELLOW,
            ChatColor.YELLOW,
            ChatColor.YELLOW,
            ChatColor.YELLOW,
            ChatColor.AQUA,
            ChatColor.DARK_AQUA,
            ChatColor.BLUE,
            ChatColor.DARK_PURPLE,
            ChatColor.LIGHT_PURPLE,
            ChatColor.of("#ff4488")
    };
    private static final McolConfig CONFIG = MineCollector.getInstance().getMcolConfig();
    private static final ScoringChapter SCORING_CONFIG = CONFIG.getScoring();
    private static final AnnouncementChapter ANNOUNCEMENT_CONFIG = CONFIG.getAnnouncement();

    /**
     * 아이템을 수집합니다.
     * @param player 플레이어
     * @param items 수집할 아이템들
     */
    public static void collect(Player player, Collection<ItemStack> items) {

        GameData data = DataManager.getData(player);
        for (ItemStack item: items) {
            if (item.getType() == Material.AIR && data.getCollection(Material.AIR) > 0)
                return;

            if (data.getCollection(item.getType()) == 0)
                noticeFirstCollection(player, item.getType());

            int oldLevel = data.getLevel(item.getType());

            data.addCollection(item);

            int newLevel = data.getLevel(item.getType());
            for (int i = oldLevel + 1; i <= newLevel; i++) {
                noticeLevelUp(player, item.getType(), i);
            }
        }
    }

    /**
     * 아이템을 수집합니다.
     * @param player 플레이어
     * @param item 수집할 아이템
     */
    public static void collect(Player player, ItemStack item) {
        collect(player, List.of(item));
    }

    /**
     * 아이템이 수집 가능한지 확인합니다.
     * @param item 아이템
     * @return 수집 가능 여부
     */
    public static boolean isCollectable(ItemStack item) {
        ItemManager itemManager = MineCollector.getInstance().getItemManager();

        if (itemManager.getItem(StaticItem.COLLECTION_BOOK).equals(item))
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
    public static void resolveAdvancement(Player player, Advancement advancement) {
        assert advancement.getDisplay() != null;
        AdvancementDisplayType type = advancement.getDisplay().getType();

        GameData data = DataManager.getData(player);
        data.addAdvCleared(type);

        GameStatistics stats = new GameStatistics(data);
        MessageUtil.send(ANNOUNCEMENT_CONFIG.getAdvancement(), player,
                "§f발전과제 점수 §b§l%s§f점을 얻었습니다. (현재 §e%s§f점)"
                        .formatted(SCORING_CONFIG.getAdvancementScores().get(type), stats.getTotalScore())
        );
        for (Player p: ANNOUNCEMENT_CONFIG.getAdvancement().resolve(player))
            SoundUtil.playFirework(p);
    }

    /**
     * 첫 수집 공지를 띄웁니다.
     * @param target 수집한 플레이어
     * @param material 수집한 아이템 종류
     */
    private static void noticeFirstCollection(Player target, Material material) {
        BaseComponent itemNameComponent = new TranslatableComponent(material.getItemTranslationKey());
        itemNameComponent.setColor(ChatColor.YELLOW);

        MessageUtil.sendRaw(ANNOUNCEMENT_CONFIG.getCollection(), target, List.of(
                new TextComponent("§e%s§a님이 ".formatted(target.getName())),
                itemNameComponent,
                new TextComponent(" §a아이템을 처음 수집했습니다!")
        ));
        for (Player p: ANNOUNCEMENT_CONFIG.getCollection().resolve(target))
           SoundUtil.playHighRing(p);
    }

    /**
     * 단계 도달 축하 공지를 띄웁니다.
     * @param target 수집한 플레이어
     * @param material 수집한 아이템 종류
     * @param level 도달한 단계 수
     */
    private static void noticeLevelUp(Player target, Material material, int level) {
        if (level < ANNOUNCEMENT_CONFIG.getHighLevelMinimum()) return;

        ChatColor color = LEVEL_UP_MSG_COLORS[Math.min(level, LEVEL_UP_MSG_COLORS.length - 1)];
        int amount = (int) Math.pow(ANNOUNCEMENT_CONFIG.getHighLevelMinimum(), level - 1);

        BaseComponent[] components = new BaseComponent[4];
        components[0] = new TextComponent("님의 ");
        components[1] = new TextComponent(" 컬렉션이 ");
        components[2] = new TextComponent(level + "단계");
        components[3] = new TextComponent(
                (amount < 64)
                ? "에 도달했습니다! (%d개)".formatted(amount)
                : "에 도달했습니다! (%d셋)".formatted(amount / 64)
        );

        components[2].setBold(true);
        for (BaseComponent component: components) {
            component.setColor(color);
            if (level >= 10)
                component.setBold(true);
        }

        BaseComponent itemNameComponent = new TranslatableComponent(material.getItemTranslationKey());
        itemNameComponent.setColor(ChatColor.YELLOW);

        MessageUtil.sendRaw(ANNOUNCEMENT_CONFIG.getHighLevelReached(), target, List.of(
                new TextComponent("§e%s".formatted(target.getName())),
                components[0],
                itemNameComponent,
                components[1],
                components[2],
                components[3]
        ));
        if (level <= 8) {
            for (Player p: ANNOUNCEMENT_CONFIG.getCollection().resolve(target))
                SoundUtil.playHighFirework(p);
        }
        else {
            for (Player p: ANNOUNCEMENT_CONFIG.getCollection().resolve(target))
                SoundUtil.playLegend(p);
        }
    }
}
