package com.doubledeltas.minecollector.ui.book.component;

import com.doubledeltas.minecollector.ui.state.CyclicState;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;

public abstract class SelectionControl<S extends CyclicState<?>> implements BookComponent {
    protected S state;

    protected ClickEvent getClickEvent(Player player) {
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/test");
    }
}
