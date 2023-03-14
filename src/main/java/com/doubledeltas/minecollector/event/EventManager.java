package com.doubledeltas.minecollector.event;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class EventManager {
    private static List<Listener> listeners = Arrays.asList(
            new OpenBookEventListener()
    );

    public static void loadEventHandlers() {
        for (Listener listener: listeners) {
            MineCollector.getPlugin().getServer().getPluginManager().registerEvents(listener, MineCollector.getPlugin());
        }
    }

}
