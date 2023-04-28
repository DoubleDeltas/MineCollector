package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Config 매니저 클래스
 * @author DoubleDeltas
 */
public class ConfigManager {
    public static MineCollector PLUGIN = MineCollector.getPlugin();
    public static File CONFIG_PATH = new File(PLUGIN.getDataFolder(), "config.yml");

    public static Config config;

    public static void loadConfig()
        throws FileNotFoundException, YAMLException, InvalidConfigException
    {
        if (!CONFIG_PATH.isFile()) {
            PLUGIN.getConfig().options().copyDefaults(true);
            PLUGIN.saveDefaultConfig();
        }

        config = Config.loadFromYaml(new FileReader(CONFIG_PATH));

        MessageUtil.log("콘피그 불러옴!");
    }
}
