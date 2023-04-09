package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.MineCollector;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.sql.Array;
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
        MineCollector.getPlugin().getLogger().log(level, msg);
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
     * @param target 수신자
     * @param msg 메시지
     */
    public static void send(CommandSender target, String msg) {
        target.sendMessage(MSG_PREFIX + msg);
    }

    /**
     * 마인콜렉터 서버 전체에 마인콜렉터 메시지를 보냅니다.
     * @param msg 메시지
     */
    public static void broadcast(String msg) {
        MineCollector.getPlugin().getServer().broadcastMessage(MSG_PREFIX + msg);
    }

    /**
     * 플레이어 또는 콘솔에게 마인콜렉터 접두어와 함께 채팅 컴포넌트를 보냅니다.
     * @param target 수신자
     * @param components 보낼 컴포넌트들
     */
    public static void sendRaw(CommandSender target, BaseComponent... components) {
        List<BaseComponent> list = new ArrayList<>();
        list.add(MSG_PREFIX_COMPONENT);
        list.addAll(Arrays.asList(components));

        target.spigot().sendMessage(list.toArray(new BaseComponent[0]));
    }

    /**
     * 마인콜렉터 서버 전체에 마인콜렉터 접두어와 함께 채팅 컴포넌트를 보냅니다.
     * @param components 보낼 컴포넌트들
     */
    public static void broadcastRaw(BaseComponent... components) {
        List<BaseComponent> list = new ArrayList<>();
        list.add(MSG_PREFIX_COMPONENT);
        list.addAll(Arrays.asList(components));

        MineCollector.getPlugin().getServer().spigot().broadcast(list.toArray(new BaseComponent[0]));
    }
}
