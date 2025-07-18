package com.doubledeltas.minecollector.ui.state;

import com.doubledeltas.minecollector.util.Range;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntRangeState implements RangeState<Integer> {
    private final int min;
    private final int max;
    private int low;
    private int hi;

    @Override
    public Integer getLowerCursor() {
        return low;
    }

    @Override
    public Integer getUpperCursor() {
        return hi;
    }

    @Override
    public Integer getMaximum() {
        return max;
    }

    @Override
    public Integer getMinimum() {
        return min;
    }

    @Override
    public void setLowerCursor(Integer lower) {
        this.low = lower;
    }

    @Override
    public void setUpperCursor(Integer upper) {
        this.hi = upper;
    }

    @Override
    public Range<Integer> getValue() {
        return new Range<>(low, hi);
    }
}
