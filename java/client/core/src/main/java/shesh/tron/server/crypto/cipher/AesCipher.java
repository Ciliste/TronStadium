package shesh.tron.server.crypto.cipher;

import shesh.tron.server.crypto.key.SecretKey;
import shesh.tron.server.crypto.key.SecretKeyGenerator;

public final class AesCipher {

    private AesCipher() {}

    public static byte[] encrypt(String message, SecretKey key) {

        return encrypt(message.getBytes(), key);
    }

    public static byte[] encrypt(byte[] message, SecretKey key) {

        return encrypt(message, key.getKey());
    }

    public static byte[] encrypt(byte[] message, String key) {

        byte[] encrypted = new byte[message.length];

        for (int i = 0; i < message.length; i++) {
            encrypted[i] = (byte) (message[i] ^ key.charAt(i % key.length()));
        }

        return encrypted;
    }

    public static byte[] decrypt(byte[] message, SecretKey key) {

        return decrypt(message, key.getKey());
    }

    public static byte[] decrypt(byte[] message, String key) {

        return encrypt(message, key);
    }

    public static void main(String[] args) {

        SecretKey key = SecretKeyGenerator.generateSecretKey(128);

        System.out.println("Original secret key: " + key);

        String data = "Sheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeesh";
        System.out.println("Original data: " + data);

        byte[] encryptedData = encrypt(data, key);
        System.out.println("Encrypted data: " + new String(encryptedData));

        String decryptedData = new String(decrypt(encryptedData, key));
        System.out.println("Decrypted data: " + decryptedData);
    }
}
