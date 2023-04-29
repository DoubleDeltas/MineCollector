package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.AnnouncementTarget;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

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
     * 마인콜렉터 서버 전체에 마인콜렉터 메시지를 보냅니다.
     * @param msg 메시지
     */
    public static void broadcast(String msg) {
        MineCollector.getInstance().getServer().broadcastMessage(MSG_PREFIX + msg);
    }

    /**
     * {@link AnnouncementTarget}에 따라 메시지를 보냅니다.
     * @param target 타겟 설정
     * @param subject 주체({@link AnnouncementTarget#SELF}일 때 수신자)
     * @param msg 메시지
     */
    public static void announce(AnnouncementTarget target, CommandSender subject, String msg) {
        switch (target) {
            case ALL_PLAYERS -> broadcast(msg);
            case SELF -> send(subject, msg);
        }
    }

    /**
     * 플레이어 또는 콘솔에게 마인콜렉터 접두어와 함께 채팅 컴포넌트를 보냅니다.
     * @param subject 주체(수신자)
     * @param components 보낼 컴포넌트들
     */
    public static void sendRaw(CommandSender subject, BaseComponent... components) {
        List<BaseComponent> list = new ArrayList<>();
        list.add(MSG_PREFIX_COMPONENT);
        list.addAll(Arrays.asList(components));

        subject.spigot().sendMessage(list.toArray(new BaseComponent[0]));
    }

    /**
     * 마인콜렉터 서버 전체에 마인콜렉터 접두어와 함께 채팅 컴포넌트를 보냅니다.
     * @param components 보낼 컴포넌트들
     */
    public static void broadcastRaw(BaseComponent... components) {
        List<BaseComponent> list = new ArrayList<>();
        list.add(MSG_PREFIX_COMPONENT);
        list.addAll(Arrays.asList(components));

        MineCollector.getInstance().getServer().spigot().broadcast(list.toArray(new BaseComponent[0]));
    }

    /**
     * {@link AnnouncementTarget}에 따라 채팅 컴포넌트를 보냅니다.
     * @param target 타겟 설정
     * @param subject 주체({@link AnnouncementTarget#SELF}일 때 수신자)
     * @param msg 메시지
     */
    public static void announceRaw(AnnouncementTarget target, CommandSender subject, BaseComponent... components) {
        switch (target) {
            case ALL_PLAYERS -> broadcastRaw(components);
            case SELF -> sendRaw(subject, components);
        }
    }
}
