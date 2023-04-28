package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

/**
 * Config 매니저 클래스
 * @author DoubleDeltas
 */
public class ConfigManager {
    public static MineCollector PLUGIN = MineCollector.getPlugin();
    public static File DEFAULT_CONFIG_FILE = new File(PLUGIN.getDataFolder(), "config.yml");
    public static InputStream CONFIG_SCHEMA_STREAM = PLUGIN.getResource("config-schema.yml");

    public static Config config;

    public static void loadConfig()
        throws FileNotFoundException, YAMLException, InvalidConfigException
    {
        if (!DEFAULT_CONFIG_FILE.isFile()) {
            PLUGIN.getConfig().options().copyDefaults(true);
            PLUGIN.saveDefaultConfig();
        }

        config = Config.loadFromYaml(new FileReader(DEFAULT_CONFIG_FILE));

        MessageUtil.log("콘피그 불러옴!");
    }
}
