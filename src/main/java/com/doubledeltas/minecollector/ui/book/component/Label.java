package com.doubledeltas.minecollector.ui.book.component;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.Arrays;

public class Label implements BookComponent {
    private final BaseComponent[] chatComponents;

    public Label(String content) {
        this.chatComponents = new ComponentBuilder(content).create();
    }

    public Label(BaseComponent... chatComponents) {
        this.chatComponents = Arrays.copyOf(chatComponents, chatComponents.length);
    }

    @Override
    public BaseComponent[] render() {
        return chatComponents;
    }
}
