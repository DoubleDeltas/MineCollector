package com.doubledeltas.minecollector.event;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.event.listener.*;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class EventManager implements McolInitializable {
    private final List<Listener> listeners = Arrays.asList(
            new PlayerInteractEventListener(),
            new PlayerLoginEventListener(),
            new PlayerAdvancementDontEventListener(),
            new EntityDeathEventListener(),
            new TestListener()
    );

    public void init(MineCollector plugin) {
        for (Listener listener: listeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, MineCollector.getInstance());
        }
    }

}
