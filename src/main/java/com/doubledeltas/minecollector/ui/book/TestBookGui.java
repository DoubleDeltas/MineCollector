package com.doubledeltas.minecollector.ui.book;

import com.doubledeltas.minecollector.ui.book.component.Label;

public class TestBookGui extends AbstractBookGui {
    public TestBookGui() {
        setPage(1,
                new Label("test")
        );
    }
}
