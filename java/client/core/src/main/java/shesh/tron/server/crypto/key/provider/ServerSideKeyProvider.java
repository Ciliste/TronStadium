package shesh.tron.server.crypto.key.provider;

import shesh.tron.server.auth.token.Token;
import shesh.tron.server.crypto.key.SecretKey;

public interface ServerSideKeyProvider {

    /**
     * Gets the secret key associated with the given id.
     * @param id The id of the key.
     * @return The secret key associated with the given id.
     */
    public abstract SecretKey getSecretKey(String id);

    /**
     * Gets the secret key associated with the given token.
     * @param token The token of the key.
     * @return The secret key associated with the given token.
     */
    public abstract SecretKey getSecretKey(Token token);
}
