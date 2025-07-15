package com.doubledeltas.minecollector;

import com.doubledeltas.minecollector.collection.CollectionManager;
import com.doubledeltas.minecollector.command.CommandManager;
import com.doubledeltas.minecollector.config.ConfigManager;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.data.DataAutoSaver;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.event.EventManager;
import com.doubledeltas.minecollector.item.InlineItemManager;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.lang.LangManager;
import com.doubledeltas.minecollector.resource.ResourceManager;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.version.VersionManager;
import com.doubledeltas.minecollector.version.VersionSystem;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;

@Getter
public final class MineCollector extends JavaPlugin {
    private final GameDirector      gameDirector        = new GameDirector();
    private final ItemManager       itemManager         = new InlineItemManager();
    private final CommandManager    commandManager      = new CommandManager();
    private final ConfigManager     configManager       = new ConfigManager();
    private final DataManager       dataManager         = new DataManager();
    private final VersionManager    versionManager      = new VersionManager();
    private final EventManager      eventManager        = new EventManager();
    private final DataAutoSaver     dataAutoSaver       = new DataAutoSaver();
    private final LangManager       langManager         = new LangManager();
    private final ResourceManager   resourceManager     = new ResourceManager();
    private final CollectionManager collectionManager   = new CollectionManager();

    @Getter(AccessLevel.NONE)
    private McolConfig config;

    public static MineCollector getInstance() {
        return MineCollector.getPlugin(MineCollector.class);
    }

    @Override
    public void onEnable() {
        versionManager.register(VersionSystem.UNLABELED);
        versionManager.register(VersionSystem.SEMANTIC);

        configManager.init(this);
        itemManager.init(this);
        collectionManager.init(this);
        dataManager.init(this);
        commandManager.init(this);
        eventManager.init(this);
        dataAutoSaver.init(this);
        langManager.init(this);
        resourceManager.init(this);
        gameDirector.init(this);

        this.config = configManager.load();
        langManager.loadLang();
        collectionManager.generatePieces();
        commandManager.loadCommands();
        dataManager.loadData();
        dataAutoSaver.start();
        MessageUtil.log(Level.INFO, "server.enabled");

        try {
            Class<?> nmcrBuiltInRegistries = Class.forName("net.minecraft.core.registries.BuiltInRegistries");
            Field fItemRegistry = Arrays.stream(nmcrBuiltInRegistries.getDeclaredFields())
                    .filter(field -> {
                        field.setAccessible(true);
                        if (!(field.getGenericType() instanceof ParameterizedType pType))
                            return false;
                        Type[] tArgs = pType.getActualTypeArguments();
                        if (tArgs.length != 1)
                            return false;
                        return "net.minecraft.world.item.Item".equals(tArgs[0].getTypeName());
                    })
                    .findFirst().orElseThrow();
            Iterable<?> itemRegistry = (Iterable<?>) fItemRegistry.get(null);
            for (Object item : itemRegistry) {
                getLogger().log(Level.INFO, item.toString());
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        dataManager.saveAll();
        MessageUtil.log(Level.INFO, "server.disabled");
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
