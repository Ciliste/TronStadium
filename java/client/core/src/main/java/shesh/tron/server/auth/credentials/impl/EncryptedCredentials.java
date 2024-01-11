package shesh.tron.server.auth.credentials.impl;

import shesh.tron.server.auth.credentials.Credentials;
import shesh.tron.server.crypto.cipher.AesCipher;
import shesh.tron.server.crypto.cipher.RsaCipher;
import shesh.tron.server.crypto.key.SecretKey;
import shesh.tron.server.crypto.key.SecretKeyGenerator;

public class EncryptedCredentials extends CredentialsImpl {

    private EncryptedCredentials(String encryptedUsername, String encryptedPassword) {

        super(encryptedUsername, encryptedPassword);
    }

    public Credentials decrypt(SecretKey key) {

        return new CredentialsImpl(new String(AesCipher.decrypt(getUsername().getBytes(), key)), new String(AesCipher.decrypt(getPassword().getBytes(), key)));
    }

    public static EncryptedCredentials encrypt(String username, String password, SecretKey key) {

        return EncryptedCredentials.encrypt(new CredentialsImpl(username, password), key);
    }

    public static EncryptedCredentials encrypt(Credentials credentials, SecretKey key) {

        return new EncryptedCredentials(new String(AesCipher.encrypt(credentials.getUsername(), key)), new String(AesCipher.encrypt(credentials.getPassword(), key)));
    }

    @Override
    public String toString() {

        return "EncryptedCredentials{" +
                "username='" + getUsername().replace("\n", "").replace("\r", "") + '\'' +
                ", password='" + getPassword().replace("\n", "").replace("\r", "") + '\'' +
                "'}";
    }

    public static void main(String[] args) {

        SecretKey key = SecretKeyGenerator.generateSecretKey(128);

        Credentials credentials = new CredentialsImpl("username", "password");

        EncryptedCredentials encryptedCredentials = EncryptedCredentials.encrypt(credentials, key);

        System.out.println("Encrypted credentials: " + encryptedCredentials);
        System.out.println("Decrypted credentials: " + encryptedCredentials.decrypt(key));
    }
}
