package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.command.*;
import com.doubledeltas.minecollector.item.EmbeddedItemManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MineCollector extends JavaPlugin {
    public final EmbeddedItemManager embeddedItemManager = new EmbeddedItemManager(this);

    public static MineCollector getPlugin() {
        return MineCollector.getPlugin(MineCollector.class);
    }

    public EmbeddedItemManager getEmbeddedItemManager() {
        return embeddedItemManager;
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
}
