package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.config.chapter.AnnouncementChapter;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import com.doubledeltas.minecollector.config.chapter.GameChapter;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileReader;

public class Config {
    public static Config DEFAULT = new Config(
            false,
            ScoringChapter.DEFAULT,
            AnnouncementChapter.DEFAULT,
            GameChapter.DEFAULT,
            DBChapter.DEFAULT
    );

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

    /**
     * YAML 파일 reader에서 config를 불러온다.
     * @param reader 불러올 YAML 파일 Reader
     * @return Config 객체
     * @throws YAMLException YAML 포맷에 맞지 않음
     * @throws InvalidConfigException Config validation에 맞지 않음
     */
    public static Config loadFromYaml(FileReader reader)
        throws YAMLException, InvalidConfigException
    {
        Yaml yaml = new Yaml();
        ConfigValidator validation = new ConfigValidator(yaml.load(reader));
        if (validation.isValid())
            throw new InvalidConfigException(validation.getCauses())
        return validation.getResult();
    }
}
