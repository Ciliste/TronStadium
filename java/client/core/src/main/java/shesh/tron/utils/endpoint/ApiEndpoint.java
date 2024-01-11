package shesh.tron.utils.endpoint;

import shesh.tron.server.auth.token.Token;
import shesh.tron.server.auth.user.User;
import shesh.tron.server.game.GameAddress;
import shesh.tron.worker.request.Request;

import shesh.tron.server.auth.AuthManager;
import shesh.tron.server.friend.FriendManager;

/**
 * This interface is used to connect to a server and to perform operations on it.
 * @see AuthManager
 * @see FriendManager
 */
public interface ApiEndpoint {

    /**
     * Gets the host of the server.
     * @return The host of the server.
     */
    public abstract String getHost();

    /**
     * Connects to the server on the default RMI port (1099).
     * @return A {@link Request} that will perform the connection and will return true if the connection is successful,
     * false otherwise.
     * @see Request
     * @see #connect(int)
     */
    default Request<Boolean> connect() {

        return connect(1099);
    }

    /**
     * Connects to the server on the specified port.
     * @param port The port to connect to.
     * @return A {@link Request} that will perform the connection and will return true if the connection is successful,
     * false otherwise.
     * @see Request
     */
    public abstract Request<Boolean> connect(int port);

    /**
     * Checks if the endpoint is connected to the server or not.
     * @return True if the endpoint is connected to the server, false otherwise.
     */
    public abstract boolean isConnected();

    /**
     * Disconnects the endpoint from the server.
     */
    public abstract void disconnect();

    /**
     * Logs in the user with the given username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A {@link Request} that will perform the login and will return a {@link Token} of the user if the login is
     * successful, null otherwise.
     * @see Request
     * @see Token
     */
    public abstract Request<Token> login(String username, String password);

    /**
     * Registers a new user with the given username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A {@link Request} that will perform the registration and will return a {@link Token} of the user if the
     * registration is successful, null otherwise.
     * @see Request
     * @see Token
     */
    public abstract Request<Token> register(String username, String password);

    /**
     * Checks if the given token is valid or not.
     * @param token The token to check.
     * @return A {@link Request} that will perform the check and will return true if the token is valid, false otherwise.
     * @see Request
     */
    public abstract Request<Boolean> isValidToken(Token token);

    /**
     * Gets the {@link User} associated with the given token.
     * @param token The token of the user.
     * @return A {@link Request} that will perform the operation and will return the {@link User} associated with the
     * given token if the operation is successful, null otherwise.
     * @see Request
     * @see User
     */
    public abstract Request<User> getUser(Token token);

    /**
     * Gets the {@link User} associated with the given user id.
     * @param token The token to perform the request.
     * @param userId The id of the user.
     * @return A {@link Request} that will perform the operation and will return the {@link User} associated with the
     * given user id if the operation is successful, null otherwise.
     * @see Request
     * @see User
     */
    public abstract Request<User> getUser(Token token, String userId);

    /**
     * Send a friend request to the user with the given username and user id.
     * @param token The token to perform the request.
     * @param username The username of the user.
     * @param userId The id of the user.
     * @return A {@link Request} that will perform the operation and will return true if the operation is successful,
     * false otherwise.
     * @see Request
     */
    public abstract Request<Boolean> sendFriendRequest(Token token, String username, String userId);

    /**
     * Gets the pending friend requests of the user associated with the given token.
     * @param token The token to perform the request.
     * @return A {@link Request} that will perform the operation and will return an array of {@link User} if the
     * operation is successful, null otherwise.
     * @see Request
     * @see User
     */
    public abstract Request<User[]> getFriendRequests(Token token);

    /**
     * Accepts the friend request of the user with the given username and user id.
     * @param token The token to perform the request.
     * @param username The username of the user.
     * @param userId The id of the user.
     * @return A {@link Request} that will perform the operation and will return true if the operation is successful,
     * false otherwise.
     * @see Request
     */
    public abstract Request<Boolean> acceptFriendRequest(Token token, String username, String userId);

    /**
     * Declines the friend request of the user with the given username and user id.
     * @param token The token to perform the request.
     * @param username The username of the user.
     * @param userId The id of the user.
     * @return A {@link Request} that will perform the operation and will return true if the operation is successful,
     * false otherwise.
     * @see Request
     */
    public abstract Request<Boolean> declineFriendRequest(Token token, String username, String userId);

    public abstract Request<GameAddress> openGame(Token token);
}
