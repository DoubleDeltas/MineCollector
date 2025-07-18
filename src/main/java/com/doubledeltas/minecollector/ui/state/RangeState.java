package com.doubledeltas.minecollector.ui.state;

import com.doubledeltas.minecollector.util.Range;

public interface RangeState<V extends Comparable<V>> extends State<Range<V>> {
    V getLowerCursor();
    V getUpperCursor();
    V getMaximum();
    V getMinimum();
    void setLowerCursor(V lower);
    void setUpperCursor(V upper);
}
