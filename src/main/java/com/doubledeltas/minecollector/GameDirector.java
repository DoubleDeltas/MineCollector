package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.constant.Titles;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.data.GameData;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class GameDirector {
    public static MineCollector PLUGIN = MineCollector.getPlugin();
    public static Server SERVER = PLUGIN.getServer();

    public static void collect(Player player, Collection<ItemStack> items) {

        GameData data = DataManager.getData(player);
        for (ItemStack item: items) {
            if (data.getCollection(item.getType()) == 0) {
                SERVER.broadcastMessage(Titles.MSG_PREFIX + "&e");
            }
        }
    }
}
