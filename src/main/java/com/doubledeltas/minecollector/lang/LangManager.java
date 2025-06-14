package com.doubledeltas.minecollector.lang;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangManager implements McolInitializable {
    private static final String DEFAULT_LANG = "en_US";

    private MineCollector plugin;
    private File langFolder;
    private File defaultLangFile;
    @Getter
    private Properties langProperties;
    private final Properties defaultProperties;
    private String currentLang;

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
            MessageUtil.log("lang.default_created");
        }
        File langFile = new File(langFolder, lang + ".lang");
        if (!langFile.exists() || !langFile.isFile()) {
            setLang(defaultLangFile);
            MessageUtil.log(Level.WARNING, "lang.using_default", langFile);
            return;
        }
        setLang(langFile);
        MessageUtil.reloadPrefix();
        currentLang = lang;
    }

    public void reloadLang() {
        this.plugin.reloadMcolConfig();
        currentLang = this.plugin.getMcolConfig().getLang();
        loadLang(currentLang);
    }

    protected void setLang(File langFile) {
        langProperties = new Properties(defaultProperties);

        try (Reader rd = new FileReader(langFile)) {
            langProperties.load(rd);
        } catch (FileNotFoundException ex) {
            throw new InvalidLangException("No file named "+ langFile.getName() +" found in lang folder.", ex);
        } catch (IOException ex) {
            throw new InvalidLangException("Could not read lang file.", ex);
        }
    }

    private static final Pattern PLADCEHOLDER_PATTERN = Pattern.compile("(\\{\\d+?})");

    public BaseComponent[] translate(MessageKey key, Object... vars) {
        checkVariableCount(key, vars);

        ComponentBuilder builder = new ComponentBuilder();

        String raw = getRaw(key);

        int lastEnd = 0;    // last end index
        Matcher matcher = PLADCEHOLDER_PATTERN.matcher(raw);
        while (matcher.find()) {
            // append text before placeholder
            if (lastEnd != matcher.start()) {
                String text = raw.substring(lastEnd, matcher.start());
                builder.appendLegacy(text);
            }

            String group = matcher.group(1);
            Object var = getVariable(group, vars);
            if (var instanceof BaseComponent component)
                builder.append(component);
            else if (var instanceof BaseComponent[] components)
                builder.append(components);
            else
                builder.appendLegacy(var.toString());

            lastEnd = matcher.end();
        }

        // append rest text
        if (lastEnd < raw.length()) {
            String text = raw.substring(lastEnd);
            builder.appendLegacy(text);
        }

        return builder.create();
    }
    
    private static void checkVariableCount(MessageKey key, Object[] vars) {
        int givenVarsCnt = vars.length;
        int neededVarsCnt = key.getVariableCount();
        if (givenVarsCnt != neededVarsCnt) {
            throw new IllegalArgumentException("The %d variable(s) was needed, but %d variable(s) was given.".formatted(neededVarsCnt, givenVarsCnt));
        }
    }

    private String getRaw(MessageKey key) {
        String fullKey = key.getFullKey();
        if (langProperties == null)
            return (String) defaultProperties.get(fullKey);

        String raw = (String) langProperties.get(fullKey);
        if (raw == null)
            raw = (String) defaultProperties.get(fullKey);
        return raw;
    }

    @SafeVarargs
    private static <T> T getVariable(String placeholder, T... vars) {
        int varIdx = Integer.parseInt(placeholder.substring(1, placeholder.length() - 1)); // e.g. 1
        return vars[varIdx];
    }
}
