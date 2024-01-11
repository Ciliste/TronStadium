package shesh.tron.server.crypto.key.impl;

import shesh.tron.server.crypto.key.PublicKey;

import java.math.BigInteger;

public class PublicKeyImpl extends AbstractRsaKey implements PublicKey {

    public PublicKeyImpl(BigInteger n, BigInteger e) {

        super(n, e);
    }

    @Override
    public BigInteger getE() {

        return super.getOtherValue();
    }

    @Override
    public String toString() {
        return "PublicKeyImpl{" +
                "n=" + getN() +
                ", e=" + getE() +
                '}';
    }
}
