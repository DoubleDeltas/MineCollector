package com.doubledeltas.minecollector.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.Queue;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BukkitTaskChain {
    private final Plugin plugin;
    private final Queue<ChainRunnable> tasks = new LinkedList<>();

    public static BukkitTaskChain create(Plugin plugin) {
        return new BukkitTaskChain(plugin);
    }

    public BukkitTaskChain then(Runnable runnable) {
        tasks.add(() -> {
            runnable.run();
            return Result.CONTINUE;
        });
        return this;
    }

    public BukkitTaskChain then(ChainRunnable runnable) {
        tasks.add(runnable);
        return this;
    }

    /** tick 단위 대기 */
    public BukkitTaskChain waitForTicks(long ticks) {
        tasks.add(() -> {
            Bukkit.getScheduler().runTaskLater(plugin, this::runNext, ticks);
            return Result.CONTINUE;
        });
        return this;
    }

    /** 초 단위 대기 */
    public BukkitTaskChain waitForSeconds(long seconds) {
        return waitForTicks(seconds * 20L);
    }

    /** 체이닝 실행 시작 */
    public void run() {
        runNext();
    }

    private void runNext() {
        ChainRunnable next = tasks.poll();
        if (next == null) return; // 끝

        Result result = next.run();
        if (result == Result.CONTINUE && !tasks.isEmpty()) {
            runNext();
        }
    }

    @FunctionalInterface
    public interface ChainRunnable {
        Result run();
    }

    public enum Result {
        CONTINUE,
        BREAK;
    }
}
