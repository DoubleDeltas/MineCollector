package com.doubledeltas.minecollector.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

/**
 * 플러그인 내부에 저장된 아이템 데이터 파일을 다루는 객체입니다.
 * GUI, 무기, 도구 등 특수한 아이템을 다룰 때 이용할 수 있습니다.
 * @author DoubleDeltas
 */
public class EmbeddedItemManager {
    public JavaPlugin plugin;
    public Map<String, ItemStack> itemStore = new HashMap<>();

    public EmbeddedItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * 아이템을 가져옵니다. 이 아이템은 GUI 등에 활용될 수 있습니다.
     * 플러그인 실행 후 최초로 가져오는 아이템이라면 파일로부터 불러옵니다.
     * 아니라면 내부 변수에 저장딘 아이템을 불러옵니다.
     *
     * 경고: 이 아이템을 수정하면 안됩니다!
     * @param itemPath 아이템 경로
     * @see EmbeddedItemManager#createItem(String) 아이템을 수정하려면 이 메소드를 사용하세요.
     * @return 아이템
     */
    public ItemStack getItem(String itemPath) {
        if (!itemStore.containsKey(itemPath)) {
            ItemStack item = this.loadItem(itemPath);
            itemStore.put(itemPath, item);
        }
        return itemStore.get(itemPath);
    }

    /**
     * 새로운 아이템을 만들어 가져옵니다. 이 아이템은 개인 아이템 등에 활용될 수 있습니다.
     * @param itemPath 아이템 경로
     * @see EmbeddedItemManager#getItem(String) GUI 아이콘으로는 이것을 사용해보세요!
     * @return 아이템
     */
    public ItemStack createItem(String itemPath) {
        return this.getItem(itemPath).clone();
    }

    /**
     * 아이템을 불러옵니다.
     * @param itemPath 아이템 경로
     * @return 불러온 아이템
     */
    private ItemStack loadItem(String itemPath) {
        Yaml yaml = new Yaml();
        Map<String, Object> itemMap = yaml.load(plugin.getResource("item/%s.yml".formatted(itemPath)));
        return ItemStack.deserialize(itemMap);
    }
}
