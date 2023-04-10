package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class GameDirector {
    public static ChatColor[] LEVEL_UP_MSG_COLORS = new ChatColor[] {
            ChatColor.AQUA,
            ChatColor.DARK_AQUA,
            ChatColor.BLUE,
            ChatColor.DARK_PURPLE,
            ChatColor.LIGHT_PURPLE,
            ChatColor.of("#ff4488")
    };

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

    public static void collect(Player player, ItemStack item) {
        collect(player, List.of(item));
    }

    public static boolean isCollectable(ItemStack item) {
        ItemManager itemManager = MineCollector.getPlugin().getItemManager();

        if (itemManager.getItem(StaticItem.COLLECTION_BOOK).equals(item))
            return true;
        if (!item.hasItemMeta())
            return true;
        return !item.getItemMeta().hasDisplayName();
    }

    private static void noticeFirstCollection(Player target, Material material) {
        BaseComponent itemNameComponent = new TranslatableComponent(material.getItemTranslationKey());
        itemNameComponent.setColor(ChatColor.YELLOW);

        MessageUtil.broadcastRaw(
                new TextComponent("§e%s§a님이 ".formatted(target.getName())),
                itemNameComponent,
                new TextComponent(" §a아이템을 처음 수집했습니다!")
        );
        for (Player p: MineCollector.getPlugin().getServer().getOnlinePlayers())
           SoundUtil.playHighRing(p);
    }

    private static void noticeLevelUp(Player target, Material material, int level) {
        if (level < 5) return;

        ChatColor color = LEVEL_UP_MSG_COLORS[Math.min(level - 5, LEVEL_UP_MSG_COLORS.length - 1)];
        int amountInSet = 1 << (2 * level - 8);

        BaseComponent[] components = new BaseComponent[4];
        components[0] = new TextComponent("님의 ");
        components[1] = new TextComponent(" 컬렉션이 ");
        components[2] = new TextComponent(level + "단계");
        components[3] = new TextComponent("에 도달했습니다! (%d셋)".formatted(amountInSet));

        components[2].setBold(true);
        for (BaseComponent component: components) {
            component.setColor(color);
            if (level >= 10)
                component.setBold(true);
        }

        BaseComponent itemNameComponent = new TranslatableComponent(material.getItemTranslationKey());
        itemNameComponent.setColor(ChatColor.YELLOW);

        MessageUtil.broadcastRaw(
                new TextComponent("§e%s".formatted(target.getName())),
                components[0],
                itemNameComponent,
                components[1],
                components[2],
                components[3]
        );
        if (level <= 8) {
            for (Player p: MineCollector.getPlugin().getServer().getOnlinePlayers())
                SoundUtil.playHighFirework(p);
        }
        else {
            for (Player p: MineCollector.getPlugin().getServer().getOnlinePlayers())
                SoundUtil.playLegend(p);
        }

    }
}
