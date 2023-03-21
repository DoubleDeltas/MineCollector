package com.doubledeltas.minecollector.data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameData {
    String name;
    UUID uuid;
    Map<Material, Integer> collection;

    public GameData(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        collection = new HashMap<>();
    }

    public String getName() { return name; }
    public UUID getUuid() { return uuid; }
    public Map<Material, Integer> getCollection() { return collection; }

    public String serialize() {
        Yaml yaml = new Yaml();
        return yaml.dump(this);
    }

    public static GameData deserialize(Reader reader) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(reader, GameData.class);
    }
}
