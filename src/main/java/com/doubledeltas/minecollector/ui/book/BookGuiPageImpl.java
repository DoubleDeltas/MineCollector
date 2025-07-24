package com.doubledeltas.minecollector.ui.book;

import com.doubledeltas.minecollector.ui.book.component.BookComponent;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BookGuiPageImpl implements BookGuiPage {
    private final List<BookComponent> components;

    public BookGuiPageImpl() {
        this.components = new LinkedList<>();
    }

    @Override
    public void addComponents(BookComponent... components) {
        this.components.addAll(List.of(components));
    }

    @Override
    public void clear() {
        this.components.clear();
    }

    @Override
    public BaseComponent[] render() {
        return this.components.stream()
                .map(BookComponent::render)
                .flatMap(Arrays::stream)
                .toArray(BaseComponent[]::new);
    }
}
