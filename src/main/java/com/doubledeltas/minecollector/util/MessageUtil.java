package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.AnnouncementTarget;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

import static com.doubledeltas.minecollector.lang.LangManager.translateToComponents;
import static com.doubledeltas.minecollector.lang.LangManager.translateToText;

public class MessageUtil {
    private static final BaseComponent BLANK_COMPONENT = new TextComponent(" ");
    private static BaseComponent[] MSG_PREFIX_COMPONENTS;
    private static String MSG_PREFIX;

    /**
     * 로그 메시지를 보냅니다.
     * @param level 로그 레벨
     * @param msg 메시지
     */
    public static void logRaw(Level level, String msg) {
        MineCollector.getInstance().getLogger().log(level, msg);
    }

    public static void logRaw(Level level, BaseComponent[] components) {
        logRaw(level, BaseComponent.toPlainText(components));
    }

    public static void log(Level level, String msgKey, Object... vars) {
        logRaw(level, BaseComponent.toPlainText(translateToComponents(msgKey, vars)));
    }

    /**
     * {@link Level#INFO} 레벨의 로그 메시지를 보냅니다.
     * @param msg 메시지
     */
    public static void logRaw(String msg) {
        logRaw(Level.INFO, msg);
    }

    public static void log(String msgKey, Object... vars) {
        log(Level.INFO, msgKey, vars);
    }

    /**
     * 플레이어 또는 콘솔에게 마인콜렉터 메시지를 보냅니다.
     * @param subject 수신자
     * @param msg 메시지
     */
    public static void sendRaw(CommandSender subject, String msg) {
        subject.sendMessage(prefix() + " " + msg);
    }

    /**
     * 플레이어 또는 콘솔에게 마인콜렉터 접두어와 함께 채팅 컴포넌트를 보냅니다.
     * @param subject 주체(수신자)
     * @param components 보낼 컴포넌트들
     */
    public static void sendRaw(CommandSender subject, BaseComponent... components) {
        subject.spigot().sendMessage(MessageUtil.getPrefixedComponents(components));
    }

    public static void send(CommandSender subject, String msgKey, Object... vars) {
        sendRaw(subject, translateToComponents(msgKey, vars));
    }

    /**
     * {@link AnnouncementTarget}에게 마인콜렉터 메시지를 보냅니다.
     * @param target {@link AnnouncementTarget}
     * @param subject 수신자
     * @param msg 메시지
     */
    public static void sendRaw(AnnouncementTarget target, Player subject, String msg) {
        if (target == AnnouncementTarget.ALL_PLAYERS) {
            broadcastRaw(msg);
            return;
        }
        for (Player player: target.resolve(subject))
            sendRaw(player, msg);
    }

    /**
     * {@link AnnouncementTarget}에게 채팅 컴포넌트를 보냅니다.
     * @param target {@link AnnouncementTarget}
     * @param subject 수신자
     * @param components 보낼 컴포넌트들
     */
    public static void sendRaw(AnnouncementTarget target, Player subject, BaseComponent... components) {
        if (target == AnnouncementTarget.ALL_PLAYERS) {
            broadcastRaw(components);
            return;
        }
        for (Player player: target.resolve(subject))
            sendRaw(player, components);
    }

    public static void send(AnnouncementTarget target, Player subject, String msgKey, Object... vars) {
        sendRaw(target, subject, translateToComponents(msgKey, vars));
    }

    /**
     * 마인콜렉터 서버 전체에 마인콜렉터 메시지를 보냅니다.
     * @param msg 메시지
     */
    public static void broadcastRaw(String msg) {
        MineCollector.getInstance().getServer().broadcastMessage(MSG_PREFIX + " " + msg);
    }

    /**
     * 마인콜렉터 서버 전체에 마인콜렉터 접두어와 함께 채팅 컴포넌트를 보냅니다.
     * @param components 보낼 컴포넌트들
     */
    public static void broadcastRaw(BaseComponent... components) {
        BaseComponent[] prefixedComponents = MessageUtil.getPrefixedComponents(components);

        MineCollector.getInstance().getServer().spigot().broadcast(prefixedComponents);
        Bukkit.getConsoleSender().spigot().sendMessage(prefixedComponents);
    }

    /**
     * 접두사가 앞에 붙은 BaseComponent 배열을 얻습니다.
     * @param components BaseComponent 배열
     */
    private static BaseComponent[] getPrefixedComponents(BaseComponent[] components) {
        BaseComponent[] prefix = prefixComponents();
        BaseComponent[] result = new BaseComponent[prefix.length + 1 + components.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        result[prefix.length] = BLANK_COMPONENT;
        System.arraycopy(components, 0, result, prefix.length + 1, components.length);
        return result;
    }

    public static void reloadPrefix() {
        MSG_PREFIX_COMPONENTS = translateToComponents("prefix");
        MSG_PREFIX = BaseComponent.toLegacyText(MSG_PREFIX_COMPONENTS);
    }

    private static String prefix() {
        if (MSG_PREFIX == null)
            reloadPrefix();
        return MSG_PREFIX;
    }

    private static BaseComponent[] prefixComponents() {
        if (MSG_PREFIX_COMPONENTS == null)
            reloadPrefix();
        return MSG_PREFIX_COMPONENTS;
    }
}
