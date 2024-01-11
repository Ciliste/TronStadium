package shesh.tron.server;

import shesh.tron.server.auth.AuthManager;
import shesh.tron.server.auth.AuthUtils;
import shesh.tron.server.auth.credentials.Credentials;
import shesh.tron.server.auth.credentials.impl.EncryptedCredentials;
import shesh.tron.server.auth.token.EncryptedToken;
import shesh.tron.server.auth.token.Token;
import shesh.tron.server.auth.token.TokenImpl;
import shesh.tron.server.auth.user.User;
import shesh.tron.server.auth.user.UserImpl;
import shesh.tron.server.crypto.key.KeyPair;
import shesh.tron.server.crypto.key.KeyPairFactory;
import shesh.tron.server.crypto.key.SecretKey;
import shesh.tron.server.crypto.key.provider.KeyProvider;
import shesh.tron.server.crypto.key.provider.ServerSideKeyProvider;
import shesh.tron.server.crypto.key.provider.impl.KeyProviderImpl;
import shesh.tron.server.friend.FriendManager;
import shesh.tron.server.game.GameController;
import shesh.tron.server.game.GameCreator;
import shesh.tron.server.game.GameManager;
import shesh.tron.server.game.GameAddress;
import shesh.tron.server.game.impl.ThreadedGameCreator;
import shesh.tron.server.info.ServerInfo;
import shesh.tron.server.serverhandler.ServerHandler;
import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SqlServerHandler extends UnicastRemoteObject implements ServerHandler, ServerInfo, AuthManager, FriendManager, GameManager {

    private transient Connection connection;
    private final ServerSideKeyProvider serverSideKeyProvider;
    private final KeyProvider keyProvider;

    public SqlServerHandler() throws RemoteException {

        super();

        Logger log = LoggerFactory.getLogger();

        log.info("Creating server KeyPair");
        KeyPair keyPair = KeyPairFactory.generateRSAKeyPair(2048);
        log.info("Server KeyPair created");

        log.info("Creating KeyProvider");
        KeyProviderImpl keyProviderImpl = new KeyProviderImpl(keyPair);
        log.info("KeyProvider created");

        keyProvider = keyProviderImpl;
        serverSideKeyProvider = keyProviderImpl;
    }

    public void init() {

        String url = "jdbc:postgresql://tronstadium.ciliste.games:54322/postgres";
        String user = "postgres";
        String password = "change_me";

        try {

            connection = DriverManager.getConnection(url, user, password);
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthManager getAuthManager() {

        return this;
    }

    @Override
    public FriendManager getFriendManager() {

        return this;
    }

    @Override
    public ServerInfo getServerInfo() {

        return this;
    }

    @Override
    public KeyProvider getKeyProvider() {

        return keyProvider;
    }

    @Override
    public EncryptedToken login(String sessionId, EncryptedCredentials encryptedCredentials) throws RemoteException {

        try {

            SecretKey key = serverSideKeyProvider.getSecretKey(sessionId);
            Credentials credentials = encryptedCredentials.decrypt(key);

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");

            statement.setString(1, credentials.getUsername());

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {

                String passwordHash = resultSet.getString("password_hash");

                if (AuthUtils.verify(credentials.getPassword(), passwordHash)) {

                    String token = AuthUtils.generateToken();

                    insertToken(resultSet.getInt("id"), token);

                    Token tok = new TokenImpl(token);

                    return new EncryptedToken(tok, key);
                }
            }
        }
        catch (SQLException e) {

            LoggerFactory.getLogger().error(e);
        }

        return null;
    }

    @Override
    public EncryptedToken register(String sessionId, EncryptedCredentials credentials) throws RemoteException {

        int id;

        do {

            id = generate4DigitNumber();
        }
        while (!isIdUnique(id));

        try {

            SecretKey key = serverSideKeyProvider.getSecretKey(sessionId);
            Credentials creds = credentials.decrypt(key);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (id, username, password_hash) VALUES (?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, creds.getUsername());
            statement.setString(3, AuthUtils.hash(creds.getPassword()));

            statement.executeUpdate();

            if (statement.getUpdateCount() == 1) {

                String token = AuthUtils.generateToken();

                System.out.println("token = " + token);
                insertToken(id, token);

                return new EncryptedToken(token, key);
            }
        }
        catch (SQLException e) {

            LoggerFactory.getLogger().error(e);
        }

        return null;
    }

    @Override
    public User getUser(String sessionId, EncryptedToken token) throws RemoteException {

        SecretKey key = serverSideKeyProvider.getSecretKey(sessionId);
        Token tok = token.decrypt(key);

        String id = getIdFromToken(tok.getToken());

        return (id == null) ? null : getUser(sessionId, token, id);
    }

    @Override
    public User getUser(String sessionId, EncryptedToken token, String userId) throws RemoteException {

        return isValidToken(sessionId, token) ? constructUser(userId) : null;
    }

    @Override
    public boolean isValidToken(String sessionId, EncryptedToken token) throws RemoteException {

        try {

            SecretKey key = serverSideKeyProvider.getSecretKey(sessionId);
            Token tok = token.decrypt(key);

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE token = ? AND token_expires > NOW()");
            statement.setString(1, tok.getToken());

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            return resultSet.next();
        }
        catch (Exception e) {

            LoggerFactory.getLogger().error(e);
            return false;
        }
    }

    private User constructUser(String id) {

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, Integer.parseInt(id));

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {

                return new UserImpl(resultSet.getString("id"), resultSet.getString("username"), resultSet.getString("created_at"));
            }
            else {

                return null;
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    private void insertToken(int id, String token) {

        try {

            PreparedStatement statement = connection.prepareStatement("UPDATE users SET token = ?, token_expires = NOW() + INTERVAL '1 day' WHERE id = ?");
            statement.setString(1, token);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
        catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private boolean isIdUnique(int id) {

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, id);

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();
            return !resultSet.next();
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    private String getIdFromToken(String token) {

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE token = ?");
            statement.setString(1, token);

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {

                return resultSet.getString("id");
            }
            else {

                return null;
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    private static int generate4DigitNumber() {

        return (int) (Math.random() * 10000);
    }

    @Override
    public boolean removeFriend(EncryptedToken token, String friendUsername, String friendUserId) throws RemoteException {
        return false;
    }

    @Override
    public boolean acceptFriendRequest(String sessionId, EncryptedToken token, String friendUsername, String friendUserId) throws RemoteException {
        return false;
    }

    @Override
    public boolean declineFriendRequest(String sessionId, EncryptedToken token, String friendUsername, String friendUserId) throws RemoteException {

        Logger log = LoggerFactory.getLogger();
        log.debug("declineFriendRequest(" + sessionId + ", " + token + ", " + friendUsername + ", " + friendUserId + ")");

        try {

            Token tok = token.decrypt(serverSideKeyProvider.getSecretKey(sessionId));
            String userId = getIdFromToken(tok.getToken());

            if (userId == null) {

                log.debug("userId == null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement("DELETE FROM friends_requests WHERE idFrom = ? AND idTo = ?");
            statement.setInt(1, Integer.parseInt(friendUserId));
            statement.setInt(2, Integer.parseInt(userId));

            statement.executeUpdate();

            return statement.getUpdateCount() == 1;
        }
        catch (Exception e) {

            log.error(e);
        }

        return false;
    }

    @Override
    public boolean sendFriendRequest(String sessionId, EncryptedToken token, String friendUsername, String friendUserId) throws RemoteException {

        Logger log = LoggerFactory.getLogger();
        log.debug("sendFriendRequest(" + token + ", " + friendUsername + ", " + friendUserId + ")");

        try {

            SecretKey key = serverSideKeyProvider.getSecretKey(sessionId);
            Token tok = token.decrypt(key);

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, Integer.parseInt(friendUserId));

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {

                String userId = getIdFromToken(tok.getToken());
                if (userId == null) {

                    log.debug("userId == null");
                    return false;
                }
                else if (userId.equals(friendUserId)) {

                    log.debug("userId.equals(friendUserId)");
                    return false;
                }

                PreparedStatement checkIfAlreadySent = connection.prepareStatement("SELECT * FROM friends WHERE (idFrom = ? AND idTo = ?) OR (idFrom = ? AND idTo = ?)");
                checkIfAlreadySent.setInt(1, Integer.parseInt(userId));
                checkIfAlreadySent.setInt(2, Integer.parseInt(friendUserId));
                checkIfAlreadySent.setInt(3, Integer.parseInt(friendUserId));
                checkIfAlreadySent.setInt(4, Integer.parseInt(userId));

                checkIfAlreadySent.executeQuery();

                ResultSet resultSet1 = checkIfAlreadySent.getResultSet();

                if (resultSet1.next()) {

                    log.debug("resultSet1.next()");
                    return false;
                }

                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO friends_requests (idFrom, idTo) VALUES (?, ?)");
                statement1.setInt(1, Integer.parseInt(userId));
                statement1.setInt(2, Integer.parseInt(friendUserId));

                statement1.executeUpdate();

                return true;
            }
        }
        catch (Exception e) {

            LoggerFactory.getLogger().error(e);
        }

        return false;
    }

    @Override
    public User[] getFriends(EncryptedToken token) throws RemoteException {
        return new User[0];
    }

    @Override
    public User[] getFriendRequests(String sessionId, EncryptedToken token) throws RemoteException {

        Logger log = LoggerFactory.getLogger();
        log.debug("getFriendRequests(" + sessionId + ", " + token + ")");

        try {

            SecretKey key = serverSideKeyProvider.getSecretKey(sessionId);
            Token tok = token.decrypt(key);

            String userId = getIdFromToken(tok.getToken());
            if (userId == null) {

                log.debug("userId == null");
                return new User[0];
            }

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friends_requests WHERE idTo = ?");
            statement.setInt(1, Integer.parseInt(userId));

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            List<User> users = new LinkedList<>();
            while (resultSet.next()) {

                int idFrom = resultSet.getInt("idFrom");

                users.add(constructUser(String.valueOf(idFrom)));
            }

            return users.toArray(new User[0]);
        }
        catch (Exception e) {

            LoggerFactory.getLogger().error(e);
        }

        return new User[0];
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public String getServerDescription() {
        return null;
    }

    private final GameCreator gameCreator = new ThreadedGameCreator();

    private final Map<String, GameController> games = new HashMap<>();

    @Override
    public GameAddress openGame(String sessionId, EncryptedToken token) throws RemoteException {

        Token tok = token.decrypt(serverSideKeyProvider.getSecretKey(sessionId));
        if (!isValidToken(sessionId, token)) {

            return null;
        }

        String gameId;
        do {

            gameId = generateGameId();
        }
        while (!isGameIdUnique(gameId));

        GameController gameController = gameCreator.createGame(gameId);
        games.put(sessionId, gameController);

        gameController.start();

        return gameController.getGameAddress();
    }

    @Override
    public void closeGame(String sessionId, EncryptedToken token, GameAddress lobbyAddress) {


    }

    private String generateGameId() {

        final String CHARS = "0123456789";

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 20; i++) {

            builder.append(CHARS.charAt((int) (Math.random() * CHARS.length())));
        }

        String gameId = builder.toString();

        if (gameId.startsWith("0")) {

            gameId = CHARS.charAt(1 + (int) (Math.random() * CHARS.length() - 1)) + gameId.substring(1);
        }

        if (gameId.endsWith("0")) {

            gameId = gameId.substring(0, gameId.length() - 1) + CHARS.charAt(1 + (int) (Math.random() * CHARS.length() - 1));
        }

        return gameId;
    }

    private boolean isGameIdUnique(String gameId) {

        return true;
    }

    @Override
    public GameManager getGameManager() {

        return this;
    }
}
