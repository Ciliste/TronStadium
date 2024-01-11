package shesh.tron.server.auth.token;

import shesh.tron.server.crypto.cipher.AesCipher;
import shesh.tron.server.crypto.key.SecretKey;

import java.io.Serializable;

public class EncryptedToken implements Serializable {

    public EncryptedToken(Token token, SecretKey key) {

        this(token.getToken(), key);
    }

    public EncryptedToken(String token, SecretKey key) {

        this(token.getBytes(), key);
    }

    private final byte[] token;

    public EncryptedToken(byte[] token, SecretKey key) {

        this.token = AesCipher.encrypt(token, key);
    }

    public Token decrypt(SecretKey key) {

        return new TokenImpl(AesCipher.decrypt(token, key));
    }

    @Override
    public String toString() {

        return "EncryptedToken{" +
                "token='" + new String(token).replace("\n", "").replace("\r", "") + '\'' +
                "'}";
    }
}
