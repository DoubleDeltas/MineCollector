package com.doubledeltas.minecollector.ui.state;

public interface RangeState<V extends Comparable<V>> extends State<V> {
    V getLowerCursor();
    V getUpperCursor();
    V getMaximum();
    V getMinimum();
    void setLowerCursor(V lower);
    void setUpperCursor(V upper);
}
