package shesh.tron.server.crypto.key.impl;

import shesh.tron.server.crypto.key.PrivateKey;

import java.math.BigInteger;

public class PrivateKeyImpl extends AbstractRsaKey implements PrivateKey {

    public PrivateKeyImpl(BigInteger n, BigInteger d) {

        super(n, d);
    }

    @Override
    public BigInteger getD() {

        return super.getOtherValue();
    }

    @Override
    public String toString() {
        return "PrivateKeyImpl{" +
                "n=" + getN() +
                ", d=" + getD() +
                '}';
    }
}
