package com.doubledeltas.minecollector.data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameData implements Serializable {
    private static final DumperOptions DUMPER_OPTIONS = new DumperOptions();

    String name;
    UUID uuid;
    Map<String, Integer> collection;

    static {
        DUMPER_OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        DUMPER_OPTIONS.setCanonical(false);
        DUMPER_OPTIONS.setExplicitStart(false);
        DUMPER_OPTIONS.setExplicitEnd(false);
    }

    public GameData(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.collection = new LinkedHashMap<>();
    }

    /**
     * {@code Map}객체로부터 데이터를 불러옵니다.
     * @param map 불러올 {@code Map} 객체
     * @see GameData#toMap()
     */
    public GameData(Map map) {
        this.name = (String) map.get("name");
        this.uuid = UUID.fromString((String) map.get("uuid"));
        this.collection = (Map<String, Integer>) map.get("collection");
    }

    /**
     * 데이터를 {@code Map} 객체로 만듭니다.
     * @return 맵
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        map.put("uuid", uuid.toString());
        map.put("collection", collection);
        return map;
    }

    /**
     * 데이터를 YAML 포맷으로 저장합니다.
     * @param writer 데이터를 저장할 {@code Writer}
     */
    public void saveToYaml(Writer writer) {
        Yaml yaml = new Yaml(DUMPER_OPTIONS);
        yaml.dump(this.toMap(), writer);
    }

    /**
     * YAML 파일로부터 데이터를 불러옵니다.
     * @param reader 데이터를 불러올 {@code Reader}
     * @return 불러온 {@link GameData} 객체
     */
    public static GameData loadFromYaml(Reader reader) {
        Yaml yaml = new Yaml();
        return new GameData(yaml.loadAs(reader, Map.class));
    }

    /**
     * 데이터 주인의 이름(닉네임)을 가져옵니다.
     * @return 이름
     */
    public String getName() { return name; }

    /**
     * 데이터 주인의 {@link UUID}를 가져옵니다.
     * @return {@link UUID} 객체
     */
    public UUID getUuid() { return uuid; }

    public void setName(String name) { this.name = name; }

    /**
     * 도감의 수집 개수를 가져옵니다.
     * @param material 아이템 타입
     * @return 수집 개수, 없으면 0.
     */
    public int getCollection(Material material) {
        String itemId = material.getKey().toString();
        if (!collection.containsKey(itemId))
            return 0;
        return collection.get(itemId);
    }

    /**
     * 도감의 수집 개수를 증가시킵니다.
     * @param itemId 아이템 ID
     * @param amount 증가시킬 개수
     */
    public void addCollection(String itemId, int amount) {
        if (collection.containsKey(itemId))
            collection.put(itemId, collection.get(itemId) + amount);
        else
            collection.put(itemId, amount);
    }

    /**
     * 도감의 수집 개수를 증가시킵니다.
     * @param material 아이템 타입
     * @param amount 증가시킬 개수
     */
    public void addCollection(Material material, int amount) {
        addCollection(material.getKey().toString(), amount);
    }

    /**
     * 도감의 수집 개수를 증가시킵니다.
     * @param itemStack 아이템 스택
     */
    public void addCollection(ItemStack itemStack) {
        addCollection(itemStack.getType(), itemStack.getAmount());
    }
}