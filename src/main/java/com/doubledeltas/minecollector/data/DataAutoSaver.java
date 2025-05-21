package com.doubledeltas.minecollector.data;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.TimeUtil;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class DataAutoSaver {
    public BukkitScheduler scheduler;
    public BukkitTask task = null;

    public void init() {
        scheduler = MineCollector.getInstance().getServer().getScheduler();
    }

    public void start() {
        McolConfig.DB dbConfig = MineCollector.getInstance().getMcolConfig().getDb();
        long period = TimeUtil.toTicks(dbConfig.getAutosavePeriod());
        if (period == 0L) period = Long.MAX_VALUE;

        boolean isLogging = dbConfig.isAutosaveLogging();
        task = scheduler.runTaskTimer(
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

    public void restart() {
        task.cancel();
        start();
    }
}
