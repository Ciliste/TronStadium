package shesh.tron.utils.struct.impl;

import shesh.tron.utils.struct.Trouple;

public class TroupleImpl<T, V, K> extends CoupleImpl<T, V> implements Trouple<T, V, K> {

    public K third;

    public TroupleImpl(T first, V second, K third) {

        super(first, second);
        this.third = third;
    }

    @Override
    public K getThird() {

        return third;
    }

    @Override
    public void setThird(K third) {

        this.third = third;
    }
}
