package com.doubledeltas.minecollector.ui.state;

import lombok.Getter;

@Getter
public class IntRangeState implements RangeState<Integer> {
    private int maximum;
    private int minimum;
    private int upperCursor;
    private int lowerCursor;
    private int value;

    @Override
    public void setLowerCursor(Integer lower) {
        this.lowerCursor = lower;
    }

    @Override
    public void setUpperCursor(Integer upper) {
        this.upperCursor = upper;
    }
}
