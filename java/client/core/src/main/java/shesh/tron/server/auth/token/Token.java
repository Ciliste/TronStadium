package shesh.tron.server.auth.token;

import java.io.Serializable;

public interface Token {

    public abstract void setToken(String token);
    public abstract String getToken();

    static Token of(String token) {

        return new TokenImpl(token);
    }
}
