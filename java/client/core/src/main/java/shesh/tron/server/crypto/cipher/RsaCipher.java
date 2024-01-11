package shesh.tron.server.crypto.cipher;

import shesh.tron.server.crypto.key.*;

import javax.crypto.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public final class RsaCipher {

    public static byte[] encrypt(String message, RsaKey key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        BigInteger n = key.getN();
        BigInteger otherValue = key.getOtherValue();

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, otherValue);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        java.security.PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        return cipher.doFinal(message.getBytes());
    }

    public static String decrypt(byte[] message, RsaKey key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        BigInteger n = key.getN();
        BigInteger otherValue = key.getOtherValue();

        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(n, otherValue);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        java.security.PrivateKey privKey = keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privKey);

        return new String(cipher.doFinal(message));
    }

    public static void main(String[] args) {

        KeyPair keyPair = KeyPairFactory.generateRSAKeyPair(2048);

        System.out.println(keyPair);

        String data = "Prout";
        System.out.println("Original data: " + data);

        try {

            byte[] encryptedData = encrypt(data, keyPair.getPublicKey());
            System.out.println("Encrypted data: " + new String(encryptedData));

            String decryptedData = decrypt(encryptedData, keyPair.getPrivateKey());
            System.out.println("Decrypted data: " + decryptedData);
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
