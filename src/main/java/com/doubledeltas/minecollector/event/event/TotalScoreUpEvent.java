package com.doubledeltas.minecollector.event.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class TotalScoreUpEvent extends Event {
    @Getter(AccessLevel.NONE)
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final BigDecimal oldScore;
    private final BigDecimal newScore;

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
