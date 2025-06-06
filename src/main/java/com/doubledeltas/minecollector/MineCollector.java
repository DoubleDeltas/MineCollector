package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.command.CommandManager;
import com.doubledeltas.minecollector.config.ConfigManager;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.data.DataAutoSaver;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.event.EventManager;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.manager.InlineItemManager;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.version.VersionManager;
import com.doubledeltas.minecollector.version.VersionSystem;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MineCollector extends JavaPlugin {
    @Getter
    private final GameDirector gameDirector = new GameDirector();
    @Getter
    private final ItemManager itemManager = new InlineItemManager();
    @Getter
    private final CommandManager commandManager = new CommandManager();
    @Getter
    private final ConfigManager configManager = new ConfigManager();
    @Getter
    private final DataManager dataManager = new DataManager();
    @Getter
    private final VersionManager versionManager = new VersionManager();
    @Getter
    private final EventManager eventManager = new EventManager();
    @Getter
    private final DataAutoSaver dataAutoSaver = new DataAutoSaver();

    private McolConfig config;

    public static MineCollector getInstance() {
        return MineCollector.getPlugin(MineCollector.class);
    }

    @Override
    public void onEnable() {
        versionManager.register(VersionSystem.UNLABELED);
        versionManager.register(VersionSystem.SEMANTIC);

        configManager.init(this);
        dataManager.init(this);
        commandManager.init(this);
        eventManager.init(this);
        dataAutoSaver.init(this);

        commandManager.loadCommands();
        try {
            this.config = configManager.load();
        } catch (InvalidConfigException e) {
            throw new RuntimeException(e);
        }
        dataManager.loadData();
        dataAutoSaver.start();
        MessageUtil.log(Level.INFO, "마인콜렉터 플러그인이 켜졌습니다!");
    }

    @Override
    public void onDisable() {
        dataManager.saveAll();
        MessageUtil.log(Level.INFO, "마인콜렉터 플러그인이 꺼졌습니다.");
    }

    public McolConfig getMcolConfig() {
        return this.config;
    }

    public void reloadMcolConfig() throws InvalidConfigException {
        this.config = configManager.load();
        dataAutoSaver.restart();
    }

    /**
     * use {@link ConfigManager#saveConfig() configManager.saveDefaultConfig} instead.
     */
    @Override
    @Deprecated
    public void saveDefaultConfig() {
        configManager.saveConfig();
    }
}
