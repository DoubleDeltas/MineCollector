package com.doubledeltas.minecollector.item;

import com.doubledeltas.minecollector.item.manager.EmbeddedItemManager;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * 아이템 데이터를 다루는 객체입니다.
 * GUI, 무기, 도구 등 특수한 아이템을 다룰 때 이용할 수 있습니다.
 * @author DoubleDeltas
 */
public abstract class ItemManager {
    protected Map<String, ItemStack> itemStore = new HashMap<>();

    /**
     * 아이템을 가져옵니다. 이 아이템은 GUI 등에 활용될 수 있습니다.
     * 플러그인 실행 후 최초로 가져오는 아이템이라면 파일로부터 불러옵니다.
     * 아니라면 내부 변수에 저장딘 아이템을 불러옵니다.
     *
     * 경고: 이 아이템을 수정하면 안됩니다!
     * @param itemId 아이템 경로
     * @see EmbeddedItemManager#createItem(String) 아이템을 수정하려면 이 메소드를 사용하세요.
     * @return 아이템
     */
    public ItemStack getItem(String itemId) {
        if (!itemStore.containsKey(itemId)) {
            ItemStack item = this.loadItem(itemId);
            itemStore.put(itemId, item);
        }
        return itemStore.get(itemId);
    }


    /**
     * 새로운 아이템을 만들어 가져옵니다. 이 아이템은 개인 아이템 등에 활용될 수 있습니다.
     * @param itemId 아이템 경로
     * @see EmbeddedItemManager#getItem(String) GUI 아이콘으로는 이것을 사용해보세요!
     * @return 아이템
     */
    public ItemStack createItem(String itemId) {
        return this.getItem(itemId).clone();
    }

    /**
     * 아이템을 불러옵니다.
     * @param itemId 아이템 경로
     * @return 불러온 아이템
     */
    protected abstract ItemStack loadItem(String itemId);
}
