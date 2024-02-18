package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.mission.Mission;
import com.doubledeltas.minecollector.mission.MissionItem;
import com.doubledeltas.minecollector.util.CollectionLevelUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.advancement.AdvancementDisplayType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.Reader;
import java.io.Writer;
import java.util.*;

@Getter
public class GameData {
    private static final DumperOptions DUMPER_OPTIONS = new DumperOptions();

    private String name;
    private UUID uuid;
    private Map<String, Integer> collection;
    private Map<AdvancementDisplayType, Integer> advCleared;
    private Map<Mission, Set<MissionItem>> missionProgress;

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
        this.advCleared = new LinkedHashMap<>();
        for (AdvancementDisplayType type: AdvancementDisplayType.values()) {
            this.advCleared.put(type, 0);
        }
        this.missionProgress = new LinkedHashMap<>();
        for (Mission mission: Mission.values()) {
            this.missionProgress.put(mission, new HashSet<>());
        }
    }

    /**
     * {@code Map}객체로부터 데이터를 불러옵니다. 이 생성자는 version backward compatibility를 보장해야 합니다.
     * @param map 불러올 {@code Map} 객체
     * @see GameData#toMap()
     */
    public GameData(Map map) {
        this.name = (String) map.get("name");
        this.uuid = UUID.fromString((String) map.get("uuid"));
        this.collection = (Map<String, Integer>) map.get("collection");
        this.advCleared = new LinkedHashMap<>();
        Map<String, Integer> advClearedStringKeyed = (Map<String, Integer>) map.get("advancement_cleared");
        for (AdvancementDisplayType type: AdvancementDisplayType.values()) {
            this.advCleared.put(type, advClearedStringKeyed.get(type.name().toLowerCase(Locale.ROOT)));
        }
        // below 1.1.x
        this.missionProgress = new LinkedHashMap<>();
        Map<String, List<String>> missionProgressStringified = (Map<String, List<String>>) map.get("mission");
        for (Mission mission: Mission.values()) {
            this.missionProgress.put(mission, new LinkedHashSet<>());
        }
        if (missionProgressStringified != null) {
            for (String missionName: missionProgressStringified.keySet()) {
                Mission mission = Mission.valueOf(missionName);
                for (String itemName: missionProgressStringified.get(missionName)) {
                    MissionItem item = mission.getItemByName(name).get();
                    this.missionProgress.get(mission).add(item);
                }
            }
        }
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
        for (AdvancementDisplayType type: AdvancementDisplayType.values()) {
            advClearedStringKeyed.put(type.name().toLowerCase(Locale.ROOT), advCleared.get(type));
        }
        map.put("advancement_cleared", advClearedStringKeyed);

        Map<String, Object> missionProgressStringified = new LinkedHashMap<>();
        for (Mission mission: Mission.values()) {
            List<String> itemNames = new ArrayList<>();
            missionProgressStringified.put(mission.name(), itemNames);
            for (MissionItem item: missionProgress.get(mission)) {
                itemNames.add(item.name());
            }
        }
        map.put("mission", missionProgressStringified);

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
    public static GameData loadFromYaml(Reader reader)
        throws YAMLException
    {
        Yaml yaml = new Yaml();
        return new GameData((Map) yaml.loadAs(reader, Map.class));
    }

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

    public int getClearedMissionCount() {
        return Math.toIntExact(this.missionProgress.entrySet().stream()
                .filter(entry -> entry.getValue().size() == entry.getKey().getItems().length)
                .count());
    }
}
