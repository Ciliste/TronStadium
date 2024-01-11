package shesh.tron.server.crypto.key.impl;

import shesh.tron.server.crypto.key.SecretKey;

public class SecretKeyImpl implements SecretKey {

    private final String key;

    public SecretKeyImpl(String key) {

        this.key = key;
    }

    @Override
    public String getKey() {

        return key;
    }

    @Override
    public String toString() {
        return "SecretKeyImpl{" +
                "key=" + key +
                '}';
    }
}
