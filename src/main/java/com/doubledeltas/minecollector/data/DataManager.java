package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.constant.Titles;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

/**
 * 데이터 관련 클래스
 * static class
 * @author DoubleDeltas
 */
public class DataManager {
    public static MineCollector PLUGIN = MineCollector.getPlugin();
    public static File CONFIG_PATH = new File(PLUGIN.getDataFolder(), "config.yml");
    public static File DATA_PATH = new File(PLUGIN.getDataFolder(), "data");

    private static Map<UUID, GameData> playerData = new HashMap<>();

    public static void setup() {
        try {
            if (!CONFIG_PATH.isFile()) {
                PLUGIN.getConfig().options().copyDefaults(true);
                PLUGIN.saveDefaultConfig();
            }

            if (!DATA_PATH.isDirectory()) {
                DATA_PATH.mkdirs();
            }

            // load all player data
            for (File file : DATA_PATH.listFiles()) {
                GameData data = GameData.deserialize(new FileReader(file));
                UUID uuid = data.getUuid();
                playerData.put(uuid, data);
            }
            
            MineCollector.log(DATA_PATH.listFiles().length + "개 게임 데이터 불러옴!");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addNewPlayerData(Player player) {
        playerData.put(player.getUniqueId(), new GameData(player));
    }

    /**
     * 플레이어 데이터를 불러옵니다.
     * @param player 불러올 플레이어
     * @return 성공 여부, 파일이 없거나 오류 시 false.
     */
    public static boolean loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        File dataFile = new File(PLUGIN.getDataFolder(), uuid + ".yml");
        if (!dataFile.isFile())
            return false;

        GameData data = null;
        try {
            data = GameData.deserialize(new FileReader(dataFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        playerData.put(uuid, data);
        return true;
    }

    public static boolean saveAll() {
        try {
            for (UUID uuid: playerData.keySet()) {
                File dataFile = new File(DATA_PATH, uuid + ".yml");
                FileWriter writer = new FileWriter(dataFile);
                playerData.get(uuid).serialize(writer);
            }
            MineCollector.log("게임데이터 저장됨!");
            return true;
        } catch (IOException e) {
            MineCollector.log(Level.SEVERE, "게임데이터 저장 실패!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 저장된 데이터가 있는지 봅니다.
     * @param player 플레이어
     * @return 플레이어 데이터 있는 지 여부
     */
    public static boolean hasData(Player player) {
        return playerData.containsKey(player.getUniqueId());
    }
}
