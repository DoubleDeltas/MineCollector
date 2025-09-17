package com.doubledeltas.minecollector.ui.book.component;

import com.doubledeltas.minecollector.ui.state.CyclicState;
import com.doubledeltas.minecollector.ui.state.TrileanState;
import com.doubledeltas.minecollector.util.Trilean;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.util.EnumMap;
import java.util.Map;

public class TriCheckbox implements BookComponent {
    private static final HoverEvent HOVER_EVENT = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("test"));
    private static final ClickEvent CLICK_EVENT = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/say hi");

    private static final Map<Trilean, String> DEFAULT_OPTION_NAMES = new EnumMap<>(Map.of(
            Trilean.FALSE,      "§c☒",
            Trilean.UNKNOWN,    "§8☐",
            Trilean.TRUE,       "§8☑"
    ));

    private final CyclicState<Trilean> state;
    private final Map<Trilean, BaseComponent> chatComponentCache;

    private TriCheckbox(Map<Trilean, String> optionNames, Trilean defaultValue) {
        this.state = new TrileanState(defaultValue);

        this.chatComponentCache = new EnumMap<>(Trilean.class);
        for (Trilean trilean : Trilean.values()) {
            BaseComponent component = new TextComponent(optionNames.get(trilean));
            component.setHoverEvent(HOVER_EVENT);
            component.setClickEvent(CLICK_EVENT);

            chatComponentCache.put(trilean, component);
        }
    }

    public TriCheckbox(String falseOptionName, String unknownOptionName, String trueOptionName, Trilean defaultValue) {
        this(Map.of(
                Trilean.FALSE, falseOptionName,
                Trilean.UNKNOWN, unknownOptionName,
                Trilean.TRUE, trueOptionName
        ), defaultValue);
    }

    public TriCheckbox(Trilean defaultValue) {
        this(DEFAULT_OPTION_NAMES, defaultValue);
    }

    public TriCheckbox() {
        this(Trilean.UNKNOWN);
    }

    @Override
    public BaseComponent[] render() {
        return new ComponentBuilder()
                .append(chatComponentCache.get(state.getValue()))
                .create();
    }
}
