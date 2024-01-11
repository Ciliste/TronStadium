package shesh.tron.server;

import shesh.tron.server.auth.AuthManager;
import shesh.tron.server.auth.credentials.impl.EncryptedCredentials;
import shesh.tron.server.auth.token.EncryptedToken;
import shesh.tron.server.auth.token.Token;
import shesh.tron.server.auth.user.User;
import shesh.tron.server.crypto.cipher.AesCipher;
import shesh.tron.server.crypto.key.PublicKey;
import shesh.tron.server.crypto.key.SecretKey;
import shesh.tron.server.crypto.key.SecretKeyGenerator;
import shesh.tron.server.crypto.key.impl.EncryptedSecretKey;
import shesh.tron.server.crypto.key.provider.KeyProvider;
import shesh.tron.utils.APIUtils;

import java.rmi.Naming;
import java.rmi.Remote;

public class RmiTestClient {

    public static void main(String[] args) {

        try {

            Remote remote = Naming.lookup("rmi://localhost/keyProvider");
            KeyProvider keyProvider = (KeyProvider) remote;

            PublicKey publicKey = keyProvider.getPublicKey();
            SecretKey secretKey = SecretKeyGenerator.generateSecretKey(128);

            System.out.println("Public key: " + publicKey);
            System.out.println("Secret key: " + secretKey);

            String id = keyProvider.registerKey(EncryptedSecretKey.of(secretKey, publicKey));
            System.out.println("Id: " + id);

            remote = Naming.lookup("rmi://localhost/authManager");
            AuthManager authManager = (AuthManager) remote;

            EncryptedCredentials encryptedCredentials = EncryptedCredentials.encrypt("a", "a", secretKey);

            EncryptedToken encryptedToken = authManager.login(id, encryptedCredentials);

            System.out.println("Encrypted token: " + encryptedToken);

            Token token = encryptedToken.decrypt(secretKey);

            System.out.println("Token: " + token);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
