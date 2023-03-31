package com.doubledeltas.minecollector.data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class GameData implements Serializable {
    private static final DumperOptions DUMPER_OPTIONS = new DumperOptions();

    String name;
    UUID uuid;
    Map<Material, Integer> collection;
    boolean isAirCollected;

    static {
        DUMPER_OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        DUMPER_OPTIONS.setCanonical(false);
        DUMPER_OPTIONS.setExplicitStart(false);
        DUMPER_OPTIONS.setExplicitEnd(false);
    }

    public GameData(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.collection = new HashMap<>();
        this.isAirCollected = false;
    }

    public GameData(Map map) {
        this.name = (String) map.get("name");
        this.uuid = UUID.fromString((String) map.get("uuid"));
        this.collection = (Map<Material, Integer>) map.get("collection");
        this.isAirCollected = (boolean) map.get("isAirCollected");
    }

    public String getName() { return name; }
    public UUID getUuid() { return uuid; }
    public Map<Material, Integer> getCollection() { return collection; }
    public boolean isAirCollected() { return isAirCollected; }

    private Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap();
        map.put("name", name);
        map.put("uuid", uuid.toString());
        map.put("collection", collection);
        map.put("isAirCollected", isAirCollected);
        return map;
    }

    public void serialize(Writer writer) {
        Yaml yaml = new Yaml(DUMPER_OPTIONS);
        yaml.dump(this.toMap(), writer);
    }

    public static GameData deserialize(Reader reader) {
        Yaml yaml = new Yaml();
        return new GameData(yaml.loadAs(reader, Map.class));
    }
}
