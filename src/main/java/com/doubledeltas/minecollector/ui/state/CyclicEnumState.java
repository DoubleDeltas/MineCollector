package com.doubledeltas.minecollector.ui.state;

import com.google.common.base.Preconditions;

public class CyclicEnumState<V extends Enum<V>> implements CyclicState<V> {
    private final V[] values;
    private final int length;
    private int index;

    public CyclicEnumState(Class<V> enumClass, int initialIndex) {
        this.values = enumClass.getEnumConstants();
        this.length = values.length;
        Preconditions.checkArgument(0 <= initialIndex && initialIndex < length);
        this.index = initialIndex;
    }

    public CyclicEnumState(Class<V> enumClass, V initialState) {
        Preconditions.checkNotNull(initialState);
        this.values = enumClass.getEnumConstants();
        this.length = values.length;
        this.index = initialState.ordinal();
    }

    public CyclicEnumState(Class<V> enumClass) {
        this(enumClass, 0);
    }

    @Override
    public V getValue() {
        return values[index];
    }

    @Override
    public V getPrevValue() {
        return values[(index + length - 1) % length];
    }

    @Override
    public V getNextValue() {
        return values[(index + 1) % length];
    }

    @Override
    public void toPrevState() {
        index = (index + length - 1) % length;
    }

    @Override
    public void toNextState() {
        index = (index + 1) % length;
    }
}
