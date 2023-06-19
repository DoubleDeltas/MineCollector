package com.doubledeltas.minecollector.item;

import com.doubledeltas.minecollector.item.itemCode.ItemCode;
import com.doubledeltas.minecollector.item.manager.EmbeddedItemManager;
import org.bukkit.Material;
import org.bukkit.block.data.Levelled;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 아이템 데이터를 다루는 객체입니다.
 * GUI, 무기, 도구 등 특수한 아이템을 다룰 때 이용할 수 있습니다.
 * @author DoubleDeltas
 */
public abstract class ItemManager {
    protected Map<ItemCode, ItemStack> itemStore = new HashMap<>();

    /**
     * 아이템을 가져옵니다. 이 아이템은 GUI 등에 활용될 수 있습니다.
     * 플러그인 실행 후 최초로 가져오는 아이템이라면 파일로부터 불러옵니다.
     * 아니라면 내부 변수에 저장딘 아이템을 불러옵니다.
     *
     * 경고: 이 아이템을 수정하면 안됩니다!
     * @param itemCode 아이템 경로
     * @see EmbeddedItemManager#createItem(ItemCode, Map) 아이템을 수정하려면 이 메소드를 사용하세요.
     * @return 아이템
     */
    public ItemStack getItem(ItemCode itemCode) {
        if (!itemStore.containsKey(itemCode)) {
            ItemStack item = this.loadItem(itemCode);
            itemStore.put(itemCode, item);
        }
        return itemStore.get(itemCode);
    }


    /**
     * 새로운 아이템을 만들어 가져옵니다. 이 아이템은 개인 아이템 등에 활용될 수 있습니다.
     * @param itemCode 아이템 경로
     * @param vars {@code [placeholder]}에 채울 변수
     * @see EmbeddedItemManager#getItem(ItemCode) GUI 아이콘으로는 이것을 사용해보세요!
     * @return 아이템
     */
    public ItemStack createItem(ItemCode itemCode, Map<String, ?> vars) {
        ItemStack item = this.getItem(itemCode).clone();
        ItemMeta meta = item.getItemMeta();

        for (Map.Entry<String, ?> entry: vars.entrySet()) {
            String key = entry.getKey();
            Object valueObj = entry.getValue();
            String value;
            if (List.of(Double.NaN, Float.NaN).contains(valueObj))
                value = "---";
            else if (List.of(Double.POSITIVE_INFINITY, Float.POSITIVE_INFINITY).contains(valueObj))
                value = "∞";
            else if (List.of(Double.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY).contains(valueObj))
                value = "-∞";
            else
                value = valueObj.toString();

            if (meta.hasDisplayName()) {
                meta.setDisplayName(meta.getDisplayName().replace("[" + key + "]", value));
            }

            if (meta.hasLore()) {
                List<String> newLore = meta.getLore().stream()
                        .map(lore -> lore.replace("[" + key + "]", value))
                        .collect(Collectors.toList());
                meta.setLore(newLore);
            }

            if (key.equals("missionCleared")) {
                // set light block level
                BlockDataMeta blockDataMeta = (BlockDataMeta) meta;
                Levelled levelled = (Levelled) blockDataMeta.getBlockData(Material.LIGHT);
                levelled.setLevel(Integer.parseInt(value));
                blockDataMeta.setBlockData(levelled);
            }

        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * 새로운 아이템을 만들어 가져옵니다. 이 아이템은 개인 아이템 등에 활용될 수 있습니다.
     * @param itemCode 아이템 경로
     * @see EmbeddedItemManager#getItem(ItemCode) GUI 아이콘으로는 이것을 사용해보세요!
     * @return 아이템
     */
    public ItemStack createItem(ItemCode itemCode) {
        return this.createItem(itemCode, Map.of());
    }

    /**
     * 아이템을 불러옵니다.
     * @param itemCode 아이템 경로
     * @return 불러온 아이템
     */
    protected abstract ItemStack loadItem(ItemCode itemCode);
}
