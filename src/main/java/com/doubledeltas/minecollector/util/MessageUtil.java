package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.AnnouncementTarget;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class MessageUtil {
    public static String MSG_PREFIX = "§8[ §a마인§f콜렉터 §8]§f ";
    public static BaseComponent MSG_PREFIX_COMPONENT = new TextComponent(MSG_PREFIX);

    /**
     * 로그 메시지를 보냅니다.
     * @param level 로그 레벨
     * @param msg 메시지
     */
    public static void log(Level level, String msg) {
        MineCollector.getInstance().getLogger().log(level, msg);
    }

    /**
     * {@link Level#INFO} 레벨의 로그 메시지를 보냅니다.
     * @param msg 메시지
     */
    public static void log(String msg) {
        log(Level.INFO, msg);
    }

    /**
     * 플레이어 또는 콘솔에게 마인콜렉터 메시지를 보냅니다.
     * @param subject 수신자
     * @param msg 메시지
     */
    public static void send(CommandSender subject, String msg) {
        subject.sendMessage(MSG_PREFIX + msg);
    }

    /**
     * {@link AnnouncementTarget}에게 마인콜렉터 메시지를 보냅니다.
     * @param target {@link AnnouncementTarget}
     * @param subject 수신자
     * @param msg 메시지
     */
    public static void send(AnnouncementTarget target, Player subject, String msg) {
        if (target == AnnouncementTarget.ALL_PLAYERS) {
            broadcast(msg);
            return;
        }
        for (Player player: target.resolve(subject))
            send(player, msg);
    }

    /**
     * 마인콜렉터 서버 전체에 마인콜렉터 메시지를 보냅니다.
     * @param msg 메시지
     */
    public static void broadcast(String msg) {
        MineCollector.getInstance().getServer().broadcastMessage(MSG_PREFIX + msg);
    }

    /**
     * 플레이어 또는 콘솔에게 마인콜렉터 접두어와 함께 채팅 컴포넌트를 보냅니다.
     * @param subject 주체(수신자)
     * @param components 보낼 컴포넌트들
     */
    public static void sendRaw(CommandSender subject, BaseComponent... components) {
        subject.spigot().sendMessage(MessageUtil.getPrefixedComponents(components));
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
        BaseComponent[] prefixedComponents = new BaseComponent[components.length + 1];
        prefixedComponents[0] = MSG_PREFIX_COMPONENT;
        System.arraycopy(components, 0, prefixedComponents, 1, components.length);
        return prefixedComponents;
    }
}
