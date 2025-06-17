package com.doubledeltas.minecollector.event.event;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

/**
 * 아이템 콜렉션의 단계가 상승할 때 발생하는 이벤트
 * @since 1.3.1
 */
@RequiredArgsConstructor
public class CollectionLevelUpEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final Material material;
    private final int newLevel;

    // --- BOILERPLATE FOR EVENT API ---

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    @Nonnull
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
