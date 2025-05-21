package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.yaml.Yamls;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {
    public static File CONFIG_PATH = new File(MineCollector.getInstance().getDataFolder(), "config.yml");

    @SuppressWarnings("unchecked")
    public McolConfig load() throws InvalidConfigException {
        // 파일이 없으면 기본 콘피그 파일 생성
        if (!CONFIG_PATH.isFile()) {
            MineCollector.getInstance().getConfig().options().copyDefaults(true);
            MineCollector.getInstance().saveDefaultConfig();
            MessageUtil.log("기본 콘피그 파일 생성됨!");
        }

        try {
            FileReader reader = new FileReader(CONFIG_PATH);
            McolConfig config = ((McolConfigYaml) Yamls.getConfigYaml().load(reader)).convert();
            MessageUtil.log("콘피그 불러옴!");
            // todo: test
            MessageUtil.log(config.toString());
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
}
