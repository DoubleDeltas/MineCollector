package com.doubledeltas.minecollector.ui.book;

import com.doubledeltas.minecollector.ui.book.component.BookComponent;

public interface BookGuiPage extends BookComponent {
    void addComponents(BookComponent... components);
    void clear();
}
