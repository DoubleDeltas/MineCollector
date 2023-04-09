package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.constant.Titles;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class MessageUtil {

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
        target.sendMessage(Titles.MSG_PREFIX + msg);
    }

    /**
     * 마인콜렉터 서버 전체에 마인콜렉터 메시지를 보냅니다.
     * @param msg 메시지
     */
    public static void broadcast(String msg) {
        MineCollector.getPlugin().getServer().broadcastMessage(Titles.MSG_PREFIX + msg);
    }
}
