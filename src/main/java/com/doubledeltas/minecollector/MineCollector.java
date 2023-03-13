package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.command.MineCollectorCommand;
import com.doubledeltas.minecollector.item.InlineItemManager;
import com.doubledeltas.minecollector.item.ItemManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MineCollector extends JavaPlugin {
    public final ItemManager itemManager = new InlineItemManager();

    public static MineCollector getPlugin() {
        return MineCollector.getPlugin(MineCollector.class);
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    @Override
    public void onEnable() {
        MineCollectorCommand.loadCommands();
        getLogger().log(Level.INFO, "마인콜렉터 플러그인이 켜졌습니다!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "마인콜렉터 플러그인이 꺼졌습니다.");
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
}
