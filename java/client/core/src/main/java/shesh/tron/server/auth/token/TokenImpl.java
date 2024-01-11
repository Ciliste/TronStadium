package shesh.tron.server.auth.token;

import java.io.Serializable;

public class TokenImpl implements Token, Serializable {

    private String token;

    public TokenImpl() {

        this.token = null;
    }

    public TokenImpl(String token) {

        this.token = token;
    }

    public TokenImpl(byte[] token) {

        this.token = new String(token);
    }

    @Override
    public void setToken(String token) {

        this.token = token;
    }

    @Override
    public String getToken() {

        return token;
    }

    @Override
    public String toString() {

        return "TokenImpl{" +
                "token='" + token + '\'' +
                "'}";
    }
}
