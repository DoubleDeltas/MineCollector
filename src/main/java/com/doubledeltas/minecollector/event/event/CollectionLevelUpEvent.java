package com.doubledeltas.minecollector.event.event;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
public class CollectionLevelUpEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final Material material;
    private final int newLevel;

    @Override
    @Nonnull
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
