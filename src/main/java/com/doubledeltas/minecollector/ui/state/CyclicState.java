package com.doubledeltas.minecollector.ui.state;

public interface CyclicState<V> extends State<V> {
    V getPrevValue();
    V getNextValue();

    void toPrevState();
    void toNextState();
}
