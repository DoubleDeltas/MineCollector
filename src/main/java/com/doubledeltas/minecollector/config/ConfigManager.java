package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.schema.McolConfigMeta;
import com.doubledeltas.minecollector.config.schema.McolConfigSchema;
import com.doubledeltas.minecollector.config.schema.McolConfigSchema1_3;
import com.doubledeltas.minecollector.config.schema.McolConfigSchemaUnlabeled;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.version.VersionSchemaTable;
import com.doubledeltas.minecollector.yaml.Yamls;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigManager implements McolInitializable {
    private MineCollector plugin;
    private File configPath;

    private VersionSchemaTable<McolConfigSchema> schemaTable;

    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.configPath = new File(plugin.getDataFolder(), "config.yml");

        this.schemaTable = new VersionSchemaTable<>(
                plugin.getVersionManager(),
                McolConfigMeta.class,
                (self, schema) -> self.getVersionManager().parse(schema.getConfigVersion())
        );
        schemaTable.registerSchema("unlabeled", McolConfigSchemaUnlabeled.class);
        schemaTable.registerSchema("1.3",       McolConfigSchema1_3.class);
    }

    public McolConfig load() throws InvalidConfigException {
        // 파일이 없으면 기본 콘피그 파일 생성
        if (!configPath.isFile()) {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
            MessageUtil.log("기본 콘피그 파일 생성됨!");
        }

        try {
            FileReader fileReader = new FileReader(configPath);
            McolConfig config = Yamls.getConfigYaml().load(fileReader, schemaTable).convert();
            MessageUtil.log("콘피그 불러옴!");
            fileReader.close();
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
