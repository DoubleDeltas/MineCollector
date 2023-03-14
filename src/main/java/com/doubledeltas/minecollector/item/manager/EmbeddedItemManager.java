package com.doubledeltas.minecollector.item.manager;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.item.ItemManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/**
 * 플러그인 내부에 저장된 아이템 데이터 파일을 다루는 객체입니다.
 * GUI, 무기, 도구 등 특수한 아이템을 다룰 때 이용할 수 있습니다.
 * @author DoubleDeltas
 */
public class EmbeddedItemManager extends ItemManager {
    public JavaPlugin plugin;

    public EmbeddedItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * 아이템을 불러옵니다.
     * @param itemId 아이템 경로
     * @return 불러온 아이템
     */
    @Override
    protected ItemStack loadItem(String itemId) {
        Yaml yaml = new Yaml();
        Map<String, Object> itemMap = yaml.load(plugin.getResource("item/%s.yml".formatted(itemId)));
        MineCollector.log("loaded item - " + itemId + ":");
        MineCollector.log(itemMap.toString());
        return ItemStack.deserialize(itemMap);
    }
}
