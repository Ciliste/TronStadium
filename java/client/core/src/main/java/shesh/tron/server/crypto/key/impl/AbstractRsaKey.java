package shesh.tron.server.crypto.key.impl;

import shesh.tron.server.crypto.key.RsaKey;

import java.math.BigInteger;

public abstract class AbstractRsaKey implements RsaKey {

    private final BigInteger n;
    private final BigInteger otherValue;

    protected AbstractRsaKey(BigInteger n, BigInteger otherValue) {

        this.n = n;
        this.otherValue = otherValue;
    }

    @Override
    public BigInteger getN() {
        return n;
    }

    @Override
    public BigInteger getOtherValue() {
        return otherValue;
    }
}
