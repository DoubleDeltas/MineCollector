package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.schema.McolConfigMeta;
import com.doubledeltas.minecollector.config.schema.McolConfigSchema;
import com.doubledeltas.minecollector.config.schema.McolConfigSchema1_3;
import com.doubledeltas.minecollector.config.schema.McolConfigSchemaUnlabeled;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.version.Version;
import com.doubledeltas.minecollector.version.VersionSchemaTable;
import com.doubledeltas.minecollector.yaml.Yamls;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

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
                (table, schema) -> table.getVersionManager().parse(schema.getConfigVersion())
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
            Version<?> configVersion = config.getConfigVersion();
            int versionComparison = Version.compare(configVersion, schemaTable.getLatestVersion());
            if (versionComparison < 0) {
                MessageUtil.log("오래된 버전(%s)의 콘피그를 현재 버전에 맞게 업데이트했습니다!".formatted(configVersion));
            }
            else if (versionComparison > 0) {
                MessageUtil.log(Level.WARNING, "콘피그 버전이 현재 최신 버전보다 높습니다! 더 높은 버전의 플러그인을 쓴 적이 있거나 config version을 변경하셨나요?");
            }
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
