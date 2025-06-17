package com.doubledeltas.minecollector.event.event;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * 아이템을 수집할 때 발생하는 이벤트
 * @since 1.3.1
 */
@RequiredArgsConstructor
public class ItemCollectEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private boolean canceled;

    private final Player player;
    private final Collection<ItemStack> itemStacks;
    private final Route route;

    @Override
    @Nonnull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }

    /**
     * 아이템을 수집한 경로(방법)을 나타냅니다.
     */
    public enum Route {
        COMMAND,
        DUMP
    }
}
