package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.util.MessageUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.SelectorComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class GameDirector {
    public static void collect(Player player, Collection<ItemStack> items) {

        GameData data = DataManager.getData(player);
        for (ItemStack item: items) {
            if (item.getType() == Material.AIR && data.getCollection(Material.AIR) > 0)
                return;

            if (data.getCollection(item.getType()) == 0) {
                BaseComponent itemNameComponent = new TranslatableComponent(item.getType().getItemTranslationKey());
                itemNameComponent.setColor(ChatColor.YELLOW);

                MessageUtil.broadcastRaw(
                        new TextComponent("§e%s§a님이 ".formatted(player.getName())),
                        itemNameComponent,
                        new TextComponent(" §a아이템을 처음 수집했습니다!")
                );
            }
            data.addCollection(item);
        }
    }

    public static void collect(Player player, ItemStack item) {
        collect(player, List.of(item));
    }
}
