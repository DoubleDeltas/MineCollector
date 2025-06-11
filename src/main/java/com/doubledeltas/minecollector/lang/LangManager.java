package com.doubledeltas.minecollector.lang;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangManager implements McolInitializable {
    private static final String DEFAULT_LANG = "ko_KR";

    private MineCollector plugin;
    private File langFolder;
    private File defaultLangFile;
    @Getter
    private Properties langProperties;
    private final Properties defaultProperties;

    public LangManager() {
        this.defaultProperties = new Properties();
    }

    @Override
    @SneakyThrows(IOException.class)
    public void init(MineCollector plugin) {
        this.plugin = plugin;
        this.langFolder = new File(plugin.getDataFolder(), "lang");
        this.defaultLangFile = new File(langFolder, DEFAULT_LANG);
        defaultProperties.load(plugin.getResource("lang/" + DEFAULT_LANG + ".lang"));
    }

    public void loadLang(String lang) {
        if (!langFolder.exists() || !langFolder.isDirectory()) {
            plugin.getResourceManager().copyDirectory("lang", langFolder);
            MessageUtil.log("lang 폴더를 생성했습니다!");
        }
        File langFile = new File(langFolder, lang + ".lang");
        if (!langFile.exists() || !langFile.isFile()) {
            setLang(defaultLangFile);
            MessageUtil.log(Level.WARNING, "lang 파일(%s)을 찾을 수 없어 기본 언어를 사용합니다.".formatted(langFile));
            return;
        }
        setLang(langFile);
    }

    public void setLang(File langFile) {
        langProperties = new Properties(defaultProperties);

        try (Reader rd = new FileReader(langFile)) {
            langProperties.load(rd);
        } catch (FileNotFoundException ex) {
            throw new InvalidLangException("No file named "+ langFile.getName() +" found in lang folder.", ex);
        } catch (IOException ex) {
            throw new InvalidLangException("Could not read lang file.", ex);
        }
    }

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("(\\$\\{.+})");

    public String translate(MessageKey key, Map<String, Object> vars) {
        for (String placeholder : key.getPlaceholders()) {
            if (!vars.containsKey(placeholder))
                throw new IllegalArgumentException("The variable for placeholder %s was needed, but no variable was given.");
        }

        String raw = (String) langProperties.get(key.getFullKey());

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(raw);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String pholder = matcher.group(1);  // e.g. "${foo}"
            String varName = pholder.substring(2, pholder.length() - 1); // e.g. "foo"
            String replacement = vars.get(varName).toString();
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    public String translate(MessageKey key) {
        return translate(key, Map.of());
    }
}
