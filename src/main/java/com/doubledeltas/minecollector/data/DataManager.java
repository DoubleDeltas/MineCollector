package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Path;

/**
 * 데이터 관련 클래스
 * static class
 * @author DoubleDeltas
 */
public class DataManager {
    public static String CONFIG_FILENAME = "config.yml";

    public static File configFile;
    public static FileConfiguration config;

    public static void loadConfig() {
        MineCollector plugin = MineCollector.getPlugin();
        Path folder = MineCollector.getPlugin().getDataFolder().toPath();

        configFile = folder.resolve(CONFIG_FILENAME).toFile();
        if (configFile.length() == 0) {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        plugin.saveDefaultConfig();
    }
}
