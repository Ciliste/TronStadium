package shesh.tron.server.crypto.key.impl;

import shesh.tron.server.crypto.cipher.RsaCipher;
import shesh.tron.server.crypto.key.*;

import java.io.Serializable;
import java.util.Arrays;

public class EncryptedSecretKey implements Serializable {

    private byte[] encryptedKey;

    public EncryptedSecretKey(byte[] encryptedKey) {

        this.encryptedKey = encryptedKey;
    }

    public byte[] getEncryptedKey() {
        return encryptedKey;
    }

    public static EncryptedSecretKey of(SecretKey secretKey, RsaKey rsaKey) {

        try {

            return new EncryptedSecretKey(RsaCipher.encrypt(secretKey.getKey(), rsaKey));
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public static SecretKey decrypt(EncryptedSecretKey encryptedSecretKey, RsaKey rsaKey) {

        try {

            return new SecretKeyImpl(RsaCipher.decrypt(encryptedSecretKey.getEncryptedKey(), rsaKey));
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        KeyPair keyPair = KeyPairFactory.generateRSAKeyPair(2048);

        System.out.println(keyPair);

        SecretKey secretKey = SecretKeyGenerator.generateSecretKey(128);
        System.out.println("Original secret key: " + secretKey);

        RsaKey rsaKey = keyPair.getPublicKey();
        System.out.println("RSA key: " + rsaKey);

        EncryptedSecretKey encryptedSecretKey = EncryptedSecretKey.of(secretKey, rsaKey);
        System.out.println("Encrypted secret key: " + new String(encryptedSecretKey.getEncryptedKey()));

        SecretKey decryptedSecretKey = EncryptedSecretKey.decrypt(encryptedSecretKey, keyPair.getPrivateKey());
        System.out.println("Decrypted secret key: " + decryptedSecretKey);
    }
}
