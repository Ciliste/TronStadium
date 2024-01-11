package shesh.tron.utils;

import shesh.tron.server.auth.AuthManager;
import shesh.tron.server.auth.token.EncryptedToken;
import shesh.tron.server.auth.token.Token;
import shesh.tron.server.auth.user.User;
import shesh.tron.server.crypto.cipher.AesCipher;
import shesh.tron.server.crypto.key.SecretKey;
import shesh.tron.utils.struct.Couple;
import shesh.tron.worker.request.impl.AbstractRequest;
import shesh.tron.worker.request.Request;

import java.rmi.Naming;
import java.rmi.Remote;

public final class APIUtils {

    private static final String API_URL = "localhost";
    private static final String AUTH_MANAGER_URL = "rmi://" + API_URL + "/authManager";

    private APIUtils() {

    }

    public static Request<Token> register(String username, String password) {

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    Remote r = Naming.lookup(AUTH_MANAGER_URL);

                    if (r instanceof AuthManager) {

                        AuthManager authManager = (AuthManager) r;
                        EncryptedToken token = authManager.register(null, null);

                        callThen(null);
                    }
                    else {

                        callError(new RuntimeException(AUTH_MANAGER_URL + " is not an instance of AuthManager"));
                    }
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    public static Request<Token> login(String username, String password) {

        return new AbstractRequest<>() {
            @Override
            public void execute() {

                try {

                    Remote r = Naming.lookup(AUTH_MANAGER_URL);

                    if (r instanceof AuthManager) {

                        AuthManager authManager = (AuthManager) r;
                        EncryptedToken token = authManager.login(null, null);

                        callThen(null);
                    }
                    else {

                        callError(new RuntimeException(AUTH_MANAGER_URL + " is not an instance of AuthManager"));
                    }
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    public static Request<User> getUserInfo(String token) {

        return new AbstractRequest<>() {

            @Override
            public void execute() {

                try {

                    Remote r = Naming.lookup(AUTH_MANAGER_URL);

                    if (r instanceof AuthManager) {

                        AuthManager authManager = (AuthManager) r;
                        User user = authManager.getUser(null, null);

                        callThen(user);
                    }
                    else {

                        callError(new RuntimeException(AUTH_MANAGER_URL + " is not an instance of AuthManager"));
                    }
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    public static Request<String> getServerInfo() {

        return null;
    }

    public static Request<Boolean> isValidToken(String token) {

        return new AbstractRequest<>() {
            @Override
            public void execute() {

                try {

                    Remote r = Naming.lookup(AUTH_MANAGER_URL);

                    if (r instanceof AuthManager) {

                        AuthManager authManager = (AuthManager) r;
                        boolean isValid = authManager.isValidToken(null, null);

                        callThen(isValid);
                    }
                    else {

                        callError(new RuntimeException(AUTH_MANAGER_URL + " is not an instance of AuthManager"));
                    }
                }
                catch (Exception e) {

                    callError(e);
                }
            }
        };
    }

    public static String formEncryptedData(String id, String data, SecretKey key) {

        return id + "/" + new String(AesCipher.encrypt(data, key));
    }

    public static Couple<String, String> parseEncryptedData(String encryptedData) {

        String id = encryptedData.substring(0, encryptedData.indexOf("/"));
        String data = encryptedData.substring(encryptedData.indexOf("/") + 1);

        return new Couple<>() {
            @Override
            public String getFirst() {

                return id;
            }

            @Override
            public String getSecond() {

                return data;
            }

            @Override
            public void setFirst(String first) {

                throw new UnsupportedOperationException();
            }

            @Override
            public void setSecond(String second) {

                throw new UnsupportedOperationException();
            }
        };
    }
}
