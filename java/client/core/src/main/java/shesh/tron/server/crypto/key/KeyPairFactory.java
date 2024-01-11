package shesh.tron.server.crypto.key;

import shesh.tron.server.crypto.key.impl.KeyPairImpl;
import shesh.tron.server.crypto.key.impl.PrivateKeyImpl;
import shesh.tron.server.crypto.key.impl.PublicKeyImpl;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public final class KeyPairFactory {

    private KeyPairFactory() {
    }

    public static KeyPair createKeyPair(PublicKey publicKey, PrivateKey privateKey) {

        return new KeyPairImpl(publicKey, privateKey);
    }

    public static KeyPair generateRSAKeyPair(int bitSize) {

        try {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            keyPairGenerator.initialize(bitSize);

            java.security.KeyPair keyPair = keyPairGenerator.generateKeyPair();

            java.security.PublicKey publicKey = keyPair.getPublic();
            java.security.PrivateKey privateKey = keyPair.getPrivate();

            if (publicKey instanceof java.security.interfaces.RSAPublicKey
                && privateKey instanceof java.security.interfaces.RSAPrivateKey) {

                java.security.interfaces.RSAPublicKey rsaPublicKey = (java.security.interfaces.RSAPublicKey) publicKey;
                java.security.interfaces.RSAPrivateKey rsaPrivateKey = (java.security.interfaces.RSAPrivateKey) privateKey;

                BigInteger n = rsaPublicKey.getModulus();
                BigInteger e = rsaPublicKey.getPublicExponent();
                BigInteger d = rsaPrivateKey.getPrivateExponent();

                return createKeyPair(new PublicKeyImpl(n, e), new PrivateKeyImpl(n, d));
            }
            else {

                throw new IllegalArgumentException("KeyPairGenerator did not generate RSA keys");
            }
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        KeyPair keyPair = generateRSAKeyPair(2048);

        System.out.println(keyPair);
    }
}
