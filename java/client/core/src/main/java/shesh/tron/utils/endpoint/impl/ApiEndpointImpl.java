package shesh.tron.utils.endpoint.impl;

import shesh.tron.server.auth.AuthManager;
import shesh.tron.server.auth.credentials.Credentials;
import shesh.tron.server.auth.credentials.impl.CredentialsImpl;
import shesh.tron.server.auth.credentials.impl.EncryptedCredentials;
import shesh.tron.server.auth.token.EncryptedToken;
import shesh.tron.server.auth.token.Token;
import shesh.tron.server.auth.user.User;
import shesh.tron.server.crypto.key.PublicKey;
import shesh.tron.server.crypto.key.SecretKey;
import shesh.tron.server.crypto.key.SecretKeyGenerator;
import shesh.tron.server.crypto.key.impl.EncryptedSecretKey;
import shesh.tron.server.crypto.key.provider.KeyProvider;
import shesh.tron.server.friend.FriendManager;
import shesh.tron.server.game.GameAddress;
import shesh.tron.server.game.GameManager;
import shesh.tron.utils.endpoint.ApiEndpoint;
import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.LoggerFactory;
import shesh.tron.worker.request.Request;
import shesh.tron.worker.request.impl.AbstractRequest;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Implementation of {@link ApiEndpoint} using RMI.
 * @see ApiEndpoint
 * @see AuthManager
 * @see KeyProvider
 * @see AuthManager#login(String, EncryptedCredentials)
 * @see AuthManager#register(String, EncryptedCredentials)
 * @see KeyProvider#getPublicKey()
 * @see KeyProvider#registerKey(EncryptedSecretKey)
 */
public class ApiEndpointImpl implements ApiEndpoint {

    private final String host;

    public ApiEndpointImpl(String host) {

        this.host = host;
    }

    private SecretKey secretKey;
    private String sessionId;
    private AuthManager authManager;
    private FriendManager friendManager;
    private GameManager gameManager;

    @Override
    public String getHost() {

        return host;
    }

    @Override
    public Request<Boolean> connect(int port) {

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    Remote r = Naming.lookup("rmi://" + host + ":" + port + "/authManager");

                    if (r instanceof AuthManager) {

                        authManager = (AuthManager) r;
                    }
                    else {

                        callThen(false);
                    }

                    r = Naming.lookup("rmi://" + host + ":" + port + "/friendManager");

                    if (r instanceof FriendManager) {

                        friendManager = (FriendManager) r;
                    }
                    else {

                        callThen(false);
                    }

                    r = Naming.lookup("rmi://" + host + ":" + port + "/gameManager");

                    if (r instanceof GameManager) {

                        gameManager = (GameManager) r;
                    }
                    else {

                        callThen(false);
                    }

                    r = Naming.lookup("rmi://" + host + ":" + port + "/keyProvider");

                    if (r instanceof KeyProvider) {

                        KeyProvider keyProvider = (KeyProvider) r;

                        PublicKey publicKey = keyProvider.getPublicKey();
                        secretKey = SecretKeyGenerator.generateSecretKey(128);

                        EncryptedSecretKey encryptedSecretKey = EncryptedSecretKey.of(secretKey, publicKey);

                        sessionId = keyProvider.registerKey(encryptedSecretKey);

                        callThen(true);
                    }
                    else {

                        callThen(false);
                    }
                }
                catch (Exception e) {

                    callThen(false);
                    callError(e);
                }
            }
        };
    }

    @Override
    public boolean isConnected() {

        return authManager != null && friendManager != null && gameManager != null && secretKey != null && sessionId != null;
    }

    @Override
    public void disconnect() {

        authManager = null;
        friendManager = null;
        gameManager = null;

        secretKey = null;
        sessionId = null;
    }

    @Override
    public Request<Token> login(String username, String password) {

        return new AbstractRequest<>() {

            public void execute() {

                try {

                    Credentials credentials = new CredentialsImpl(username, password);
                    EncryptedCredentials encryptedCredentials = EncryptedCredentials.encrypt(credentials, secretKey);

                    EncryptedToken encryptedToken = authManager.login(sessionId, encryptedCredentials);

                    if (encryptedToken != null) {

                        Token token = encryptedToken.decrypt(secretKey);

                        Logger logger = LoggerFactory.getLogger();

                        logger.info("Logged in as " + username + " with token " + token);

                        callThen(token);
                    }
                    else {

                        callError(new RuntimeException("Invalid credentials"));
                    }
                }
                catch (RemoteException e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<Token> register(String username, String password) {

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    Credentials credentials = new CredentialsImpl(username, password);
                    EncryptedCredentials encryptedCredentials = EncryptedCredentials.encrypt(credentials, secretKey);

                    EncryptedToken encryptedToken = authManager.register(sessionId, encryptedCredentials);

                    if (encryptedToken != null) {

                        Token token = encryptedToken.decrypt(secretKey);

                        callThen(token);
                    }
                    else {

                        callError(new RuntimeException("Invalid credentials"));
                    }
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<Boolean> isValidToken(Token token) {

        Logger logger = LoggerFactory.getLogger();

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    EncryptedToken encryptedToken = new EncryptedToken(token, secretKey);

                    boolean valid = authManager.isValidToken(sessionId, encryptedToken);

                    callThen(valid);
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<User> getUser(Token token) {

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    EncryptedToken encryptedToken = new EncryptedToken(token, secretKey);

                    User user = authManager.getUser(sessionId, encryptedToken);

                    callThen(user);
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<User> getUser(Token token, String userId) {

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    EncryptedToken encryptedToken = new EncryptedToken(token, secretKey);

                    User user = authManager.getUser(sessionId, encryptedToken, userId);

                    callThen(user);
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<Boolean> sendFriendRequest(Token token, String username, String userId) {

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    EncryptedToken encryptedToken = new EncryptedToken(token, secretKey);

                    friendManager.sendFriendRequest(sessionId, encryptedToken, username, userId);

                    callThen(true);
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<User[]> getFriendRequests(Token token) {

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    EncryptedToken encryptedToken = new EncryptedToken(token, secretKey);

                    User[] friendRequests = friendManager.getFriendRequests(sessionId, encryptedToken);
                    LoggerFactory.getLogger().debug("Friend requests: " + friendRequests);

                    callThen(friendRequests);
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<Boolean> acceptFriendRequest(Token token, String username, String userId) {

        return new AbstractRequest<Boolean>() {
            @Override
            public void execute() {

                try {

                    EncryptedToken encryptedToken = new EncryptedToken(token, secretKey);

                    callThen(friendManager.acceptFriendRequest(sessionId, encryptedToken, username, userId));
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<Boolean> declineFriendRequest(Token token, String username, String userId) {

        return new AbstractRequest<Boolean>() {
            @Override
            public void execute() {

                try {

                    EncryptedToken encryptedToken = new EncryptedToken(token, secretKey);

                    callThen(friendManager.declineFriendRequest(sessionId, encryptedToken, username, userId));
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    @Override
    public Request<GameAddress> openGame(Token token) {

        return new AbstractRequest<GameAddress>() {

            @Override
            public void execute() {

                try {

                    EncryptedToken encryptedToken = new EncryptedToken(token, secretKey);

                    GameAddress gameAddress = gameManager.openGame(sessionId, encryptedToken);

                    callThen(gameAddress);
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }
}
