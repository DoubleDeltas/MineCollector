package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.constant.Titles;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.event.EventManager;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.manager.InlineItemManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MineCollector extends JavaPlugin {
    private final ItemManager itemManager = new InlineItemManager();

    public static MineCollector getPlugin() {
        return MineCollector.getPlugin(MineCollector.class);
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    @Override
    public void onEnable() {
        DataManager.setup();
        EventManager.loadEventHandlers();
        CommandRoot.loadCommands();
        MineCollector.log(Level.INFO, "마인콜렉터 플러그인이 켜졌습니다!");
    }

    @Override
    public void onDisable() {
        DataManager.saveAll();
        MineCollector.log(Level.INFO, "마인콜렉터 플러그인이 꺼졌습니다.");
    }

    /**
     * 로그 메시지를 보냅니다.
     * @param level 로그 레벨
     * @param msg 메시지
     */
    public static void log(Level level, String msg) {
        getPlugin().getLogger().log(level, msg);
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
        getPlugin().getServer().broadcastMessage(Titles.MSG_PREFIX + msg);
    }
}
