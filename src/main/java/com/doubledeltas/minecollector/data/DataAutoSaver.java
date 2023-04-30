package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class DataAutoSaver {
    public static final BukkitScheduler SCHEDULER = MineCollector.getInstance().getServer().getScheduler();
    public static BukkitTask task = null;

    public static void start() {
        DBChapter dbConfig = MineCollector.getInstance().getMcolConfig().getDb();
        long period = (long) dbConfig.getAutosavePeriod() * 60 * 20;
        if (period == 0L) period = Long.MAX_VALUE;

        boolean isLogging = dbConfig.isAutosaveLogging();
        task = SCHEDULER.runTaskTimer(
                MineCollector.getInstance(), () -> {
                    DataManager.saveAll();
                    if (isLogging) {
                        MessageUtil.log("데이터 자동 저장됨!");
                    }
                },
                period,
                period
        );
    }

    public static void restart() {
        task.cancel();
        start();
    }
}
