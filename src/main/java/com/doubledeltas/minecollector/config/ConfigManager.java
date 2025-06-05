package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.schema.McolConfigMeta;
import com.doubledeltas.minecollector.config.schema.McolConfigSchema;
import com.doubledeltas.minecollector.config.schema.McolConfigSchema1_3;
import com.doubledeltas.minecollector.config.schema.McolConfigSchemaUnlabeled;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.ReflectionUtil;
import com.doubledeltas.minecollector.version.VersionSchemaTable;
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
            saveDefaultConfig();
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

    public void saveDefaultConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (configFile.exists()) {
            plugin.getLogger().log(Level.WARNING, "Could not save config.yml because config.yml already exists.");
            return;
        }

        InputStream is = plugin.getResource("config.yml");
        if (is == null) {
            throw new IllegalArgumentException("The embedded resource 'config.yml' cannot be found");
        }

        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        McolConfigSchema.PlaceholderContext ctx
                = new McolConfigSchema.PlaceholderContext(plugin, McolConfigSchema.getLatestDefault());
        String replacedConfig = replacePlaceholders(isr, ctx);

        try (Writer fw = new FileWriter(configFile)) {
            fw.write(replacedConfig);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save replaced config.yml", ex);
        }
    }
}
