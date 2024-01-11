package shesh.tron.server.crypto.key.provider.impl;

import shesh.tron.server.auth.token.Token;
import shesh.tron.server.crypto.key.KeyPair;
import shesh.tron.server.crypto.key.PublicKey;
import shesh.tron.server.crypto.key.SecretKey;
import shesh.tron.server.crypto.key.impl.EncryptedSecretKey;
import shesh.tron.server.crypto.key.provider.KeyProvider;
import shesh.tron.server.crypto.key.provider.ServerSideKeyProvider;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class KeyProviderImpl extends UnicastRemoteObject implements KeyProvider, ServerSideKeyProvider {

    private final KeyPair keyPair;
    private final Map<String, SecretKey> keys;

    public KeyProviderImpl(KeyPair keyPair) throws RemoteException {

        super();

        this.keyPair = keyPair;
        this.keys = new HashMap<>();
    }

    @Override
    public PublicKey getPublicKey() throws RemoteException {

        return keyPair.getPublicKey();
    }

    @Override
    public String registerKey(EncryptedSecretKey key) throws RemoteException {

        String id = generateId();

        SecretKey secretKey = EncryptedSecretKey.decrypt(key, keyPair.getPrivateKey());

        keys.put(id, secretKey);

        return id;
    }

    private String generateId() {

        StringBuilder id;
        do {

            id = new StringBuilder();

            for (int i = 0; i < 16; i++) {

                id.append((char) (Math.random() * 26 + 'a'));
            }
        }
        while (keys.containsKey(id.toString()));

        return id.toString();
    }

    @Override
    public SecretKey getSecretKey(String id) {

        return keys.get(id);
    }

    private final Map<String, String> tokenToIdMap = new HashMap<>();

    @Override
    public SecretKey getSecretKey(Token token) {

        return getSecretKey(tokenToIdMap.get(token.getToken()));
    }
}
