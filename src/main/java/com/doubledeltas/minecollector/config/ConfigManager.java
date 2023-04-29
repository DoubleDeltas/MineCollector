package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SpaceToCamelPropertyUtils;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {
    public static Constructor YAML_CONSTRUCTOR;
    public static File CONFIG_PATH = new File(MineCollector.getInstance().getDataFolder(), "config.yml");

    static {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setEnumCaseSensitive(false);
        YAML_CONSTRUCTOR = new Constructor(McolConfig.class, loaderOptions);
        YAML_CONSTRUCTOR.setPropertyUtils(new SpaceToCamelPropertyUtils());
    }

    public static McolConfig load() throws InvalidConfigException {
        // 파일이 없으면 기본 콘피그 파일 생성
        if (!CONFIG_PATH.isFile()) {
            MineCollector.getInstance().getConfig().options().copyDefaults(true);
            MineCollector.getInstance().saveDefaultConfig();
            MessageUtil.log("기본 콘피그 파일 생성됨!");
        }

        Yaml yaml = new Yaml(YAML_CONSTRUCTOR);
        yaml.setBeanAccess(BeanAccess.FIELD);
        try {
            FileReader reader = new FileReader(CONFIG_PATH);
            McolConfig config = yaml.load(reader);
            validate(config);
            MessageUtil.log("콘피그 불러옴!");
            reader.close();
            return config;
        } catch (FileNotFoundException e) {
            throw new InvalidConfigException("config.yml 파일을 찾을 수 없습니다!", e);
        } catch (YAMLException e) {
            throw new InvalidConfigException("config.yml 파일을 읽어들이던 중 오류가 발생했습니다!", e);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void validate(McolConfig config) throws InvalidConfigException
    {
        if (config.getAnnouncement().getHighLevelMinimum() < 2)
            throw new InvalidConfigException("announcement-high level minimum은 2 이상의 정수여야 합니다!");

        if (config.getScoring().getStackMultiple() < 2)
            throw new InvalidConfigException("scoring-stack multiple은 2 이상의 정수여야 합니다!");

        if (config.getDb().getAutosavePeriod() <= 0)
            throw new InvalidConfigException("db-autosave period는 양의 정수여야 합니다!");
    }
}
