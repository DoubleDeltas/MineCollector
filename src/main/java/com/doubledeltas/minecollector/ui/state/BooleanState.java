package com.doubledeltas.minecollector.ui.state;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BooleanState implements CyclicState<Boolean> {
    private boolean value;

    public BooleanState() {
        this(false);
    }

    @Override
    public Boolean getPrevValue() {
        return !value;
    }

    @Override
    public Boolean getNextValue() {
        return !value;
    }

    @Override
    public void toPrevState() {
        value = !value;
    }

    @Override
    public void toNextState() {
        value = !value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
