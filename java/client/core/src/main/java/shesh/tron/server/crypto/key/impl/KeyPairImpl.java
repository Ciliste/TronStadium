package shesh.tron.server.crypto.key.impl;

import shesh.tron.server.crypto.key.KeyPair;
import shesh.tron.server.crypto.key.PrivateKey;
import shesh.tron.server.crypto.key.PublicKey;

public class KeyPairImpl implements KeyPair {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public KeyPairImpl(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    @Override
    public String toString() {
        return "KeyPairImpl{" +
                "publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                '}';
    }

    @Override
    public PublicKey getFirst() {
        return getPublicKey();
    }

    @Override
    public PrivateKey getSecond() {
        return getPrivateKey();
    }

    @Override
    public void setFirst(PublicKey publicKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSecond(PrivateKey privateKey) {
        throw new UnsupportedOperationException();
    }
}
