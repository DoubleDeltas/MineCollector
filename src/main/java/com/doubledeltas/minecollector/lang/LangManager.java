package com.doubledeltas.minecollector.lang;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.MessageUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangManager implements McolInitializable {
    public static final String DEFAULT_LANG = "en_US";

    private MineCollector plugin;
    private File langFolder;
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

        InputStream is = plugin.getResource("lang/" + DEFAULT_LANG + ".lang");
        defaultProperties.load(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

    /**
     * 현재 로딩된 LangManager로 번역합니다.
     * @param msgKey 메시지 키
     * @param vars 대입할 변수
     * @return 번역한 문자열
     */
    public static String translateToText(String msgKey, Object... vars) {
        MessageKey key = MessageKey.of(msgKey, vars.length);
        return MineCollector.getInstance().getLangManager().translateToText(key, vars);
    }

    /**
     * 현재 로딩된 LangManager로 번역합니다.
     * @param msgKey 메시지 키
     * @param vars 대입할 변수
     * @return 번역한 채팅 컴포넌트열
     */
    public static BaseComponent[] translateToComponents(String msgKey, Object... vars) {
        MessageKey key = MessageKey.of(msgKey, vars.length);
        return MineCollector.getInstance().getLangManager().translate(key, vars);
    }
    
    /**
     * 언어팩을 로딩합니다.
     * @param lang 언어팩 파일 이름
     * @return 로딩 성공 여부
     */
    public boolean loadLang(String lang) {
        if (!langFolder.exists() || !langFolder.isDirectory()) {
            plugin.getResourceManager().copyDirectory("lang", langFolder);
            MessageUtil.log("lang.default_created");
        }
        File langFile = new File(langFolder, lang + ".lang");
        if (!langFile.exists() || !langFile.isFile()) {
            MessageUtil.log(Level.WARNING, "lang.using_default", langFile);
            return false;
        }
        setLang(langFile);
        MessageUtil.reloadPrefix();
        plugin.getItemManager().clearCache();
        currentLang = lang;
        return true;
    }

    /**
     * Config에 명시된 언어팩을 로딩합니다.
     * @return 로딩 성공 여부
     */
    public boolean loadLang() {
        currentLang = this.plugin.getMcolConfig().getLang();
        return loadLang(currentLang);
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

    public String translateToText(MessageKey key, Object... vars) {
        return BaseComponent.toLegacyText(translate(key, vars));
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
