package com.doubledeltas.minecollector.ui.book;

import com.doubledeltas.minecollector.ui.book.component.Label;
import com.doubledeltas.minecollector.ui.book.component.TriCheckbox;

public class TestBookGui extends AbstractBookGui {
    public TestBookGui() {
        setPage(1,
                new Label("test"),
                Label.NEW_LINE,
                new TriCheckbox()
        );
    }
}
