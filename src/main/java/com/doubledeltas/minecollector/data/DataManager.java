package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;

/**
 * 데이터 매니저 클래스
 * @author DoubleDeltas
 */
public class DataManager implements McolInitializable {
    public MineCollector plugin;
    public File dataPath;

    private final Map<UUID, GameData> playerData = new HashMap<>();

    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.dataPath = new File(plugin.getDataFolder(), "data");
    }

    public void loadData() {
        try {
            if (!dataPath.isDirectory()) {
                dataPath.mkdirs();
            }

            // 접속중인 플레이어의 데이터 파일이 없으면 만듦
            for (Player player: MineCollector.getInstance().getServer().getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                File dataFile = new File(dataPath, uuid + ".yml");

                if (!dataFile.exists())
                    save(new GameData(player));
            }

            // convert all player data
            for (File file : dataPath.listFiles()) {
                GameData data = GameData.loadFromYaml(new FileReader(file));
                UUID uuid = data.getUuid();
                playerData.put(uuid, data);
            }

            MessageUtil.logRaw(dataPath.listFiles().length + "개 게임 데이터 불러옴!");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addNewPlayerData(Player player) {
        playerData.put(player.getUniqueId(), new GameData(player));
    }

    /**
     * 플레이어 데이터를 불러옵니다.
     * @param player 불러올 플레이어
     * @return 성공 여부, 파일이 없거나 오류 시 false.
     */
    public boolean loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        File dataFile = new File(plugin.getDataFolder(), uuid + ".yml");
        if (!dataFile.isFile())
            return false;

        GameData data = null;
        try {
            data = GameData.loadFromYaml(new FileReader(dataFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        playerData.put(uuid, data);
        return true;
    }

    /**
     * 게임 데이터를 저장합니다.
     * @param data 저장할 게임 데이터
     * @return 저장 성공 여부
     */
    public boolean save(GameData data) {
        try {
            File dataFile = new File(dataPath, data.getUuid() + ".yml");
            FileWriter writer = new FileWriter(dataFile);
            data.saveToYaml(writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 모든 게임 데이터를 저장합니다.
     * @return 모든 데이터 저장 성공 여부
     */
    public boolean saveAll() {
        for (GameData data: playerData.values()) {
            boolean result = save(data);
            if (!result) {
                MessageUtil.logRaw(Level.SEVERE, "게임데이터 저장 실패!");
                return false;
            }
        }
        return true;
    }

    /**
     * 저장된 데이터가 있는지 봅니다.
     * @param player 플레이어
     * @return 플레이어 데이터 있는 지 여부
     */
    public boolean hasData(Player player) {
        return playerData.containsKey(player.getUniqueId());
    }

    /**
     * 플레이어의 데이터를 가져옵니다.
     * @param player 플레이어
     * @return 플레이어의 데이터
     */
    public GameData getData(Player player) {
        return playerData.get(player.getUniqueId());
    }

    /**
     * 특정 기준으로 Top 10을 불러옵니다.
     * 인덱스 0은 {@code null}이고, 1부터 10까지 각 순위권에 해당합니다.
     * @param keyFunc 키 함수
     * @param <K> 키 함수 반환 타입
     * @return Top 10 리스트
     */
    public <K extends Comparable<K>> List<GameData> getTop10(Function<GameData, K> keyFunc) {
        List<GameData> top10 = new ArrayList<>();
        top10.add(null);
        top10.addAll(
                playerData.values().stream()
                        .sorted(Comparator.comparing(keyFunc).reversed())
                        .limit(10)
                        .toList()
        );
        return top10;
    }
}
