package shesh.tron.server.auth;

import shesh.tron.server.auth.credentials.impl.EncryptedCredentials;
import shesh.tron.server.auth.token.EncryptedToken;
import shesh.tron.server.auth.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthManager extends Remote {

    /**
     * Authenticates the user with the given encrypted username and password.
     * @param encryptedUsername The encrypted username of the user.
     * @param encryptedPassword The encrypted password of the user.
     * @throws RemoteException
     * @return A new token for the user if the authentication is successful, null otherwise.
     */
    public abstract EncryptedToken login(String sessionId, EncryptedCredentials credentials) throws RemoteException;

    /**
     * Registers a new user with the given encrypted username and password.
     * @param encryptedUsername The encrypted username of the user.
     * @param encryptedPassword The encrypted password of the user.
     * @throws RemoteException
     * @return A new token for the user if the registration is successful, null otherwise.
     */
    public abstract EncryptedToken register(String sessionId, EncryptedCredentials credentials) throws RemoteException;

    /**
     * Gets the user with the given token.
     * @param token The token of the user.
     * @throws RemoteException
     * @return The user with the given token if the token is valid, null otherwise.
     */
    public abstract User getUser(String sessionId, EncryptedToken encryptedToken) throws RemoteException;

    /**
     * Gets the user with the given id using the given token.
     * @param token The token to access the user.
     * @param id The id of the user.
     * @throws RemoteException
     * @return The user with the given id if the token is valid, null otherwise.
     */
    public abstract User getUser(String sessionId, EncryptedToken token, String userId) throws RemoteException;

    /**
     * Checks if the given token is valid.
     * @param token The token to check.
     * @throws RemoteException
     * @return True if the token is valid, false otherwise.
     */
    public abstract boolean isValidToken(String sessionId, EncryptedToken token) throws RemoteException;
}
