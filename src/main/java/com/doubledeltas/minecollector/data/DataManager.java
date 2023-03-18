package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        Path folder = MineCollector.getPlugin().getDataFolder().toPath();

        try {
            configFile = folder.resolve(CONFIG_FILENAME).toFile();
            config = YamlConfiguration.loadConfiguration(configFile);
            if (!configFile.exists() || configFile.length() == 0) {
                MineCollector.log("asdf");
                config.options().copyDefaults(true);
                config.save(configFile);
            }
            MineCollector.getPlugin().saveDefaultConfig();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
