package shesh.tron.utils.struct.impl;

import shesh.tron.utils.struct.Couple;

public class CoupleImpl<T, V> implements Couple<T, V> {

    public T first;
    public V second;

    public CoupleImpl(T first, V second) {

        this.first = first;
        this.second = second;
    }

    @Override
    public T getFirst() {

        return first;
    }

    @Override
    public V getSecond() {

        return second;
    }

    @Override
    public void setFirst(T first) {

        this.first = first;
    }

    @Override
    public void setSecond(V second) {

        this.second = second;
    }
}
