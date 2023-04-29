package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.chapter.AnnouncementChapter;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import com.doubledeltas.minecollector.config.chapter.GameChapter;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;
import com.doubledeltas.minecollector.util.MessageUtil;
import lombok.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Data
public class Config {
    public static LoaderOptions LOADER_OPTIONS = new LoaderOptions();
    public static Representer REPRESENTER = new Representer(new DumperOptions());
    public static File CONFIG_PATH = new File(MineCollector.getPlugin().getDataFolder(), "config.yml");

    static {
        PropertyUtils propUtils = new PropertyUtils();
        propUtils.setAllowReadOnlyProperties(true);
        REPRESENTER.setPropertyUtils(propUtils);
    }

    private boolean             enabled;
    private ScoringChapter      scoring;
    private AnnouncementChapter announcement;
    private GameChapter         game;
    private DBChapter           db;

    public static Config load() {
        // 파일이 없으면 기본 콘피그 파일 생성
        if (!CONFIG_PATH.isFile()) {
            MineCollector.getPlugin().getConfig().options().copyDefaults(true);
            MineCollector.getPlugin().saveDefaultConfig();
            MessageUtil.log("기본 콘피그 파일 생성됨!");
        }

        Yaml yaml = new Yaml(new Constructor(Config.class, LOADER_OPTIONS), REPRESENTER);
        try {
            Config config = yaml.load(new FileReader(CONFIG_PATH));
            MessageUtil.log("콘피그 불러옴!");
            return config;
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
