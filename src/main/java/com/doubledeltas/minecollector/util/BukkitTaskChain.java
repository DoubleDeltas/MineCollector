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
    private final Queue<Runnable> tasks = new LinkedList<>();

    public static BukkitTaskChain create(Plugin plugin) {
        return new BukkitTaskChain(plugin);
    }

    /** 즉시 실행되는 작업 추가 (스레드 상관 없음) */
    public BukkitTaskChain then(Runnable runnable) {
        tasks.add(runnable);
        return this;
    }

    /** 메인 스레드에서 실행 */
    public BukkitTaskChain thenSync(Runnable runnable) {
        tasks.add(() -> Bukkit.getScheduler().runTask(plugin, runnable));
        return this;
    }

    /** 비동기 스레드에서 실행 */
    public BukkitTaskChain thenAsync(Runnable runnable) {
        tasks.add(() -> Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable));
        return this;
    }

    /** tick 단위 대기 */
    public BukkitTaskChain waitForTicks(long ticks) {
        tasks.add(() -> Bukkit.getScheduler().runTaskLater(plugin, this::runNext, ticks));
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
        Runnable next = tasks.poll();
        if (next == null) return; // 끝

        // next 실행 후 자동으로 다음 작업 이어감
        next.run();
        if (!tasks.isEmpty()) {
            // 즉시 다음 runnable 실행 (스케줄링 된 경우에는 스케줄러가 runNext 호출)
            runNext();
        }
    }
}
