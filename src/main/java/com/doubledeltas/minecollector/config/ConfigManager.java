package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.schema.*;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.ReflectionUtil;
import com.doubledeltas.minecollector.version.*;
import com.doubledeltas.minecollector.yaml.Yamls;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigManager implements McolInitializable {
    private MineCollector plugin;
    private File configPath;

    private VersionSchemaTable<McolConfigSchema> schemaTable;
    private VersionUpdaterChain<McolConfigSchema> updaterChain;

    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.configPath = new File(plugin.getDataFolder(), "config.yml");

        VersionManager versionManager = plugin.getVersionManager();

        this.schemaTable = new VersionSchemaTable<>(
                versionManager,
                McolConfigMeta.class,
                (table, schema) -> table.getVersionManager().parse(schema.getConfigVersion())
        );
        schemaTable.registerSchema("unlabeled", McolConfigSchemaUnlabeled.class);
        schemaTable.registerSchema("1.3",       McolConfigSchema1_3.class);

        this.updaterChain = new VersionUpdaterChain<>(versionManager, UnlabeledVersion.INSTANCE);
        updaterChain.chain("1.3", new McolConfigSchema1_3.Updater());
    }

    public McolConfig load() throws InvalidConfigException {
        // 파일이 없으면 기본 콘피그 파일 생성
        if (!configPath.isFile()) {
            plugin.getConfig().options().copyDefaults(true);
            saveConfig();
            MessageUtil.log("config.default_created");
        }

        try (FileReader fileReader = new FileReader(configPath)) {
            McolConfigSchema schema = Yamls.getConfigYaml().load(fileReader, schemaTable);
            String schemaVersionName = schema.getConfigVersion();
            Version<?> schemaVersion = Version.parse(schemaVersionName);
            int versionComparison = Version.compare(Version.parse(schemaVersionName), schemaTable.getLatestVersion());
            if (versionComparison < 0) {
                schema = updaterChain.updateToLatest(schema, schemaVersion);
                saveConfig(schema, true);
                MessageUtil.log("config.updated", schemaVersionName);
            }
            else if (versionComparison > 0) {
                MessageUtil.log(Level.WARNING, "config.higher_version_warning");
            }
            MessageUtil.log("config.loaded");
            return ((CurrentMcolConfigSchema) schema).convert();
        } catch (FileNotFoundException e) {
            throw new InvalidConfigException("Could not found config.yml file", e);
        } catch (YAMLException e) {
            throw new InvalidConfigException("An error occurred while loading config.yml file", e);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final Pattern PLACEHOLDER_PATTERN
            = Pattern.compile("(\\$\\{[a-zA-Z_$][a-zA-Z0-9_$]*(\\.[a-zA-Z_$][a-zA-Z0-9_$]*)*})");

    public String replacePlaceholders(Reader rd, McolConfigSchema.PlaceholderContext context) {
        return replacePlaceholders(new BufferedReader(rd), context);
    }

    public String replacePlaceholders(BufferedReader br, McolConfigSchema.PlaceholderContext context) {
        StringBuilder sb = new StringBuilder();
        try {
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                String replacedLine = replacePlaceholders(line, context);
                sb.append(replacedLine);
                sb.append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows({IllegalStateException.class, IllegalAccessException.class})
    public String replacePlaceholders(String str, McolConfigSchema.PlaceholderContext context)
    {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(str);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String pholder = matcher.group(1); // e.g., "${foo.bar}"
            String graph = pholder.substring(2, pholder.length() - 1); // extract "foo.bar"
            Object value = ReflectionUtil.traverseGetters(context, graph);
            String replacement = value != null ? value.toString() : "null";
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * 기본 컨피그를 폴더에 저장합니다.
     */
    public void saveConfig() {
        saveConfig(McolConfigSchema.getLatestDefault(), false);
        System.out.println(McolConfigSchema.getLatestDefault());
    }

    /**
     * 컨피그를 폴더에 저장합니다.
     * @param schema 저장할 컨피그 스키마
     * @param override 기존 config.yml 파일을 덮어쓸지 여부
     */
    public void saveConfig(McolConfigSchema schema, boolean override) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (configFile.exists() && !override) {
            MessageUtil.log(Level.WARNING, "config.already_exist_warning");
            return;
        }

        InputStream is = plugin.getResource("config.yml");
        if (is == null) {
            throw new IllegalArgumentException("The embedded resource 'config.yml' cannot be found");
        }

        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        McolConfigSchema.PlaceholderContext ctx
                = new McolConfigSchema.PlaceholderContext(plugin, schema);
        String replacedConfig = replacePlaceholders(isr, ctx);

        try (Writer fw = new FileWriter(configFile)) {
            fw.write(replacedConfig);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
