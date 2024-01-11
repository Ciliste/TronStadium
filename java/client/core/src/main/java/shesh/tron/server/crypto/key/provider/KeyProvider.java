package shesh.tron.server.crypto.key.provider;

import shesh.tron.server.crypto.key.PublicKey;
import shesh.tron.server.crypto.key.SecretKey;
import shesh.tron.server.crypto.key.impl.EncryptedSecretKey;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KeyProvider extends Remote {

    /**
     * Gets the public RSA key of the server.
     * @return The public key of the server.
     */
    public abstract PublicKey getPublicKey() throws RemoteException;

    /**
     * Registers the given secret key.
     * @param key The key to register.
     * @return The id associated with the key.
     */
    public abstract String registerKey(EncryptedSecretKey key) throws RemoteException;
}
