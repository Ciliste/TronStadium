package shesh.tron.server.auth.credentials.impl;

import shesh.tron.server.auth.credentials.Credentials;

import java.io.Serializable;

public class CredentialsImpl implements Credentials, Serializable {

    private String username;
    private String password;

    public CredentialsImpl() {

        this.username = null;
        this.password = null;
    }

    public CredentialsImpl(String username, String password) {

        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {

        return this.username;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public void setUsername(String username) {

        this.username = username;
    }

    @Override
    public void setPassword(String password) {

        this.password = password;
    }

    @Override
    public String toString() {

        return "Credentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
