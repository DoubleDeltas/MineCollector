package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.chapter.AnnouncementChapter;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import com.doubledeltas.minecollector.config.chapter.GameChapter;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.checkerframework.checker.units.qual.C;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {
    public static LoaderOptions LOADER_OPTIONS = new LoaderOptions();
    public static File CONFIG_FILE = new File(MineCollector.getPlugin().getDataFolder(), "config.yml");

    private boolean             enabled;
    private ScoringChapter      scoring;
    private AnnouncementChapter announcement;
    private GameChapter         game;
    private DBChapter           db;

    public Config(
            boolean             enabled,
            ScoringChapter      scoring,
            AnnouncementChapter announcement,
            GameChapter         game,
            DBChapter           db
    ) {
        this.enabled = enabled;
        this.scoring = scoring;
        this.announcement = announcement;
        this.game = game;
        this.db = db;
    }

    public static Config load() {
        Yaml yaml = new Yaml(new Constructor(Config.class, LOADER_OPTIONS));
        try {
            Config config = yaml.load(new FileReader(CONFIG_FILE));
            MessageUtil.log("콘피그 불러옴!");
            return config;
        } catch (FileNotFoundException e) {

            return null;
        }
    }

    public boolean enabled() {
        return enabled;
    }

    public ScoringChapter scoring() {
        return scoring;
    }

    public AnnouncementChapter announcement() {
        return announcement;
    }

    public GameChapter game() {
        return game;
    }

    public DBChapter db() {
        return db;
    }
}
