package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.CollectionLevelUtil;
import com.doubledeltas.minecollector.yaml.Yamls;
import org.bukkit.Material;
import org.bukkit.advancement.AdvancementDisplayType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class GameData {
    private String name;
    private UUID uuid;
    private Map<String, Integer> collection;
    private Map<AdvancementDisplayType, Integer> advCleared;

    public GameData(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.collection = new LinkedHashMap<>();
        this.advCleared = new LinkedHashMap<>();
        advCleared.put(AdvancementDisplayType.TASK, 0);
        advCleared.put(AdvancementDisplayType.GOAL, 0);
        advCleared.put(AdvancementDisplayType.CHALLENGE, 0);
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
        this.advCleared = new LinkedHashMap<>();
        Map<String, Integer> advClearedStringKeyed = (Map<String, Integer>) map.get("advancement_cleared");
        advCleared.put(AdvancementDisplayType.TASK, advClearedStringKeyed.get("task"));
        advCleared.put(AdvancementDisplayType.GOAL, advClearedStringKeyed.get("goal"));
        advCleared.put(AdvancementDisplayType.CHALLENGE, advClearedStringKeyed.get("challenge"));
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

        Map<String, Object> advClearedStringKeyed = new LinkedHashMap<>();
        advClearedStringKeyed.put("task", advCleared.get(AdvancementDisplayType.TASK));
        advClearedStringKeyed.put("goal", advCleared.get(AdvancementDisplayType.GOAL));
        advClearedStringKeyed.put("challenge", advCleared.get(AdvancementDisplayType.CHALLENGE));

        map.put("advancement_cleared", advClearedStringKeyed);
        return map;
    }

    /**
     * 데이터를 YAML 포맷으로 저장합니다.
     * @param writer 데이터를 저장할 {@code Writer}
     */
    public void saveToYaml(Writer writer) {
        Yamls.getDataYaml().dump(this.toMap(), writer);
    }

    /**
     * YAML 파일로부터 데이터를 불러옵니다.
     * @param reader 데이터를 불러올 {@code Reader}
     * @return 불러온 {@link GameData} 객체
     */
    public static GameData loadFromYaml(Reader reader)
        throws YAMLException
    {
        return new GameData(Yamls.getDataYaml().loadAs(reader, Map.class));
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
     * 도감 Map을 가져옵니다. 수집 개수를 가져올 떄는 {@link GameData#getCollection(Material)}를 이용하세요.
     * @return 도감 Map
     */
    public Map<String, Integer> getCollectionMap() {
        return this.collection;
    }

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

    /**
     * 컬렉션의 단계수를 가져옵니다.
     * @param itemId 아이템 ID
     * @return 컬렉션 단계
     */
    public int getLevel(String itemId) {
        int multiple = MineCollector.getInstance().getMcolConfig().getScoring().getStackMultiple();

        if (!collection.containsKey(itemId))
            return 0;
        return CollectionLevelUtil.getLevel(collection.get(itemId));
    }

    /**
     * 컬렉션의 단계수를 가져옵니다.
     * @param material 아이템 타입
     * @return 컬렉션 단계
     */
    public int getLevel(Material material) {
        return getLevel(material.getKey().toString());
    }

    /**
     * 달성한 발전과제 수를 보여줍니다.
     * @param type 발전과제 타입
     * @return 달성한 발전과제 수
     */
    public int getAdvCleared(AdvancementDisplayType type) {
        return advCleared.get(type);
    }

    /**
     * 발전과제 달성 수를 증가시킵니다.
     * @param type 달성한 발전과제 타입
     */
    public void addAdvCleared(AdvancementDisplayType type) {
        advCleared.put(type, advCleared.get(type) + 1);
    }
}
