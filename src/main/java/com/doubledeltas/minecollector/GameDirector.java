package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.data.GameData;
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
                MineCollector.broadcast(
                        "§e%s§a님이 §e%s §a아이템을 처음 수집했습니다!"
                                .formatted(player.getName(), item.getType().toString())
                );
            }
            data.addCollection(item);
        }
    }

    public static void collect(Player player, ItemStack item) {
        collect(player, List.of(item));
    }
}
