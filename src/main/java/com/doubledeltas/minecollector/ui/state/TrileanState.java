package com.doubledeltas.minecollector.ui.state;

import com.doubledeltas.minecollector.util.Trilean;
import lombok.Getter;

@Getter
public class TrileanState implements CyclicState<Trilean> {
    private Trilean value;

    @Override
    public Trilean getPrevValue() {
        return switch (value) {
            case FALSE -> Trilean.TRUE;
            case UNKNOWN -> Trilean.FALSE;
            case TRUE -> Trilean.UNKNOWN;
        };
    }

    @Override
    public Trilean getNextValue() {
        return switch (value) {
            case FALSE -> Trilean.UNKNOWN;
            case UNKNOWN -> Trilean.TRUE;
            case TRUE -> Trilean.FALSE;
        };
    }

    @Override
    public void toPrevState() {
        this.value = getPrevValue();
    }

    @Override
    public void toNextState() {
        this.value = getNextValue();
    }
}
