package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class AutoSaver {
    public static final BukkitScheduler SCHEDULER = MineCollector.getInstance().getServer().getScheduler();
    public static BukkitTask task = null;

    public void start() {
        DBChapter dbConfig = MineCollector.getInstance().getMcolConfig().getDb();

        task = SCHEDULER.runTaskTimer(MineCollector.getInstance(), () -> {

        }, 0, dbConfig.getAutosavePeriod());
    }
}
