package shesh.tron.server.crypto.key;

import shesh.tron.server.crypto.key.impl.SecretKeyImpl;

public class SecretKeyGenerator {

    public static SecretKey generateSecretKey(int bitLength) {

        StringBuilder key = new StringBuilder();

        for (int i = 0; i < bitLength; i++) {
            key.append((int) (Math.random() * 10));
        }

        return new SecretKeyImpl(key.toString());
    }
}
