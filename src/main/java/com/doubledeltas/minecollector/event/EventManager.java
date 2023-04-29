package com.doubledeltas.minecollector.event;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class EventManager {
    private static List<Listener> listeners = Arrays.asList(
            new OpenBookEventListener(),
            new OnLoginEventListener(),
            new OnAdvancementDoneEventListener()
    );

    public static void loadEventHandlers() {
        for (Listener listener: listeners) {
            MineCollector.getInstance().getServer().getPluginManager().registerEvents(listener, MineCollector.getInstance());
        }
    }

}
